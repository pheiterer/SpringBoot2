package springboot2.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import springboot2.service.DevDojoUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
    private final DevDojoUserDetailsService devDojoUserDetailsService;

    /**
     * BasicAuthenticationFilter
     * UsernamePasswordAuthenticatorFilter
     * DefaultLoginPageGeneratingFilter
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor
     * Authentication -> Authorization
     *
     * @param http
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeHttpRequests()
                .requestMatchers("/animes/admin/**").hasRole("ADMIN")
                .requestMatchers("/animes/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = createDelegatingPasswordEncoder();
//        log.info("Password encoded {}", encoder.encode("12345"));
//        UserDetails user = User.withUsername("clay")
//                    .password(encoder.encode("12345"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}