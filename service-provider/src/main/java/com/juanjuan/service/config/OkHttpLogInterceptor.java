package com.juanjuan.service.config;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 *  自定义请求日志拦截器
 * @Author: yuhui.guan
 * @Date: 2020/8/4 11:13
 */
public class OkHttpLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
