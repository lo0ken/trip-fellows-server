package com.tripfellows.server.entity;

import com.tripfellows.server.enums.RoleCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private RoleCodeEnum code;

    @Column(name = "name", nullable = false)
    private String name;
}
