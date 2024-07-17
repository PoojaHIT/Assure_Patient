package com.hit.assure.Retrofit;

import com.hit.assure.Util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBase {

    public static Retrofit getClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.readTimeout(Constant.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(Constant.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(Constant.UrlPath.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getClient_mxFace() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.readTimeout(Constant.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(Constant.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(Constant.UrlPath.BASE_URL_MXFACE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getClient_Videocl() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.readTimeout(Constant.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(Constant.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(Constant.UrlPath.BASE_URL_VIDEOCL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
