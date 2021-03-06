package com.company.common.config;

import com.company.common.security.JwtAuthenticationFilter;
import com.company.common.security.JwtAuthorizationFilter;
import com.company.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@ComponentScan("com.company")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetails;

    private static final String[] CSRF_IGNORE = {"/signin/**", "/signup/**","/user/login"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        new CookieCsrfTokenRepository();
        http.cors()
                .and()
              //  .addFilterAfter(new CustomCsrfFilter(), CsrfFilter.class)
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.csrfTokenRepository(csrfTokenRepository())
                .ignoringAntMatchers(CSRF_IGNORE)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
      /*  http
                .cors().and().csrf().disable()
               *//*  .and().addFilterAfter(new CustomCsrfFilter(), CsrfFilter.class)
                .csrf()
                .ignoringAntMatchers(CSRF_IGNORE)
                .csrfTokenRepository(csrfTokenRepository())*//*

                .authorizeRequests()
                .antMatchers( "/user/login").permitAll()
                .anyRequest().authenticated() ;
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(),getApplicationContext()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));*/
      /*  http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(/*Arrays.asList("*")*/"http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Origin","Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization","X-XSRF-TOKEN"
                ));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetails);
    }
}