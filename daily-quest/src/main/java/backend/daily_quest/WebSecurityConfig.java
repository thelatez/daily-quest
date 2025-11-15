package backend.daily_quest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // Required for Postman (otherwise POST/PUT/DELETE fail)
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/register", "/error").permitAll()
            // API needs authentication, but NOT form login redirect
            .requestMatchers("/api/**").authenticated()
            // Normal site pages
            .requestMatchers("/edit/**", "/delete/**", "/newdaily").authenticated()
            .anyRequest().authenticated()
        )
        // Enables FORM login for browser users
        .formLogin(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/dailylist", true)
            .permitAll()
        )
        // Enables BASIC auth for Postman users
        .httpBasic(Customizer.withDefaults())
        .logout(logout -> logout.permitAll());
    return http.build();
}
}
