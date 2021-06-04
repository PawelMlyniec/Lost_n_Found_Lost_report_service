package com.pw.lrs.domain.user;

import com.pw.lrs.domain.user.User;
import com.pw.lrs.domain.user.UserId;

import java.util.Optional;

public interface AuthenticationProvider {
    Optional<User> getUser(UserId userId);
}
