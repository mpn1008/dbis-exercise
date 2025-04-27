package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface PersonRepository extends JpaRepository<Person, Integer> {

}
