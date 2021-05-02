package com.pw.lrs.domain.ports.outgoing;

import retrofit2.Retrofit;

public interface RetrofitClient {
    public Retrofit getRetrofitClient(String url);
}
