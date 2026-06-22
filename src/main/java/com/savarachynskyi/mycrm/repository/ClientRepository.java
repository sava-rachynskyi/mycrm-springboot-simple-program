package com.savarachynskyi.mycrm.repository;

import com.savarachynskyi.mycrm.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
