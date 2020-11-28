package com.tripfellows.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
public class Point implements Serializable {
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String address;

    public Point(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(latitude, point.latitude) &&
                Objects.equals(longitude, point.longitude) &&
                Objects.equals(address, point.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, address);
    }
}
