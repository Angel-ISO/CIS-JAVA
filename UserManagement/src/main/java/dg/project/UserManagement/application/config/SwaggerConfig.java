package dg.project.UserManagement.application.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "User Management API",
                version = "1.0",
                description = "API for managing users and roles"
        ), servers = {
        @Server(
                description = "DEV SERVER",
                url = "http://localhost:6969"
        ),
        @Server(
                description = "PROD SERVER",
                url = "http://DigitalGansters:8080"
        )
},
        security = @SecurityRequirement(name = "Bearer Authentication")

)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        paramName = HttpHeaders.AUTHORIZATION
)
public class SwaggerConfig {
}