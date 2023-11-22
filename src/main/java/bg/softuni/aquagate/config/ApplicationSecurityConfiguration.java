package bg.softuni.aquagate.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                                .requestMatchers("/", "/about", "/identity/**", "/habitats/**", "/error")
                                .permitAll()

                                .requestMatchers("/logout", "/profile", "/topics/**", "/comments/**")
                                .authenticated()

                                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("MODERATOR")


                                .requestMatchers(new AntPathRequestMatcher("/admin/user-edit/**")).hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).formLogin(
                        formLogin -> formLogin
                                .loginPage("/identity/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/identity/login")
                ).logout(
                        logout ->
                                logout.logoutUrl("/logout")
                                        .logoutSuccessUrl("/about")
                                        .clearAuthentication(true)
                                        .invalidateHttpSession(true)

                ).exceptionHandling(
                        exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/login")
                );
        return httpSecurity.build();
    }
}
