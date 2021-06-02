package com.pw.lrs.infrastructure.adapters.auth0;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class Auth0AccessToken {
    private String access_token;
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }
}
