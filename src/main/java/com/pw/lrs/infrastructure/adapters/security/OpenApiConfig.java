package com.pw.lrs.infrastructure.adapters.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "auth0",
    in = SecuritySchemeIn.HEADER,
    type = SecuritySchemeType.APIKEY,
    scheme = "basic"
)
@OpenAPIDefinition(
    info = @Info(title = "Lost Report Service API", version = "v1"),
    security = @SecurityRequirement(name = "auth0")
)
public class OpenApiConfig {
}
