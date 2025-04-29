package com.example.dbis.app;

import com.example.dbis.domain.model.Apartment;
import com.example.dbis.domain.model.Estate;
import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.domain.model.House;
import com.example.dbis.infra.jpa.ApartmentRepository;
import com.example.dbis.infra.jpa.EstateAgentRepository;
import com.example.dbis.infra.jpa.EstateRepository;
import com.example.dbis.infra.jpa.HouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class EstateService {
  private final EstateRepository estateRepository;
  private final HouseRepository houseRepository;
  private final ApartmentRepository apartmentRepository;
  private final EstateAgentRepository estateAgentRepository;

  public List<Estate> findAllEstates() {
    return estateRepository.findAll();
  }

  public Optional<Estate> findEstateById(Integer id) {
    //log.info("class=EstateService, method=findById, id={}", id);
    return estateRepository.findById(id);
  }

  public boolean createNewHouse(CreateHouseRequest req) {
    log.info("method=createNewHouse, req={}", req);
    Optional<EstateAgent> agent = estateAgentRepository.findById(req.getAgentId());
    if (agent.isPresent()) {
      houseRepository.save(House.builder()
          .agent(agent.get())
          .city(req.getCity())
          .floors(req.getFloor())
          .area_sqm(req.getAreaSqm())
          .estateId(req.getEstateId())
          .postal_code(req.getPostalCode())
          .street(req.getStreet())
          .street_no(req.getStreetNo())
          .build());
      return true;
    }
    return false;
  }

  public void saveApartment(Apartment apartment) {
    //log.info("class=EstateService, method=save, estate={}", apartment);
    apartmentRepository.save(apartment);
  }

  public void saveHouse(House house) {
    //log.info("class=EstateService, method=save, estate={}", house);
    houseRepository.save(house);
  }

  public void deleteEstate(Integer id) {
    // log.info("class=EstateService, method=deleteById, id={}", id);
    // 1) If it's an apartment, delete its row
    if (apartmentRepository.existsById(id)) {
      apartmentRepository.deleteById(id);
    }
    // 2) Else if it's a house, delete that
    else if (houseRepository.existsById(id)) {
      houseRepository.deleteById(id);
    }
  }
}
