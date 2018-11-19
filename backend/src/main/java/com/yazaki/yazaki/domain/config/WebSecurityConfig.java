package com.yazaki.yazaki.domain.config;

import com.yazaki.yazaki.domain.config.authentication.*;
import com.yazaki.yazaki.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CorsConfigurationSource corsConfigurationSource;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private YazakiAuthenticationProvider yazakiAuthenticationProvider;
    private LogoutSuccess logoutSuccess;


    @Autowired
    public WebSecurityConfig(UserService userService,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             CorsConfigurationSource corsConfigurationSource,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             JwtAuthenticationFilter jwtAuthenticationFilter,
                             AuthenticationSuccessHandler authenticationSuccessHandler,
                             AuthenticationFailureHandler authenticationFailureHandler,
                             YazakiAuthenticationProvider yazakiAuthenticationProvider,
                             LogoutSuccess logoutSuccess) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.yazakiAuthenticationProvider = yazakiAuthenticationProvider;
        this.logoutSuccess = logoutSuccess;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(yazakiAuthenticationProvider);
            //.userDetailsService(userService).
            //.passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .cors()
                    .configurationSource(corsConfigurationSource)
            .and()
                //.csrf()
                    //.ignoringAntMatchers("/user/login", "/user/register")
                    //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
            .and()
                .formLogin()
                    .loginPage("/user/login")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
            .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessHandler(logoutSuccess)
                    .deleteCookies(TOKEN_COOKIE)
            .and()
                .csrf().disable();
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, "/user/register");
    }
}
