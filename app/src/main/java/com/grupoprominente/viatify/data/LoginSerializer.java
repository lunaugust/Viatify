package com.grupoprominente.viatify.data;

import android.content.Context;

import com.grupoprominente.viatify.model.LoginResponse;

public class LoginSerializer extends ObjectSerializer
{
    //User file name
    private final static String FILE_NAME = "Login.dat";

    //Singleton instance
    private static LoginSerializer loginSerializer;

    private LoginSerializer()
    {
        //Prevent initialization from the reflection api.
        if(loginSerializer != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized LoginSerializer getInstance()
    {
        if(loginSerializer == null)
            loginSerializer = new LoginSerializer();

        return loginSerializer;
    }

    public void save(Context context, LoginResponse login)
    {
        save(context, FILE_NAME, login);
    }

    public LoginResponse load(Context context)
    {
        return (LoginResponse) load(context, FILE_NAME);
    }

    public void clear(Context context)
    {
        save(context, null);
    }
}
