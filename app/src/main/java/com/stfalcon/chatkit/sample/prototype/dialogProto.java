package com.stfalcon.chatkit.sample.prototype;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.responseModel.LoginData;
import com.stfalcon.chatkit.sample.responseModel.dialogData;
import com.stfalcon.chatkit.sample.staticData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface dialogProto {

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("dialog/getDialog?")
    Call<List<Dialog>> getDialogs(@Query("uid") String uid, @Query("timestamp") String time);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("dialog/getAllChats?")
    Call<List<User>> getAllChats(@Query("uid") String uid);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("dialog/newDialog?")
    Call<Dialog> newDialog(@Query("uid") String uid, @Query("timestamp") String time,
                           @Query("newDialog")  Dialog newDialog);

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(staticData.serverAddress)
            .client((new networkSetup()).provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
