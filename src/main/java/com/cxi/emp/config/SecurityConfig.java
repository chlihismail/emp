package com.cxi.emp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cxi.emp.entity.User;
import com.cxi.emp.service.UserDetailsServiceImp;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final UserDetailsServiceImp userDetailsServiceImp;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/signup", "/verify-email", "/signin", "/signout", "/api/signup", "/api/verify-email", "/api/signin", "/api/signout", "/api/events").permitAll()
                .requestMatchers("/event-form", "/delete-event/**", "/api/organizer").hasRole("ORGANIZER")
                .requestMatchers("/book-event/**","/checkout", "/success", "/api/participant").hasRole("PARTICIPANT")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .failureUrl("/signin?error=true")
                 .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll())
            ;

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            User user = (User) authentication.getPrincipal();
            if (Boolean.TRUE.equals(user.getEmailVerified())) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/signin?error=emailNotVerified");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImp);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


}

