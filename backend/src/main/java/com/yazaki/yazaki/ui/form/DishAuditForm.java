package com.yazaki.yazaki.ui.form;

import java.time.LocalDate;

public class DishAuditForm {

    private LocalDate amended_at;

    private String username;

    private String operation;

    private String description;

    private String name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
