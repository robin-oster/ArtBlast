package dev.ArtBlast.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(request -> request
            .requestMatchers("/posts/**")
            .hasRole("USER"))
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        User.UserBuilder users = User.builder();
        UserDetails robin = users
            .username("roster")
            .password(passwordEncoder.encode("abc123"))
            .roles("USER")
            .build();

            return new InMemoryUserDetailsManager(robin);
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }
}