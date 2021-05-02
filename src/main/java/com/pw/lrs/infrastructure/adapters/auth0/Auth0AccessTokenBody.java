package com.pw.lrs.infrastructure.adapters.auth0;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class Auth0AccessTokenBody {
    private String client_id;
    private String client_secret;
    private String audience;
    private String grant_type;
}
