package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.ui.form.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_ALL_USER_AUDITS = "SELECT yr.amended_at, yr.amended_by, ua.authority_id, ua.revtype, ua.username" +
            " FROM users_audit ua" +
            " JOIN yazaki_revision yr on yr.id = ua.rev";

    Optional<User> findOneByUsername(final String username);

    @Query(value = FIND_ALL_USER_AUDITS, nativeQuery = true)
    List<UserAudit> findAllUserAudits();
}
