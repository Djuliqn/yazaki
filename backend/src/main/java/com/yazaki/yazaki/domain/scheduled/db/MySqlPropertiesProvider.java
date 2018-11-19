package com.yazaki.yazaki.domain.scheduled.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MySqlPropertiesProvider {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${app.name}")
    private String dbName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }
}
