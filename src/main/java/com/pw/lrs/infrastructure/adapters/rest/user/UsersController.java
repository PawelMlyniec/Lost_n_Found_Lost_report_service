package com.pw.lrs.infrastructure.adapters.rest.user;

import com.pw.lrs.domain.ports.outgoing.AuthenticationProvider;
import com.pw.lrs.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final AuthenticationProvider authenticationProvider;

    @GetMapping("/{id}/name")
    String getUserName(@PathVariable String id) {

        return authenticationProvider.getUser(UserId.of(id))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
            .firstName();
    }
}
