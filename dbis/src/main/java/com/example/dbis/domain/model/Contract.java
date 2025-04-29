package com.example.dbis.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "contract")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class Contract {
    @Id
    @Column(name = "contract_no")
    Integer contractNo;

    @Column(name = "contract_date")
    LocalDate contractDate;

    @Column(name = "place")
    String place;

    @Column(name = "person_id")
    Integer personId;

    @Column(name = "estate_id")
    Integer estateId;

}
