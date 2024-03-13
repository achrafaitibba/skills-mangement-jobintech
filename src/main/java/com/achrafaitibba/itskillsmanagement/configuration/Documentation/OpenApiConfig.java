package com.achrafaitibba.itskillsmanagement.configuration.Documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Achraf Ait Ibba",
                        email = "aitibbaachraf@gmail.com",
                        url = "https://achrafaitibba.com"
                ),
                description = "<b>Documentation for 'Skill Management' API:</b> <br>",
                title = "Skill Management API",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url = "https://github.com/achrafaitibba/where_is_my_money/blob/master/LICENSE.md"
                )
        ),
        servers = {
                @Server(
                        description = "PROD/test ENV",
                        url = "https://wmm.up.railway.app"
                ),
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}