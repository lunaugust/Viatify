package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grupoprominente.viatify.data.LoginSerializer;
import com.grupoprominente.viatify.model.LoginResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginResponse lResponse = LoginSerializer.getInstance().load(SplashActivity.this);
        if (lResponse != null) {
            Date date = lResponse.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 29);
            long diff  = cal.getTime().getTime() - new Date().getTime();
            long days = TimeUnit.DAYS.convert(diff , TimeUnit.MILLISECONDS);
            if(days > 1){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
        else{
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }


    }
}
