package com.danbi_000.waitix;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by danbi_000 on 2016-10-16.
 */


public class ServerNetworkManager<T> {

/*
    PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance());
    CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
    .cookieJar(new JavaNetCookieJar(cookieManager))
            .build();
    */

    private T service;
    private String baseUrl = "https://127.0.0.1/";

   public T getClient(Class<? extends T> type){
       if(service == null) {
           OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
               @Override
               public Response intercept(Chain chain) throws IOException {

                   Request original = chain.request();

                   Request request = original.newBuilder()
                           .header("ex-hader", "sample")
                           .method(original.method(), original.body())
                           .build();

                   return chain.proceed(request);
               }
           }).build();

           Retrofit client = new Retrofit.Builder()
                   .baseUrl(baseUrl)
                   .client(okHttpClient)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           service = client.create(type);
//           APIService service = retrofit.create(APIService.class);

       }
       return service;
       }
}
