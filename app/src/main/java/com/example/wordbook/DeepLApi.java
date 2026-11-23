package com.example.wordbook;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DeepLApi {
    @POST("v2/translate")
    Call<DeepLResponse> translate(
            @Header("Authorization") String authorization,
            @Body DeepLRequest request
    );
}