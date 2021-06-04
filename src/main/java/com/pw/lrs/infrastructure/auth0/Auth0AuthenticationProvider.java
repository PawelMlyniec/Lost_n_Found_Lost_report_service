package com.pw.lrs.infrastructure.auth0;

import com.pw.lrs.domain.user.AuthenticationProvider;
import com.pw.lrs.domain.user.User;
import com.pw.lrs.domain.user.UserId;
import com.pw.lrs.infrastructure.retrofit.RequestExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class Auth0AuthenticationProvider implements AuthenticationProvider {
    private final Auth0AccessTokenBody accessTokenBody;
    private final RequestExecutor requestExecutor;
    private final Auth0Api auth0Api;

    @Override
    public Optional<User> getUser(UserId userId) {
        var accessToken = getAccessToken();
        var response = requestExecutor.execute(auth0Api.getUser(buildTokenAuthorization(accessToken), userId.raw()));
        return mapUser(response.body());
    }

    private Optional<User> mapUser(Auth0User auth0User) {
        return Optional.ofNullable(auth0User)
            .map(auth0UserDefined -> User.builder()
                .firstName(auth0UserDefined.given_name())
                .userId(UserId.of(auth0UserDefined.user_id()))
                .build());
    }

    private Auth0AccessToken getAccessToken() {
        var accessTokenResponse = requestExecutor.execute(auth0Api.getAccessToken(accessTokenBody));
        return accessTokenResponse.body();
    }

    private String buildTokenAuthorization(Auth0AccessToken accessToken) {
        return accessToken.token_type() + " " + accessToken.getAccess_token();
    }
}
