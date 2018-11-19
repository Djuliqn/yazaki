package com.yazaki.yazaki.ui.form;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserAuditForm {

    private LocalDate amended_at;

    private String username;

    private String operation;

    private String role;

    private String amended_by;


    public LocalDate getAmended_at() {
        return amended_at;
    }

    public void setAmended_at(LocalDate amended_at) {
        this.amended_at = amended_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAmended_by() {
        return amended_by;
    }

    public void setAmended_by(String amended_by) {
        this.amended_by = amended_by;
    }
}
