package com.tripfellows.server.service.api;

import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.model.TripMember;

import java.util.List;

public interface TripAccountService {

    /**
     * Save all trip members to trip_account table, associated with existing trip
     *
     * @param tripId existing trip to associate members
     * @param members list of members to add to trip
     * @return list of members, added to trip
     */
    List<TripMember> saveAll(Integer tripId, List<TripMember> members);

    /**
     * Save new trip_account record with specified tripId, accountId and roleId,
     * associated with specified roleCode
     *
     * @param tripId tripId to add member
     * @param accountId accountId of member to add
     * @param roleCode role, associated with member after adding
     * @return the new member of trip
     */
    TripMember addTripMember(Integer tripId, Integer accountId, RoleCodeEnum roleCode);

    /**
     * Delete trip member from trip (trip_account table)
     * @param tripMemberId id of trip member which is equals to id in trip_account table
     */
    void removeTripMember(Integer tripMemberId);
}
