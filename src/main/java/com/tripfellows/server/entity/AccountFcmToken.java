package com.tripfellows.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account_fcm_token")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AccountFcmToken {

    @Id
    @Column(name = "account_id", nullable = false, unique = true)
    private Integer accountId;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;
}
