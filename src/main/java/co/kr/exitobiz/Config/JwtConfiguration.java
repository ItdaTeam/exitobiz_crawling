package co.kr.exitobiz.Config;

import co.kr.exitobiz.jwt.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtAuthTokenProvider jwtAuthTokenProvider() {
        return new JwtAuthTokenProvider(secret);
    }

}
