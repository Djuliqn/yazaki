package com.yazaki.yazaki.domain.config.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private List<String> pathsToSkip = Arrays.asList("/user/login", "/user/logout", "/user/register");

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authToken = jwtTokenUtil.getToken(httpServletRequest);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null);
        if (authToken != null && !skipPathRequest(httpServletRequest, pathsToSkip)) {
            try {
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                // get user
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // create authentication
                JwtBasedAuthentication authentication = new JwtBasedAuthentication(userDetails);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean skipPathRequest(HttpServletRequest request, List<String> pathsToSkip) {
        Assert.notNull(pathsToSkip, "path cannot be null.");
        List<RequestMatcher> m = pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
