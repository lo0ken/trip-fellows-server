package com.tripfellows.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder(toBuilder = true)
public class Trip implements Serializable {
    private Integer id;
    private Point startPoint;
    private Point endPoint;
    private BigDecimal price;
    private Account creator;
    private LocalDateTime createDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String comment;
    private TripStatus status;
    private Integer placesCount;
    private List<TripMember> members;
}
