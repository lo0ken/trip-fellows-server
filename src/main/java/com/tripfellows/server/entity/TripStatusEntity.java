package com.tripfellows.server.entity;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "trip_status")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TripStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private TripStatusCodeEnum code;

    @Column(name = "name", nullable = false)
    private String name;
}
