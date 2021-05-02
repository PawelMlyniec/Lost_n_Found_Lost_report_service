package com.pw.lrs.domain.ports.outgoing;

import com.pw.lrs.infrastructure.adapters.auth0.Auth0AccessToken;
import com.pw.lrs.infrastructure.adapters.auth0.Auth0AccessTokenBody;
import com.pw.lrs.infrastructure.adapters.auth0.Auth0User;
import retrofit2.Call;
import retrofit2.http.*;

public interface Auth0Service {
    @Headers({"Content-Type: application/json"})
    @POST("/oauth/token")
    Call<Auth0AccessToken> getAccessToken(@Body Auth0AccessTokenBody body);
    @GET("/api/v2/users/{id}")
    Call<Auth0User> getUser(@Header("Authorization") String authorization, @Path("id") String id);

}
