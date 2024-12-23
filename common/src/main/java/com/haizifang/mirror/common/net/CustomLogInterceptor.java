package com.haizifang.mirror.common.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CustomLogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        printInfo(request, response);
        return response;
    }

    private void printInfo(Request request, Response response) {
        StringBuilder builder = new StringBuilder();
            String logInfo = "START HTTP->"
                    .concat("请求方法-->：")
                    .concat(request.method())
                    .concat(" ")
                    .concat(request.url().toString())
                    .concat(" \r\n ")
                    .concat("请求头-->：")
                    .concat(getRequestHeaders(request))
                    .concat(" \r\n ")
                    .concat("请求参数-->：")
                    .concat(getRequestParams(request))
                    .concat(" \r\n ")
                    .concat("返回结果-->：")
                    .concat(getResponseText(response))
                    .concat(" \r\n ")
                    .concat("END HTTP->");

            Log.i("network",logInfo);
    }

    private String getResponseText(Response response) {
        String str = "Empty!";
        try {
            ResponseBody body = response.body();
            if (body != null && body.contentLength() != 0) {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    @SuppressWarnings("CharsetObjectCanBeUsed") Charset charset = mediaType.charset(
                            Charset.forName("UTF-8"));
                    if (charset != null) {
                        str = buffer.clone().readString(charset);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private String getRequestParams(Request request) {
        String str = "Empty!";
        try {
            RequestBody body = request.body();
            if (body != null) {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    @SuppressWarnings("CharsetObjectCanBeUsed") Charset charset = mediaType.charset(
                            Charset.forName("UTF-8"));
                    if (charset != null) {
                        str = buffer.readString(charset);
                    } else {
                        str = buffer.readUtf8();
                    }
                } else {
                    str = buffer.readUtf8();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private String getRequestHeaders(Request request) {
        Headers headers = request.headers();
        String str = "Empty!";
        StringBuilder builder = new StringBuilder();
        if (headers.size() > 0) {
            for (int i = 0, count = headers.size(); i < count; i++) {
                builder.append(headers.name(i) + ": " + headers.value(i)).append(" \r\n ");
            }
            return builder.toString();
        } else {
            return "Empty!";
        }
    }
}