package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ContractRepository extends JpaRepository<Contract, Integer> {
}
