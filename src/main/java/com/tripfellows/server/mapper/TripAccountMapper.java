package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.model.TripMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface TripAccountMapper {
    @Mapping(source = "tripId", target = "trip.id")
    @Mapping(source = "tripMember.account.id", target = "account.id")
    @Mapping(source = "tripMember.role.id", target = "role.id")
    TripAccountEntity map(TripMember tripMember, Integer tripId);

    @Mapping(source = "tripId", target = "trip.id")
    @Mapping(source = "accountId", target = "account.id")
    @Mapping(source = "roleId", target = "role.id")
    TripAccountEntity from(Integer tripId, Integer accountId, Integer roleId);

    TripMember map(TripAccountEntity entity);
}
