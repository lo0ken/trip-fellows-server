package com.tripfellows.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "trip")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_point_id")
    private PointEntity startPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_point_id")
    private PointEntity endPoint;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private AccountEntity creator;

    @Column(name = "create_dt", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "comment", length = 500)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private TripStatusEntity status;

    @Column(name = "places_count")
    private Integer placesCount;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private Set<TripAccountEntity> tripToAccounts;
}
