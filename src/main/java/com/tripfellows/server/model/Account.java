package com.tripfellows.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Account extends IdModel {
    private String uid;
    private String name;
    private String phoneNumber;

    public Account(String uid, String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
