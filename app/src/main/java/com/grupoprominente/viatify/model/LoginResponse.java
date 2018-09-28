package com.grupoprominente.viatify.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LoginResponse implements Serializable {
    @SerializedName("access_token")
    private String token;
    @SerializedName("token_type")
    private String token_type;
    @SerializedName("expires_in")
    private int expires_in;
    @SerializedName("date")
    private Date date;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
