package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.model.TripStatus;
import org.mapstruct.Mapper;

@Mapper
public interface TripStatusMapper {
    TripStatus map(TripStatusEntity entity);
    TripStatusEntity map(TripStatus tripStatus);
}
