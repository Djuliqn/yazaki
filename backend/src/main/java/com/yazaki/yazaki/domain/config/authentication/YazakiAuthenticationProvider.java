package com.yazaki.yazaki.domain.config.authentication;

import com.yazaki.yazaki.domain.exception.UserException;
import com.yazaki.yazaki.domain.exception.YazakiException;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class YazakiAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public YazakiAuthenticationProvider(UserService userService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication.getName() == null || authentication.getCredentials() == null) {
            return new UsernamePasswordAuthenticationToken(null, null, null);
        }

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final User foundUser = (User) userService.loadUserByUsername(username);

        if(!bCryptPasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new UserException("Грешна парола.");
        }

        return new UsernamePasswordAuthenticationToken(
                foundUser, foundUser.getPassword(), foundUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
