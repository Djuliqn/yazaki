package com.yazaki.yazaki.domain.service.role;

import com.yazaki.yazaki.domain.model.Role;
import com.yazaki.yazaki.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Transactional
public class DefaultRoleService implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public DefaultRoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(final Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role findRoleByAuthority(final String authority) {
        return roleRepository.findOneByAuthority(authority);
    }

    @Override
    public void saveRole(final Role role) {
        roleRepository.save(role);
    }
}
