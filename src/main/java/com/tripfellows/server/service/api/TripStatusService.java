package com.tripfellows.server.service.api;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.TripStatus;

public interface TripStatusService {

    /**
     * Finds trip status by code
     *
     * @param code code to search trip status by
     * @return trip status with id code and name
     */
    TripStatus findByCode(TripStatusCodeEnum code);
}
