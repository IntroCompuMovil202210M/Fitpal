package com.gitgud.fitpal.Notifications;


import com.gitgud.fitpal.Notifications.MyResponse;
import com.gitgud.fitpal.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAdUFMPKY:APA91bE9j48fQPy_jHlBLMyu-SenLgjsBUBJ6eUuJNa8MeYQ_mqD6Jon8bBaZ7-N4MBumlwMas2ShQq8JD516flQpBG6xbzbzj1bzYoX8rlL3lq8XHeyHGhyk-GJZ8lai1NAjyUblvLW"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
