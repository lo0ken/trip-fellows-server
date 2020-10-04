package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripAccountRepository;
import com.tripfellows.server.service.impl.TripAccountServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripAccountServiceTest {

    @Mock
    TripAccountRepository tripAccountRepository;

    @InjectMocks
    TripAccountServiceImpl tripAccountService;

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
}