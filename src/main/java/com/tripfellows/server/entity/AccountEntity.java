package com.tripfellows.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uid", nullable = false, length = 500)
    private String uid;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 500)
    private String phoneNumber;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<TripAccountEntity> accountToTrips;
}
