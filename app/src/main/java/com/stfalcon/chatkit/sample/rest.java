package com.stfalcon.chatkit.sample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nima on 7/29/2018.
 */

public interface rest {
    @GET("api/search/{item}")
    Call<List<String>> listRepos(@Path("user") String user);
}
