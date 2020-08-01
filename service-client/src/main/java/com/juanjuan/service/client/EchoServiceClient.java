package com.juanjuan.service.client;

import com.juanjuan.service.factory.EchoServiceClientFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yuhui.guan
 */
@FeignClient(
        value = "service-provider"
//        ,fallbackFactory = EchoServiceClientFactory.class
)
public interface EchoServiceClient {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable String message);
}
