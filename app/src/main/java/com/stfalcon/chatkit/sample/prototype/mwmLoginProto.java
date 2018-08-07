package com.stfalcon.chatkit.sample.prototype;

import com.stfalcon.chatkit.sample.responseModel.LoginData;
import com.stfalcon.chatkit.sample.staticData;

import java.lang.reflect.Array;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface mwmLoginProto {


    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("login/signIn?")
    Call<LoginData> login(@Query("uid") int uid, @Query("pwd") int pwd);


    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(staticData.serverAddress)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
