package com.yazaki.yazaki.domain.model;

import lombok.Data;

@Data
public class UserTokenState {

    private String access_token;
    private Long expires_in;

    public UserTokenState() {
        this.access_token = null;
        this.expires_in = null;
    }

    public UserTokenState(String access_token, long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }
}
