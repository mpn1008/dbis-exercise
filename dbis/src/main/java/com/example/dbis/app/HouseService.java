package com.example.dbis.app;


import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.domain.model.House;
import com.example.dbis.infra.jpa.HouseRepository;
import com.example.dbis.infra.jpa.EstateAgentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository repository;
    private final EstateAgentRepository estateAgentRepository;

    @Transactional
    public boolean createNewHouse(CreateHouseRequest req) {
        log.info("class=EstateAgentService, method=createNewHouse");
        Optional<EstateAgent> agent = estateAgentRepository.findById(req.getAgentId());
        if (agent.isPresent()) {
            repository.save(House.builder()
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
}

