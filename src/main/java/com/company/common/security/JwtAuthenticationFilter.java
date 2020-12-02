package com.company.common.security;

//import com.jakublesko.jwtsecurity.constants.SecurityConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.dto.LoginRequestDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }
    private String jsonUsername;
    private String jsonPassword;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            try {
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                //json transformation
                ObjectMapper mapper = new ObjectMapper();
                LoginRequestDTO loginRequest = mapper.readValue(sb.toString(), LoginRequestDTO.class);

                this.jsonUsername = loginRequest.getUserName();
                this.jsonPassword = loginRequest.getPassword();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object authenticationToken = new UsernamePasswordAuthenticationToken(jsonUsername, jsonPassword);
        return authenticationManager.authenticate((Authentication) authenticationToken);
       /* Object username = request.getParameter(SecurityConstants.PARAM_USERNAME);
        Object password = request.getParameter(SecurityConstants.PARAM_PASS);
        Object authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
       return authenticationManager.authenticate((Authentication) authenticationToken);*/
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        com.company.domain.User user =(com.company.domain.User) authentication.getPrincipal();
        ObjectMapper mapper =new ObjectMapper();
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(SecurityConstants.TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("roleName", user.getAuthorities()).claim("userName",user.getUsername())
                .compact();
        String refreshToken=createRefreshToken(user.getUsername());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        tokenMap.put("refreshToken", refreshToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);
        clearAuthenticationAttributes(request);
    }
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    public String  createRefreshToken(String username) {
        if (username==null||username.isEmpty()) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("scopes", Arrays.asList("USER"));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                .compact();
        return token;
    }
}
