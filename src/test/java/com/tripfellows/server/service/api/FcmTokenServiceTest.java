package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.AccountFcmToken;
import com.tripfellows.server.repository.AccountFcmTokenRepository;
import com.tripfellows.server.service.impl.FcmTokenServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FcmTokenServiceTest {

    @Mock
    AccountFcmTokenRepository fcmTokenRepository;

    @InjectMocks
    FcmTokenServiceImpl fcmTokenService;

    EasyRandom easyRandom = new EasyRandom();

    @Test
    public void findByAccountIdsTest() {
        List<Integer> accountIds = easyRandom.objects(Integer.class, 10).collect(toList());

        when(fcmTokenRepository.findById(any()))
                .thenReturn(Optional.of(easyRandom.nextObject(AccountFcmToken.class)));

        List<String> tokens = fcmTokenService.findByAccountIds(accountIds);

        assertThat(tokens).hasSize(accountIds.size());
    }

    @Test
    public void findByAccountIdsWhenTokenNotExistsTest() {
        List<Integer> accountIds = easyRandom.objects(Integer.class, 10).collect(toList());

        when(fcmTokenRepository.findById(any()))
                .thenReturn(Optional.empty());

        List<String> tokens = fcmTokenService.findByAccountIds(accountIds);

        assertThat(tokens).hasSize(0);
    }

    @Test
    public void findByAccountIdsWhenEmptyTest() {
        List<Integer> accountIds = new ArrayList<>();

        List<String> tokens = fcmTokenService.findByAccountIds(accountIds);

        assertThat(tokens).hasSize(0);
    }

    @Test
    public void updateWhenExistsTest() {
        String newFcmToken = easyRandom.nextObject(String.class);
        AccountFcmToken fcmToken = easyRandom.nextObject(AccountFcmToken.class);

        when(fcmTokenRepository.findById(fcmToken.getAccountId()))
                .thenReturn(Optional.of(fcmToken));

        fcmTokenService.update(fcmToken.getAccountId(), newFcmToken);

        verifyTokenSave(fcmToken.getAccountId(), newFcmToken);
    }

    @Test
    public void updateWhenEmptyTest() {
        AccountFcmToken fcmToken = easyRandom.nextObject(AccountFcmToken.class);

        when(fcmTokenRepository.findById(fcmToken.getAccountId()))
                .thenReturn(Optional.empty());

        fcmTokenService.update(fcmToken.getAccountId(), fcmToken.getFcmToken());

        verifyTokenSave(fcmToken.getAccountId(), fcmToken.getFcmToken());
    }

    private void verifyTokenSave(Integer accountId, String fcmToken) {
        verify(fcmTokenRepository).save(argThat(t ->
                t.getAccountId().equals(accountId) && t.getFcmToken().equals(fcmToken)));
    }

    @Test
    public void deleteWhenExistsTest() {
        Integer accountId = easyRandom.nextInt();
        when(fcmTokenRepository.existsById(accountId)).thenReturn(true);

        fcmTokenService.delete(accountId);

        verify(fcmTokenRepository).deleteById(accountId);
    }

    @Test
    public void deleteWhenNotExistsTest() {
        Integer accountId = easyRandom.nextInt();
        when(fcmTokenRepository.existsById(accountId)).thenReturn(false);

        fcmTokenService.delete(accountId);

        verify(fcmTokenRepository, never()).deleteById(accountId);
    }
}