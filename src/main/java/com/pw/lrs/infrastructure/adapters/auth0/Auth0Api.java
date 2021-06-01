package com.pw.lrs.infrastructure.adapters.auth0;

import retrofit2.Call;
import retrofit2.http.*;

public interface Auth0Api {
    @Headers({"Content-Type: application/json"})
    @POST("/oauth/token")
    Call<Auth0AccessToken> getAccessToken(@Body Auth0AccessTokenBody body);
    @GET("/api/v2/users/{id}")
    Call<Auth0User> getUser(@Header("Authorization") String authorization, @Path("id") String id);
}
