package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.exception.NoAvailablePlacesFoundException;
import com.tripfellows.server.exception.PassengerOfAnotherTripException;
import com.tripfellows.server.mapper.TripAccountMapper;
import com.tripfellows.server.model.Role;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripAccountRepository;
import com.tripfellows.server.service.impl.RoleServiceImpl;
import com.tripfellows.server.service.impl.TripAccountServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripAccountServiceTest {

    @Mock
    TripAccountRepository tripAccountRepository;

    @InjectMocks
    TripAccountServiceImpl tripAccountService;

    @Mock
    RoleServiceImpl roleService;

    @Mock
    TripService tripService;

    TripAccountMapper tripAccountMapper = Mappers.getMapper(TripAccountMapper.class);

    @Test
    public void saveAllTest() {
        int size = 5;
        int tripId = 1;
        EasyRandom easyRandom = new EasyRandom();
        List<TripMember> members = easyRandom.objects(TripMember.class, size).collect(toList());


        when(tripAccountRepository.save(any())).thenReturn(easyRandom.nextObject(TripAccountEntity.class));

        List<TripMember> result = tripAccountService.saveAll(tripId, members);

        assertThat(result).hasSize(size);
    }

    @Test
    public void saveEmptyTest() {
        int size = 0;

        List<TripMember> result = tripAccountService.saveAll(1, new ArrayList<>());

        assertThat(result).hasSize(size);
    }

    @Test
    public void addMemberTest() {
        Integer tripId = 1;
        Integer accountId = 2;
        Role role = new Role(RoleCodeEnum.PASSENGER, "name");
        role.setId(3);

        TripAccountEntity tripAccountEntity = tripAccountMapper.from(tripId, accountId, role.getId());

        when(roleService.findByCode(role.getCode())).thenReturn(role);
        when(tripAccountRepository.save(any())).thenReturn(tripAccountEntity);
        when(tripService.findAvailablePlacesOfTrip(tripId)).thenReturn(1);

        TripMember result = tripAccountService.addTripMember(tripId, accountId, RoleCodeEnum.PASSENGER);

        verify(tripAccountRepository).save(argThat(t ->
                t.getTrip().getId().equals(tripId) &&
                t.getRole().getId().equals(role.getId()) &&
                t.getAccount().getId().equals(accountId)));

        assertThat(result.getRole().getId()).isEqualTo(role.getId());
        assertThat(result.getAccount().getId()).isEqualTo(accountId);
    }

    @Test(expected = NoAvailablePlacesFoundException.class)
    public void addMemberWhenNoAvailablePlacesFoundTest() {
        Integer tripId = 1;
        Integer accountId = 2;

        when(tripService.findAvailablePlacesOfTrip(tripId)).thenReturn(0);

        tripAccountService.addTripMember(tripId, accountId, RoleCodeEnum.PASSENGER);
    }

    @Test(expected = PassengerOfAnotherTripException.class)
    public void passengerOfAnotherTripTest() {
        Integer accountId = 10;

        when(tripService.findCurrentTrip(accountId))
                .thenReturn(Optional.of(Trip.builder().build()));

        tripAccountService.addTripMember(1, accountId, RoleCodeEnum.PASSENGER);
    }

    @Test
    public void removeMemberTest() {
        Integer tripMemberId = new EasyRandom().nextInt();

        tripAccountService.removeTripMember(tripMemberId);

        verify(tripAccountRepository).deleteById(tripMemberId);
    }
}