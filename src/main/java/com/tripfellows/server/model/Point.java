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
    private Double x;
    private Double y;
    private String address;

    public Point(Double x, Double y, String address) {
        this.x = x;
        this.y = y;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(x, point.x) &&
                Objects.equals(y, point.y) &&
                Objects.equals(address, point.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, address);
    }
}
