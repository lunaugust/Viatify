package com.grupoprominente.viatify.data.api;

import com.grupoprominente.viatify.model.User;
import com.grupoprominente.viatify.model.LoginResponse;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("grant_type") String grantType, @Field("userName") String userName, @Field("password") String password);
}
