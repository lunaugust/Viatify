package com.grupoprominente.viatify.network;

public class ApiInterface {
    @GET("inbox.json")
    Call<List<Message>> getInbox();


}
