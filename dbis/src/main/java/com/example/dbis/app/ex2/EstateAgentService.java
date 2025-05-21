package com.example.dbis.app.ex2;

import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.infra.jpa.EstateAgentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstateAgentService {
  private final EstateAgentRepository estateAgentRepository;

  public void save(EstateAgent estateAgent) {
    estateAgentRepository.save(estateAgent);
  }

  public Optional<EstateAgent> findAgentById(Integer id) {
    return estateAgentRepository.findById(id);
  }

  @Transactional
  public boolean update(Integer id, EstateAgent estateAgent) {
    var agent = estateAgentRepository.findById(id);

    if (agent.isPresent()) {
      var a = agent.get();
      a.setAddress(estateAgent.getAddress().isBlank() && estateAgent.getAddress().isEmpty() ? a.getAddress() : estateAgent.getAddress());

      a.setName(estateAgent.getName().isBlank() ? a.getName() : estateAgent.getName());

      a.setPassword(estateAgent.getPassword().isBlank() ? a.getPassword() : estateAgent.getPassword());

      estateAgentRepository.save(estateAgent);

      return true;
    }
    return false;
  }

  public List<EstateAgent> findAll() {
    return estateAgentRepository.findAll();
  }
  public Optional<EstateAgent> findByLoginAndPassword(String login, String password) {
    log.info("class=EstateAgentService, method=findByLoginAndPassword, login={}, password={}", login, password);
    return estateAgentRepository.findByLoginAndPassword(login, password);
  }

  public void deleteById(Integer id) {
    log.info("class=EstateAgentService, method=deleteById, id={}", id);
    estateAgentRepository.deleteById(id);
  }
}
