package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.PointEntity;
import com.tripfellows.server.model.Point;
import org.mapstruct.Mapper;

@Mapper
public interface PointMapper {
    Point map(PointEntity entity);
    PointEntity map(Point point);
}
