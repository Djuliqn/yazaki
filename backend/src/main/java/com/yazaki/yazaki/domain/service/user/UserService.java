package com.yazaki.yazaki.domain.service.user;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yazaki.yazaki.domain.model.User;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User findUserById(final Long id);

    void saveUser(final User user);

    void deleteUser(final User user);

    void updateUser(final User user);
}
