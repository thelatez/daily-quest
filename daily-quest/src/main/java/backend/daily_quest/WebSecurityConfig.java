package backend.daily_quest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	private UserDetailsService userDetailsService; // type of attribute -> interface

    public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests( authorize -> authorize       
                .requestMatchers("/css/**", "/register", "/error").permitAll()
                .requestMatchers("/edit/**", "/delete/**", "/newdaily").authenticated()   
                .anyRequest().authenticated()                 
            )                                             
        .formLogin( formlogin -> formlogin                   
            .loginPage("/login")                          
            .defaultSuccessUrl("/dailylist", true)      
            .permitAll()                                  
        )
        .logout( logout -> logout
            .permitAll()
        );
        return http.build();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
