package wf.garnier.demo.spring.security.new64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .webAuthn(passkeys -> {
                    passkeys.rpName("Faros Demo")
                            .rpId("localhost")
                            .allowedOrigins("http://localhost:8080");
                })
                .oneTimeTokenLogin(ott -> {
                    var delegate = new RedirectOneTimeTokenGenerationSuccessHandler("/login/ott");
                    ott.tokenGenerationSuccessHandler(
                            (request, response, oneTimeToken) -> {
                                // TODO: send email with token
                                System.out.println("📧 Token was sent by email! " + oneTimeToken.getTokenValue());
                                delegate.handle(request, response, oneTimeToken);
                            }
                    );
                })
                .build();
    }
}
