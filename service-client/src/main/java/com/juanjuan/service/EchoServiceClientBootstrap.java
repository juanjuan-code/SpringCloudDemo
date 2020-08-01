package com.juanjuan.service;

import com.juanjuan.service.client.EchoServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yuhui.guan
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class EchoServiceClientBootstrap {

    private final EchoServiceClient echoServiceClient;

    @LoadBalanced
    private final RestTemplate restTemplate;

    public EchoServiceClientBootstrap(EchoServiceClient echoServiceClient, RestTemplate restTemplate) {
        this.echoServiceClient = echoServiceClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        return echoServiceClient.echo(message);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceClientBootstrap.class, args);
    }
}
