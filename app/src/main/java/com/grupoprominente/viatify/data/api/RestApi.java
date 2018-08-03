package com.grupoprominente.viatify.data.api;

import com.grupoprominente.viatify.constants.RestApiConstants;
import com.grupoprominente.viatify.model.User;
import com.grupoprominente.viatify.model.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    private static RestApi restApi;

    private RestApi()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApi getInstance()
    {
        if(restApi == null)
            restApi = new RestApi();

        return restApi;
    }
    public LoginResponse login(User user)
    {
        LoginResponse loginResponse = null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<LoginResponse> loginCall = apiService.login(user.getGrant_type(),user.getUserName(), user.getPassword());
            loginResponse = loginCall.execute().body();
        }
        catch(Exception e)
        {

        }

        return loginResponse;
    }
    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl(RestApiConstants.API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
