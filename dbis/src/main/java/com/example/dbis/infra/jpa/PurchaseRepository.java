package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.PurchaseContract;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface PurchaseRepository extends JpaRepository<PurchaseContract, Integer> {
}
