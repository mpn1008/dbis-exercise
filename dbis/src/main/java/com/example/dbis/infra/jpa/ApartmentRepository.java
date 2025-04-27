package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

}