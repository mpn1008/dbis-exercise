package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Integer> {

}
