package com.juanjuan.service.web.kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.io.*;
import java.util.Map;

public class ObjectSerializer implements Serializer<Serializable> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Serializable data) {

        System.out.printf("当前 Topic : %s , 序列化对象：%s\n", topic, data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] dataArray = null;

        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) { // Java 7 try resource {}
            // 将对象写入到 ObjectOutputStream
            oos.writeObject(data);
            // 将写入后的数据，通过字节数组方式获取
            dataArray = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dataArray;
    }

    @Override
    public void close() {

    }
}
