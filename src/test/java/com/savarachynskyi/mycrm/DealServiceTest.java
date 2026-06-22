package com.savarachynskyi.mycrm;

import com.savarachynskyi.mycrm.models.Deal;
import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.repository.DealRepository;
import com.savarachynskyi.mycrm.repository.ClientRepository;
import com.savarachynskyi.mycrm.services.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DealService dealService;

    @Test
    public void shouldThrowExceptionWhenCompletingAlreadyCompletedDeal() {
        Deal deal = new Deal();
        deal.setId(100L);
        deal.setStatus("COMPLETED");

        when(dealRepository.findById(100L)).thenReturn(Optional.of(deal));

        assertThrows(IllegalArgumentException.class, () -> {
            dealService.updateDeal(100L, 500.0, "COMPLETED");
        });
    }

    @Test
    public void shouldSetInProgressStatusWhenCreatingNewDeal() {
        Client client = new Client();
        client.setId(5L);

        Deal deal = new Deal();
        deal.setTitle("Website Development");
        deal.setPrice(1200.0);

        when(clientRepository.findById(5L)).thenReturn(Optional.of(client));
        when(dealRepository.save(any(Deal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Deal createdDeal = dealService.createDeal(deal, 5L);

        assertNotNull(createdDeal);
        assertEquals("IN_PROGRESS", createdDeal.getStatus());
        assertNotNull(createdDeal.getCreatedAt());
    }
}