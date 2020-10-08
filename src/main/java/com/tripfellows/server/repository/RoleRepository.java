package com.tripfellows.server.repository;

import com.tripfellows.server.entity.RoleEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByCode(RoleCodeEnum code);
}
