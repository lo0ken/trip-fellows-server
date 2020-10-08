package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.RoleEntity;
import com.tripfellows.server.model.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    Role map(RoleEntity entity);
    RoleEntity map(Role role);
}
