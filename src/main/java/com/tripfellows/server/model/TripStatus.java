package com.tripfellows.server.model;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TripStatus {
    private Integer id;
    private TripStatusCodeEnum code;
    private String name;

    public TripStatus(TripStatusCodeEnum code, String name) {
        this.code = code;
        this.name = name;
    }
}
