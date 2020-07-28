package com.juanjuan.service.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuhui.guan
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class EchoServiceClientBootstrap {

    private final EchoServiceClient echoServiceClient;

    public EchoServiceClientBootstrap(EchoServiceClient echoServiceClient) {
        this.echoServiceClient = echoServiceClient;
    }

    @GetMapping(value = "/call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        return echoServiceClient.echo(message);
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceClientBootstrap.class, args);
    }
}
