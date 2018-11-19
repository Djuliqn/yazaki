package com.yazaki.yazaki.domain.service.authority;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.yazaki.yazaki.domain.exception.AuthorityException;
import com.yazaki.yazaki.domain.model.Authority;
import com.yazaki.yazaki.domain.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class DefaultAuthorityService implements AuthorityService {

    private static final String ERROR_MESSAGE = "Ролята не може да бъде празна.";

    private AuthorityRepository authorityRepository;

    @Autowired
    public DefaultAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public void saveAuthority(Authority authority) {
        try {
            Preconditions.checkNotNull(authority);
            if (Strings.isNullOrEmpty(authority.getAuthority())) {
                log.error("Authority name must not be null or empty.");
                throw new AuthorityException(ERROR_MESSAGE);
            }

            authorityRepository.save(authority);
        } catch (NullPointerException ex) {
            log.error("Saved authority must not be null or blank.");
            throw new AuthorityException(ERROR_MESSAGE);
        }
    }

    @Override
    public Authority findAuthorityByName(String name) {
        return authorityRepository.findOneByName(name);
    }
}
