package com.danbi_000.waitix;

import android.support.v4.util.Pair;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by danbi_000 on 2016-10-16.
 */


public class ServerNetworkManager {

//    public static final String HOST = "112.175.184.64"; //구입
    public static final String HOST = "192.168.0.6"; //카페 에스투
//    public static final String HOST = "192.168.43.247"; //단비파이
//    public static final String HOST = "172.17.8.8"; //학교
//    public static final String HOST = "192.168.0.211"; //오버더문


    private OkHttpClient client;
    public CookieManager cookieManager;

    private static ServerNetworkManager instance;

    public static ServerNetworkManager newInstance() {
        if (instance == null) {
            instance = new ServerNetworkManager();
            return instance;
        }
        return instance;
    }

    public ServerNetworkManager() {
        cookieManager  = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    public void post(String url, List<Pair<String, String>> parameters, Callback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        for (Pair<String, String> parameter : parameters) {
            formBody.add(parameter.first, parameter.second);
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(HOST)
                .port(8080)
                .addPathSegment(url).build();
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .post(formBody.build())
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void get(String url, List<Pair<String, String>> parameters, Callback callback) {
        HttpUrl.Builder httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(HOST)
                .port(8080)
                .addPathSegment(url);
        for (Pair<String, String> parameter : parameters) {
            httpUrl.addQueryParameter(parameter.first, parameter.second);
        }

        try {
            Request request = new Request.Builder()
                    .url(httpUrl.build())
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
