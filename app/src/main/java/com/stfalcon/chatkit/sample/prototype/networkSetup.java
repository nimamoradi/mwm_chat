package com.stfalcon.chatkit.sample.prototype;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class networkSetup {
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);


        return okhttpClientBuilder.build();
    }
}
