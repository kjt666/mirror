package com.haizifang.mirror.common.net;

import com.haizifang.mirror.common.GlobalConfig;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpRetrofit {

    private static HttpRetrofit sHttpRetrofit;
    private Retrofit retrofit;

    private OnRequestErrorListener listener;

    public static HttpRetrofit getInstance() {
        if (sHttpRetrofit == null) {
            sHttpRetrofit = new HttpRetrofit();
        }
        return sHttpRetrofit;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    private HttpRetrofit() {
        HttpSSlSocket.SSLParams sslParams = HttpSSlSocket.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new CustomLogInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(GlobalConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void setListener(OnRequestErrorListener listener) {
        this.listener = listener;
    }

    public OnRequestErrorListener getListener() {
        return listener;
    }
}
