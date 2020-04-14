package com.example.grabnotifications;

import android.content.res.Resources;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StringPostAPI {

    @Headers("Content-Type: application/json")
    @POST("v1/fnb")
    Call<String> submit(@Body String body);

    @GET("v1/fnb")
    Call<List<Transaction>> getTransactions(@Header("x-api-key") String api_key);

    @Headers("Content-Type: application/json")
    @POST("v1/trans")
    Call<Transaction> update(@Body Transaction body);

    @Headers("Content-Type: application/json")
    @POST("v1/newtrans")
    Call<Transaction> create(@Body Transaction body);
}
