package com.example.dragonhack.api;

import com.example.dragonhack.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ServiceGenerator {
    private static Retrofit.Builder sBuilder;
    private static OkHttpClient.Builder sHttpClient;
    private static Retrofit sRetrofit;

    static {
        init();
    }

    // Init of sHttpClient for API connection and calls
    private static void init() {
        sHttpClient = new OkHttpClient.Builder();
        sBuilder = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()); // TODO: add converter

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        sHttpClient.addInterceptor(httpLoggingInterceptor);

        sRetrofit = sBuilder.client (sHttpClient.build()).build();
        Timber.d("Retrofit built with base url: %s", sRetrofit.baseUrl().url().toString());
    }

    public static <S> S createService(Class<S> serviceClass) {
        return sRetrofit.create(serviceClass);
    }

}
