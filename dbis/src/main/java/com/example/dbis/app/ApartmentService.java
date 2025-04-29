package com.example.dbis.app;

import com.example.dbis.domain.model.Apartment;
import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.infra.jpa.ApartmentRepository;
import com.example.dbis.infra.jpa.EstateAgentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApartmentService {

  private final ApartmentRepository repository;
  private final EstateAgentRepository estateAgentRepository;

  @Transactional
  public boolean createNewApartment(CreateApartmentRequest req) {
    log.info("class=EstateAgentService, method=createNewApartment");
    Optional<EstateAgent> agent = estateAgentRepository.findById(req.getAgentId());
    if (agent.isPresent()) {
      repository.save(Apartment.builder()
          .agent(agent.get())
          .city(req.getCity())
          .floor(req.getFloor())
          .area_sqm(req.getAreaSqm())
          .rent(req.getRent())
          .rooms(req.getRooms())
          .estateId(req.getEstateId())
          .postal_code(req.getPostalCode())
          .street(req.getStreet())
          .has_balcony(req.getHasBalcony())
          .has_kitchen(req.getHasKitchen())
          .street_no(req.getStreetNo())
          .build());
      return true;
    }
    return false;
  }
}
