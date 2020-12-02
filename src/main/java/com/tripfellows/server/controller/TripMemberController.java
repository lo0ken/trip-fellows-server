package com.tripfellows.server.controller;

import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.model.request.AddMemberRequest;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.TripAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing trip members
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trip-members")
public class TripMemberController {

    private final TripAccountService tripAccountService;

    private final SecurityService securityService;

    /**
     * POST /api/trip-members/addMember : add member to existing trip
     *
     * @param request request includes tripId, accountId, and roleCode
     * @return the ResponseEntity with status 201 (Created) and with body the new trip member
     * or ResponseEntity with status 500 (Internal Server Error) if user is already a passenger of another trip
     * or the trip does not have any available places
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addMember")
    public ResponseEntity<TripMember> addMember(@RequestBody AddMemberRequest request) throws URISyntaxException {
        Integer accountId = securityService.getCurrentAccount().getId();

        log.debug("REST request to add member with accountId {} to trip with id {}",
                accountId, request.getTripId());

        TripMember result = tripAccountService.addTripMember(
                request.getTripId(), accountId, RoleCodeEnum.PASSENGER
        );

        return ResponseEntity.created(new URI("/api/trips/" + request.getTripId()))
                .body(result);
    }

    /**
     * DELETE /api/trip-members/removeMember/:tripMemberId : remove member from trip
     *
     * @param tripMemberId id of trip member to delete
     * @return the ResponseEntity with status 201 (Created) with empty body
     */
    @DeleteMapping("/removeMember/{tripMemberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Integer tripMemberId) {
        log.debug("REST request to delete trip member with id : {}", tripMemberId);

        tripAccountService.removeTripMember(tripMemberId);

        return ResponseEntity.ok().build();
    }
}
