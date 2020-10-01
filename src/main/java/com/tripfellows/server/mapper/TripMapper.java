package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.model.Trip;
import org.mapstruct.Mapper;

@Mapper
public interface TripMapper {
    Trip map(TripEntity entity);
    TripEntity map(Trip role);
}
