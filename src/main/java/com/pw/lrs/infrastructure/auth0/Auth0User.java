package com.pw.lrs.infrastructure.auth0;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class Auth0User {
    @JsonProperty
    @Nullable
    private String user_id;
    @JsonProperty
    @Nullable
    private String email;
    @JsonProperty
    @Nullable
    private Boolean email_verified;
    @JsonProperty
    @Nullable
    private String username;
    @JsonProperty
    @Nullable
    private Boolean blocked;
    @JsonProperty
    @Nullable
    private String name;
    @JsonProperty
    @Nullable
    private String given_name;
    @JsonProperty
    @Nullable
    private String family_name;
    @JsonProperty
    @Nullable
    private String nickname;
}
