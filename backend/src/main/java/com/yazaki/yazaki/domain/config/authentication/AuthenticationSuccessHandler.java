package com.yazaki.yazaki.domain.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.model.UserTokenState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);
        User user = (User) authentication.getPrincipal();

        String jws = jwtTokenUtil.generateToken(user.getUsername(), user.getAuthority().getAuthority());

        // Create token auth Cookie
        Cookie authCookie = new Cookie(TOKEN_COOKIE, (jws));

        authCookie.setHttpOnly(true);

        authCookie.setMaxAge(EXPIRES_IN);

        authCookie.setPath("/");
        // Add cookie to response
        response.addCookie(authCookie);

        // JWT is also in the response
        UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
        String jwtResponse = objectMapper.writeValueAsString(userTokenState);
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);

    }
}
