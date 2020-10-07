package com.tripfellows.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
public class TripMember implements Serializable {
    private Account account;
    private Role role;

    public TripMember(Account account, Role role) {
        this.account = account;
        this.role = role;
    }
}
