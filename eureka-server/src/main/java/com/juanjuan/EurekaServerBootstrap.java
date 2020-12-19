package com.juanjuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * Eureka 服务器
 * @author yuhui.guan
 * @Date 2020/07/28
 */
public class EurekaServerBootstrap {
    /**
     * EurekaServer 的服务注册与发现功能
     */
    @EnableAutoConfiguration
    @EnableEurekaServer
    public static class EurekaServerConfiguration {
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerConfiguration.class, args);
    }
}
