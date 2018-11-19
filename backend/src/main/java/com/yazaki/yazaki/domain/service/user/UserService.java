package com.yazaki.yazaki.domain.service.user;

import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.ui.form.UserAudit;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void saveUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

    User findById(Long id);

    List<UserAudit> findAllUserAudits();
}
