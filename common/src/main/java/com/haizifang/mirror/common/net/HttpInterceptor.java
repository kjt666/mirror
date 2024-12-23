package com.haizifang.mirror.common.net;

import android.content.ContextWrapper;

import com.haizifang.mirror.common.constant.CommonConstants;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (request.method().equalsIgnoreCase("GET")) {
            request = setHead(request, requestBuilder);
        } else if (request.method().equalsIgnoreCase("POST")) {
            //添加头
            request = setHead(request, requestBuilder);
        }

        return chain.proceed(request);
    }

    private Request setHead(Request request, Request.Builder requestBuilder) {
        Map<String, String> headerParamsMap = new HashMap<>();
        if (headerParamsMap != null && headerParamsMap.size() > 0) {
            Set<String> keys = headerParamsMap.keySet();
            for (String headerKey : keys) {
                requestBuilder.header(headerKey, headerParamsMap.get(headerKey));
            }
        }
        requestBuilder.header("verCode", CommonConstants.appVersionName);
        requestBuilder.header("token", CommonConstants.userToken);
        requestBuilder.header("userId", CommonConstants.userId);
        requestBuilder.header("appPkg", "com.cash.finian.loan.okrd.ifectiv.prstme");
        requestBuilder = requestBuilder.method(request.method(), request.body());
        return requestBuilder.build();
    }


    public static ContextWrapper getContext() {
        try {
            Class<?> c = Class.forName("android.app.AppGlobals");
            Method method = c.getDeclaredMethod("getInitialApplication");
            return (ContextWrapper) method.invoke(c);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Method method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                method.setAccessible(true);
                return (ContextWrapper) method.invoke(null);
            } catch (Exception e1) {

            }
        }
        return null;
    }


}
