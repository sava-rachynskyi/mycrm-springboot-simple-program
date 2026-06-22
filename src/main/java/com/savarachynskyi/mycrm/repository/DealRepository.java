package com.savarachynskyi.mycrm.repository;

import com.savarachynskyi.mycrm.models.Deal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByClientId(Long clientId, Sort sort);
    boolean existsByClientId(Long clientId);
}
