package com.example.dbis.app;

import com.example.dbis.domain.model.Apartment;
import com.example.dbis.domain.model.Estate;
import com.example.dbis.domain.model.House;
import com.example.dbis.infra.jpa.ApartmentRepository;
import com.example.dbis.infra.jpa.EstateRepository;
import com.example.dbis.infra.jpa.HouseRepository;
import jakarta.transaction.Transactional;
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

  public List<Estate> findAllEstates() {
    return estateRepository.findAll();
  }

  public Optional<Estate> findEstateById(Integer id) {
    //log.info("class=EstateService, method=findById, id={}", id);
    return estateRepository.findById(id);
  }


  public void saveApartment(Apartment apartment) {
    //log.info("class=EstateService, method=save, estate={}", apartment);
    apartmentRepository.save(apartment);
  }

  public void saveHouse(House house) {
    //log.info("class=EstateService, method=save, estate={}", house);
    houseRepository.save(house);
  }

  @Transactional
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
