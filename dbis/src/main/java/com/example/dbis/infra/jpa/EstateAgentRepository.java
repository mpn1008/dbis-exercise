package com.example.dbis.infra.jpa;

import com.example.dbis.domain.model.EstateAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstateAgentRepository extends JpaRepository<EstateAgent, Integer> {
  Optional<EstateAgent> findByLoginAndPassword(String login, String password);

}
