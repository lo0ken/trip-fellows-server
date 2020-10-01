package com.tripfellows.server.service.impl;

import com.tripfellows.server.mapper.PointMapper;
import com.tripfellows.server.repository.PointRepository;
import com.tripfellows.server.service.api.PointService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for Points
 */
@Slf4j
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final PointMapper pointMapper;

    private final PointRepository pointRepository;

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
        pointMapper = Mappers.getMapper(PointMapper.class);
    }
}
