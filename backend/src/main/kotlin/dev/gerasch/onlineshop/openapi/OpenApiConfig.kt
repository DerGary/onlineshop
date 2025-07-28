package dev.gerasch.onlineshop.openapi

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val securityScheme =
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")

        val securityRequirement = SecurityRequirement().addList("bearerAuth")

        return OpenAPI()
                .info(
                        Info().title("Online Shop API")
                                .version("1.0")
                                .description("Documentation of the REST API of this online shop.")
                )
                .components(Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
    }
}
