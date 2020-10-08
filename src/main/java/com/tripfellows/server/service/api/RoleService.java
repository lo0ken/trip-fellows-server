package com.tripfellows.server.service.api;

import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.model.Role;


public interface RoleService {

    /**
     * Finds role by code
     *
     * @param code code to search role by
     * @return role with id code and name
     */
     Role findByCode(RoleCodeEnum code);
}
