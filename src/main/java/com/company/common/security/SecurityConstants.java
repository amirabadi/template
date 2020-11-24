package com.company.common.security;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/user/login";

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final String TOKEN_SUBJECT = "sub";
    public static final String PARAM_USERNAME="userName";
    public static final String PARAM_PASS="password";

    public static final long RefreshTokenExpTime=20;
}

