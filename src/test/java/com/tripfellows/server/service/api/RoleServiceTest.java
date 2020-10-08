package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.RoleEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.model.Role;
import com.tripfellows.server.repository.RoleRepository;
import com.tripfellows.server.service.impl.RoleServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    public void findRoleByCodeTest() {
        EasyRandom easyRandom = new EasyRandom();
        RoleCodeEnum expectedCode = easyRandom.nextObject(RoleCodeEnum.class);
        RoleEntity roleEntity = easyRandom.nextObject(RoleEntity.class);
        roleEntity.setCode(expectedCode);

        when(roleRepository.findByCode(expectedCode)).thenReturn(roleEntity);

        Role actualCode = roleService.findByCode(expectedCode);

        assertEquals(expectedCode, actualCode.getCode());
    }
}