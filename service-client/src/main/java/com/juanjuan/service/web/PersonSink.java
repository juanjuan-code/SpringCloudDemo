package com.juanjuan.service.web;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PersonSink {

    @Input("person-sink")
    SubscribableChannel channel();
}
