package dev.gerasch.onlineshop.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@Bean
fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
    val converter = JwtAuthenticationConverter()
    converter.setJwtGrantedAuthoritiesConverter { jwt ->
        val roles = jwt.getClaimAsMap("realm_access").get("roles") as? List<*>
        roles?.map { SimpleGrantedAuthority("ROLE_${it.toString()}") } ?: emptyList()
    }
    return converter
}

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .authorizeHttpRequests { it.anyRequest().permitAll() }
                .oauth2ResourceServer {
                    it.jwt { jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()) }
                }
                .build()
    }
}

@EnableMethodSecurity(prePostEnabled = true) @Configuration class MethodSecurityConfig {}
