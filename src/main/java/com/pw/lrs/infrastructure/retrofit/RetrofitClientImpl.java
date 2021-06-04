package com.pw.lrs.infrastructure.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitClientImpl implements RetrofitClient {
    @Override
    public Retrofit getRetrofitClient(String url) {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    }
}
