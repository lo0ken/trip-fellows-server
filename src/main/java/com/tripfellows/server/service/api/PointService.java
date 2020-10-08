package com.tripfellows.server.service.api;

import com.tripfellows.server.model.Point;

public interface PointService {

    /**
     * Update all information for a specific point, and return the modified point
     * Or create it if not exists
     *
     * @param point point to create or update
     * @return updated or created point
     */
    Point save(Point point);
}
