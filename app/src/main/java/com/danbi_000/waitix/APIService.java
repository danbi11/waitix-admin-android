package com.danbi_000.waitix;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by danbi_000 on 2016-10-16.
 */

interface APIService {
    @POST("/store/cancel")
//    String cancelRequest(@Field("unum") String unum);
    Call<APIdata> getStoreid(@Query("unum") String unum);

    @GET("/store/current")
    String checkCurrentStatus(@Field("snum") int snum);
//    Call<APIdata> getStoreid(@Query("unum") String unum);

    @POST("/store/request")
    String offlineRequest(@Field("unum") String unum, @Field("snum") int snum,
                          @Field("pon") int pon);

    @GET("/store/about")
    String about(@Field("snum") int snum);

    @POST("/store/signup")
    String signup(@Field("storeid") String storeid, @Field("address") String address,
                  @Field("tel") String tel, @Field("text") String text,
                  @Field("imgsrc") String imgsrc, @Field("alarmtime") String alarmTime,
                  @Field("password") String passsword);
    Call<APIdata.signup> getStoreid(@Query("unum") String unum);


    @POST("/store/modify")
    String modify(@Field("storeid") String storeid, @Field("address") String address,
                  @Field("tel") String tel, @Field("text") String text,
                  @Field("imgsrc") String imgsrc, @Field("alarmtime") String alarmTime,
                  @Field("password") String passsword);
}

