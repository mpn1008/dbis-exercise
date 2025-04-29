package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.TenancyContract;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface TenancyRepository extends JpaRepository<TenancyContract, Integer> {
}
