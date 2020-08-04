package com.juanjuan.service.web;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PersonSource {

    @Output("person-source")
    MessageChannel output();
}
