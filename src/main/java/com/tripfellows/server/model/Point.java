package com.tripfellows.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
public class Point implements Serializable {
    private Integer id;

    private Double x;

    private Double y;

    private String address;
}
