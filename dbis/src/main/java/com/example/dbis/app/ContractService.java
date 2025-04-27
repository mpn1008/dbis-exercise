package com.example.dbis.app;
import com.example.dbis.domain.model.*;
import com.example.dbis.infra.jpa.TenancyRepository;
import com.example.dbis.infra.jpa.ContractRepository;
import com.example.dbis.infra.jpa.PurchaseRepository;
import com.example.dbis.infra.jpa.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class ContractService  {
    private final PersonRepository personRepository;
    private final ContractRepository contractRepository;
    private final PurchaseRepository purchaseRepository;
    private final TenancyRepository tenancyRepository;

    public void save(Person person) {
        personRepository.save(person);
    }

    public Optional<Person> findPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public void saveTenancy(TenancyContract tenancy) {
       tenancyRepository.save(tenancy);
    }

    public void savePurchase(PurchaseContract purchase) {
        purchaseRepository.save(purchase);
    }

    public List<Contract> findAllContracts() {
        return contractRepository.findAll();
    }

}
