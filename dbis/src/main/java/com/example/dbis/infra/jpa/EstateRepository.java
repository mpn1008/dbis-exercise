package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepository extends JpaRepository<Estate, Integer> {
}

