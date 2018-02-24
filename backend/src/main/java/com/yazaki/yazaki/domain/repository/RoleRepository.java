package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByAuthority(final String authority);
}
