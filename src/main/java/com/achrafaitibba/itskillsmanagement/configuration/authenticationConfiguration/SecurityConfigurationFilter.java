package com.achrafaitibba.itskillsmanagement.configuration.authenticationConfiguration;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurationFilter {

    private final JwtAuthenticationFIlter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                /** to allow cors from all origins */
                .csrf()
                .disable()
                .cors()
                /////////////////
                .and()
                /** authorized endpoints: doesn't require authentication */
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/user/register",
                        "/api/v1/user/refresh-token",
                        "/api/v1/user/authenticate",
                        "/api/v1/questions",
                        "/swagger-ui/index.html#",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()
                //////////////////////
                /** ask authentication for any other request */
                .anyRequest()
                .authenticated()
                .and()
                ////////////////////////////
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /** enable frames, I used it for swagger documentation to put it inside an IFRAME tag */
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                        (request,
                         response,
                         authentication) ->
                        SecurityContextHolder.clearContext())
        ;
        return httpSecurity.build();
    }
}
