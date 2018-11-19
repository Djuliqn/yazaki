package com.yazaki.yazaki.domain.service.authority;

import com.yazaki.yazaki.domain.model.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> getAllAuthorities();

    void saveAuthority(Authority authority);

    Authority findAuthorityByName(String name);
}
