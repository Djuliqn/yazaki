package com.yazaki.yazaki.domain.config.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

public class YazakiRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        YazakiRevision yazakiRevision = (YazakiRevision) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            yazakiRevision.setUsername(authentication.getName());
        } else {
            yazakiRevision.setUsername("Джулиян");
        }

        yazakiRevision.setLocalDate(LocalDate.now());
    }
}
