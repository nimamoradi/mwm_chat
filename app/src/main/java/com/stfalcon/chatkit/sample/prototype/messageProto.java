package com.stfalcon.chatkit.sample.prototype;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.staticData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface messageProto {
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("message/getMessage?")
    Call<ArrayList<Message>> getMessage(@Query("uid") String uid, @Query("timestamp") String time,
                                        @Query("startIndex") int startIndex, @Query("endIndex") int endIndex);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("message/sendMessage?")
    Call<Message> sendMessage(@Query("uid") String uid, @Query("newMessage") Message newMessage);

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(staticData.serverAddress)
            .client((new networkSetup()).provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
