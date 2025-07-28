package dev.gerasch.onlineshop.users

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.StandardClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getGretting(auth: JwtAuthenticationToken): UserInfoDto {
        return UserInfoDto(
                auth.getToken().getClaimAsString(StandardClaimNames.SUB),
                auth.getToken().getClaimAsString(StandardClaimNames.EMAIL),
                auth.getToken().getClaimAsString(StandardClaimNames.GIVEN_NAME),
                auth.getToken().getClaimAsString(StandardClaimNames.FAMILY_NAME),
                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        )
    }

    data class UserInfoDto(
            val userId: String,
            val email: String,
            val givenName: String,
            val familyName: String,
            val roles: List<String>
    )
}

@RestController
class AdminController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    fun get(auth: JwtAuthenticationToken): String {
        return "du bist admin"
    }
}
