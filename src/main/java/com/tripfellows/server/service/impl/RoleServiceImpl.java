package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.RoleEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.mapper.RoleMapper;
import com.tripfellows.server.model.Role;
import com.tripfellows.server.repository.RoleRepository;
import com.tripfellows.server.service.api.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for Roles
 */
@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        roleMapper = Mappers.getMapper(RoleMapper.class);
    }

    @Override
    public Role findByCode(RoleCodeEnum code) {
        log.debug("retrieving role status with code : {}", code.getValue());
        RoleEntity roleEntity = roleRepository.findByCode(code);

        return roleMapper.map(roleEntity);
    }
}
