package bg.softuni.aquagateclient.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                                .requestMatchers("/static/**", "/", "/about", "/identity/register",
                                        "/identity/login", "/identity/login-error", "/habitats/**", "/error")
                                .permitAll()

                                .requestMatchers("/logout", "/profile", "/topics/**", "/comments/**",
                                        "/admin/**")
                                .authenticated()

                                .requestMatchers("/admin/**").hasRole("MODERATOR")
                                .requestMatchers("/admin/user-edit/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                ).formLogin(
                        formLogin -> formLogin
                                .loginPage("/identity/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/identity/login-error")

                ).logout(
                        logout -> logout
                                .logoutUrl("/identity/logout")
                                .logoutSuccessUrl("/")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)

                ).exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .accessDeniedPage("/login")

                );

        return httpSecurity.build();
    }
}
