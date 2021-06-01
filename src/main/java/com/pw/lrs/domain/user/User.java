package com.pw.lrs.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private final UserId userId;
    private final String firstName;
}
