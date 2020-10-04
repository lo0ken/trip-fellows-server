package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TripMapper {
    @Mapping(source = "tripToAccounts", target = "members")
    Trip map(TripEntity entity);

    TripEntity map(Trip role);
}
