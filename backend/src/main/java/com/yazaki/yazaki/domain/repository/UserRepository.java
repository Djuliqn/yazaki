package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(final String username);
}
