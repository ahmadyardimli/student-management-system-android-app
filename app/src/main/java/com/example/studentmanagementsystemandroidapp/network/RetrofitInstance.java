package com.example.studentmanagementsystemandroidapp.network;

import android.content.Context;

import com.example.studentmanagementsystemandroidapp.auth.TokenStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    // macOS tip: run `ipconfig getifaddr en0` in Terminal to get your laptop’s Wi-Fi IP for BASE_URL.
    public static final String BASE_URL = "http://192.168.0.123:8080/";
//    public static final String BASE_URL = "http://<YOUR_LAPTOP_LAN_IP>:8080/";

    private static volatile Retrofit retrofit;

    public static synchronized Retrofit getRetrofitInstance(Context ctx) {
        if (retrofit == null) {
            TokenStore store = new TokenStore(ctx.getApplicationContext());

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(store))
                    .authenticator(new TokenAuthenticator(store, BASE_URL, ctx))
                    .callTimeout(300, TimeUnit.SECONDS)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    // Call after login, logout, or when changing auth behavior
    public static synchronized void reset(Context ctx) {
        retrofit = null;
        getRetrofitInstance(ctx);
    }
}