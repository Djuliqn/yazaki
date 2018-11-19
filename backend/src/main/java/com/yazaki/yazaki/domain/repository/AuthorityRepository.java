package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findOneByName(final String authorityName);
}
