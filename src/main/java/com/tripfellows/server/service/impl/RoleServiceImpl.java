package com.tripfellows.server.service.impl;

import com.tripfellows.server.mapper.RoleMapper;
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
}
