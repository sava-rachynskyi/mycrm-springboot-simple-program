package com.savarachynskyi.mycrm.services;

import com.savarachynskyi.mycrm.models.Client;
import com.savarachynskyi.mycrm.models.Deal;
import com.savarachynskyi.mycrm.repository.ClientRepository;
import com.savarachynskyi.mycrm.repository.DealRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DealService {
    private final ClientRepository clientRepository;
    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository, ClientRepository clientRepository) {
        this.dealRepository = dealRepository;
        this.clientRepository = clientRepository;
    }

    public List<Deal> getDealsByClient(Long clientId, String sortParameter) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortParameter);
        return dealRepository.findByClientId(clientId, sort);
    }

    public Deal createDeal(Deal deal, Long clientId) {
        if (deal.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0!");
        }
        if (clientId != null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Client not found"));
            deal.setClient(client);
        }
        deal.setStatus("IN_PROGRESS");
        deal.setCreatedAt(LocalDate.now());
        deal.setEndDate(null);
        return dealRepository.save(deal);
    }

    public Deal updateDeal(Long id, double newPrice, String newStatus) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Deal not found"));

        if ("COMPLETED".equals(deal.getStatus())) {
            throw new IllegalArgumentException("This deal is already completed and locked!");
        }

        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0!");
        }

        deal.setPrice(newPrice);

        if ("COMPLETED".equals(newStatus)) {
            deal.setEndDate(LocalDate.now());
        }

        deal.setStatus(newStatus);
        return dealRepository.save(deal);
    }

    public void deleteDeal(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Deal not found"));
        dealRepository.deleteById(id);
    }
}