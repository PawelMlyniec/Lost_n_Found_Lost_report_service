package com.pw.lrs.infrastructure.retrofit;

import retrofit2.Retrofit;

public interface RetrofitClient {
    Retrofit getRetrofitClient(String url);
}
