package com.tripfellows.server.controller;

import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.model.request.AddMemberRequest;
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

    /**
     * POST /api/trip-members/addMember : add member to existing trip
     *
     * @param request request includes tripId, accountId, and roleCode
     * @return the ResponseEntity with status 201 (Created) and with body the new trip member
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addMember")
    public ResponseEntity<TripMember> addMember(@RequestBody AddMemberRequest request) throws URISyntaxException {
        log.debug("REST request to add member with accountId {} to trip with id {}",
                request.getAccountId(), request.getTripId());

        TripMember result = tripAccountService.addTripMember(
                request.getTripId(), request.getAccountId(), request.getRoleCode()
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
