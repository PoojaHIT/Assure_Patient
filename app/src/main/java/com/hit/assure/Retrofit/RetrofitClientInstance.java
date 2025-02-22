package com.hit.assure.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.readTimeout(Constant.TimeOut.SOCKET_TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(Constant.TimeOut.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(Constant.UrlPath.BASE_URL_SUREPASS_FACE_MATCH)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
