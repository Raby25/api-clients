package payetonkawa.api_clients;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // désactive CSRF pour les tests
                .authorizeRequests().anyRequest().permitAll(); // permet toutes les requêtes
        return http.build();
    }
}
