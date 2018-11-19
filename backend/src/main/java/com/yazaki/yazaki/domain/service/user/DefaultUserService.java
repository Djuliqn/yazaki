package com.yazaki.yazaki.domain.service.user;

import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.exception.UserException;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.repository.AuthorityRepository;
import com.yazaki.yazaki.domain.repository.UserRepository;
import com.yazaki.yazaki.ui.form.UserAudit;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class DefaultUserService implements UserService {

    private static final String DEFAULT_USER_ROLE = "ROLE_USER";

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        try {
            user.setAuthority(authorityRepository.findOneByName(DEFAULT_USER_ROLE));
            userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            log.error("Username exists exception.");
            throw new UserException("Съществува запис с това име.");
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            Assert.notNull(user);
            return userRepository.save(user);
        } catch(DataIntegrityViolationException ex) {
            log.error("Username exists exception.");
            throw new UserException("Съществува запис с това име.");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException | IllegalArgumentException ex) {
            throw new RecordNotFoundException("Потребителят не е намерен.");
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<UserAudit> findAllUserAudits() {
        return userRepository.findAllUserAudits();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByUsername(username);

        user.orElseThrow(() -> new UserException("Грешно потребителско име."));

        return user.get();
    }
}
