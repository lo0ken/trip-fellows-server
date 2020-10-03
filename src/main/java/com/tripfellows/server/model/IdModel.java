package com.tripfellows.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public abstract class IdModel {
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdModel that = (IdModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
