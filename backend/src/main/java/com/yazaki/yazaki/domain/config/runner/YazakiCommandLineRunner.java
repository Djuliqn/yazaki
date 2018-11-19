package com.yazaki.yazaki.domain.config.runner;

import com.yazaki.yazaki.domain.model.Authority;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.repository.UserRepository;
import com.yazaki.yazaki.domain.service.authority.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

public class YazakiCommandLineRunner {

    @Order(1)
    @Component
    private static class RoleCommandLineRunner implements CommandLineRunner {

        private final AuthorityService authorityService;

        private static final String ROLE_ADMIN = "ROLE_ADMIN";
        private static final String ROLE_USER = "ROLE_USER";
        private static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

        private static final Authority ADMIN = new Authority(ROLE_ADMIN);
        private static final Authority USER = new Authority(ROLE_USER);
        private static final Authority EMPLOYEE = new Authority(ROLE_EMPLOYEE);

        @Autowired
        public RoleCommandLineRunner(final AuthorityService authorityService) {
            this.authorityService = authorityService;
        }

        @Override
        public void run(String... args) {
            if (Objects.isNull(authorityService.findAuthorityByName(ROLE_ADMIN))) {
                authorityService.saveAuthority(ADMIN);
            }

            if (Objects.isNull(authorityService.findAuthorityByName(ROLE_USER))) {
                authorityService.saveAuthority(USER);
            }

            if (Objects.isNull(authorityService.findAuthorityByName(ROLE_EMPLOYEE))) {
                authorityService.saveAuthority(EMPLOYEE);
            }
        }
    }

    @Order(2)
    @Component
    private static class UserCommandLineRunner implements CommandLineRunner {

        private final UserRepository userRepository;
        private final AuthorityService authorityService;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        private static final String USERNAME = "Джулиян";
        private static final String PASSWORD = "0898351444";
        private static final String ROLE_ADMIN = "ROLE_ADMIN";

        @Autowired
        public UserCommandLineRunner(final UserRepository userRepository, final AuthorityService authorityService,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.userRepository = userRepository;
            this.authorityService = authorityService;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        public void run(String... args) {
            if (!userRepository.findOneByUsername(USERNAME).isPresent()) {
                final User user = new User();
                user.setUsername(USERNAME);
                user.setPassword(bCryptPasswordEncoder.encode(PASSWORD));
                user.setAuthority(authorityService.findAuthorityByName(ROLE_ADMIN));
                userRepository.save(user);
            }
        }
    }
}
