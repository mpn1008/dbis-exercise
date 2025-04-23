package com.example.dbis.app;

import com.example.dbis.domain.model.Estate;
import com.example.dbis.infra.jpa.EstateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstateService {
    private final EstateRepository estateRepository;

//    public void saveEstate(Estate estate) {
//        estateRepository.save(estate);
//    }

//    public void deleteEstate(Integer estateId) {
//        estateRepository.deleteById(estateId);
//    }
//
//    public Estate findEstateById(Integer estateId) {
//        return estateRepository.findById(estateId).orElse(null);
//    }
//
    public List<Estate> findAllEstates() {
        return estateRepository.findAll();
    }
}
