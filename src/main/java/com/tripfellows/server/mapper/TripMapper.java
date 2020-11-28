package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TripMapper {
    @Mapping(source = "tripToAccounts", target = "members")
    @Mapping(source = "startPoint", target = "departureAddress")
    @Mapping(source = "endPoint", target = "destinationAddress")
    Trip map(TripEntity entity);

    @Mapping(source = "departureAddress", target = "startPoint")
    @Mapping(source = "destinationAddress", target = "endPoint")
    TripEntity map(Trip trip);
}
