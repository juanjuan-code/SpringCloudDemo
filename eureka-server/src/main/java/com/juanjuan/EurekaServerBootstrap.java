package com.juanjuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @author yuhui.guan
 * @Date 2020/07/28
 */
public class EurekaServerBootstrap {

    @EnableAutoConfiguration
    @EnableEurekaServer
    public static class EurekaServerConfiguration {
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerConfiguration.class, args);
    }
}
