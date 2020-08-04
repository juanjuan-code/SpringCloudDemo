package com.juanjuan.service.web.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

public class ObjectDeserializer implements Deserializer<Serializable> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        System.out.println(configs);
    }

    @Override
    public Serializable deserialize(String topic, byte[] data) {

        Serializable object = null;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            object = (Serializable) ois.readObject();
        } catch (IOException | ClassNotFoundException e) { // Java 7 Multiple Exception Catch
            throw new RuntimeException(e);
        }

        System.out.printf("当前 Topic : %s , 反序列化对象：%s\n", topic, String.valueOf(object));
        return object;
    }

    @Override
    public void close() {

    }
}
