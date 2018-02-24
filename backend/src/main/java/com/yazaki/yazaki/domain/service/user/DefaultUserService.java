package com.yazaki.yazaki.domain.service.user;

import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.exception.UsernameExistsException;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.repository.UserRepository;
import com.yazaki.yazaki.domain.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Primary
@Transactional
public class DefaultUserService implements UserService {

    private static final String DEFAULT_USER_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public DefaultUserService(final UserRepository userRepository, final RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(final Long id) {
        return executeOperation(id, userRepository::findOne, new RecordNotFoundException());
    }

    @Override
    public void saveUser(final User user) {
        user.setRole(roleService.findRoleByAuthority(DEFAULT_USER_ROLE));
        executeOperation(user, userRepository::save);
    }

    @Override
    public void deleteUser(final User user) {
        executeOperation(user, userRepository::delete);
    }

    @Override
    public void updateUser(final User user) {
        executeOperation(user, userRepository::save);
    }


    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findOneByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Грешна самоличност.");
        }

        return user;
    }

    private void executeOperation(final User user, final Consumer<User> block) {
        try {
            block.accept(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UsernameExistsException(exception);
        } catch (InvalidDataAccessApiUsageException exception) {
            throw new RecordNotFoundException(exception);
        }
    }

    private User executeOperation(final Long id, final Function<Long, User> block,
                                  final RuntimeException throwedException) {
        try {
            return block.apply(id);
        } catch (Exception exception) {
            throw throwedException;
        }
    }
}
