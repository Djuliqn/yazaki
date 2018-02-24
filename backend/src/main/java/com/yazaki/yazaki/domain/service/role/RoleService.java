package com.yazaki.yazaki.domain.service.role;

import com.yazaki.yazaki.domain.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role findRoleById(final Long id);

    Role findRoleByAuthority(final String authority);

    void saveRole(final Role role);

}
