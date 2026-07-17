package com.codewithmosh.springecommerce.security;

import com.codewithmosh.springecommerce.entity.Role;
import com.codewithmosh.springecommerce.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(
                "ThisIsATestOnlySecretKeyThatIsLongEnoughForHS256Algorithms!!",
                3600000L
        );
    }

    @Test
    void generateToken_containsEmailSubjectAndRoleClaim() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("encoded")
                .role(Role.CUSTOMER)
                .build();

        String token = jwtService.generateToken(user);
        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUsername(token)).isEqualTo("john@example.com");
        assertThat(role).isEqualTo("CUSTOMER");
        assertThat(jwtService.isTokenValid(token, user)).isTrue();
        assertThat(jwtService.isTokenExpired(token)).isFalse();
    }
}
