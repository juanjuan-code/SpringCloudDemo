package com.juanjuan.service.web;

import com.juanjuan.service.web.domain.Person;
import com.juanjuan.service.web.kafka.ObjectSerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@EnableAutoConfiguration
@SpringBootApplication // 具备当前类所在包扫描功能
@EnableDiscoveryClient
@EnableFeignClients
@RestController
@EnableBinding({Source.class, PersonSource.class, PersonSink.class})
public class EchoServiceClientBootstrap {

    private final EchoServiceClient echoServiceClient;
//
//    @LoadBalanced
//    private final RestTemplate restTemplate;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Source source;

    private final PersonSource personSource;

    private final PersonSink personSink;

    private final CsrfTokenRepository csrfTokenRepository;

    @Bean
    public ObjectSerializer objectSerializer() {
        return new ObjectSerializer();
    }


    public EchoServiceClientBootstrap(EchoServiceClient echoServiceClient,
//                                      RestTemplate restTemplate,
                                      KafkaTemplate<String, Object> kafkaTemplate,
                                      Source source,
                                      PersonSource personSource,
                                      PersonSink personSink, CsrfTokenRepository csrfTokenRepository) {
        this.echoServiceClient = echoServiceClient;
//        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.source = source;
        this.personSource = personSource;
        this.personSink = personSink;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    /**
     * 通过 Spring Message API 监听数据
     *
     * @return
     */
    @Bean
    public ApplicationRunner runner() {
        return args -> {
            personSink.channel().subscribe(new MessageHandler() {
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    MessageHeaders headers = message.getHeaders();
                    String contentType = headers.get("Content-Type", String.class);
                    Object object = message.getPayload();
                    System.out.printf("收到消息[主体：%s , 消息头：%s \n", object, headers);
                }
            });
        };
    }


    /**
     * 通过注解方式监听数据
     *
     * @param person
     */
    @StreamListener("person-source") // 指定 Channel 名称
    public void listenFromStream(Person person) {
        System.out.println(person);
    }

//    @KafkaListener(topics = "juanjuan")
//    public void listen(Person person) {
//        System.out.println(person);
//    }

    @GetMapping("/csrf/token")
    public CsrfToken csrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        if (csrfToken == null) {
            csrfToken = csrfTokenRepository.generateToken(request);
            csrfTokenRepository.saveToken(csrfToken, request, response);
        }
        return csrfToken;
    }

    /**
     * 发送 Kafka 消息
     *
     * @param name
     * @return
     */
    @GetMapping("/person")
    public Person person(String name) {
        Person person = createPerson(name);
        kafkaTemplate.send("juanjuan", person);
        return person;
    }

    @GetMapping("/stream/person/source")
    public Person streamPersonSource(String name) {
        Person person = createPerson(name);
        MessageChannel messageChannel = personSource.output();
        MessageBuilder messageBuilder = MessageBuilder.withPayload(person).setHeader("Content-Type", "java/pojo");
        messageChannel.send(messageBuilder.build());
        return person;
    }

    @GetMapping("/stream/person")
    public Person streamPerson(String name) {
        Person person = createPerson(name);
        MessageChannel messageChannel = source.output();
        messageChannel.send(MessageBuilder.withPayload(person).build());
        return person;
    }

    private Person createPerson(String name) {
        Person person = new Person();
        person.setId(System.currentTimeMillis());
        person.setName(name);
        return person;
    }


    @GetMapping(value = "/call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        return echoServiceClient.echo(message);
    }
//
//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceClientBootstrap.class, args);
    }
}
