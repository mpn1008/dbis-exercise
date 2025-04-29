package com.example.dbis.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "estate")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Estate {
    @Id
    @Column(name = "estate_id")
    Integer estateId;

    @Column(name = "city")
    String city;

    @Column(name = "postal_code")
    String postal_code;

    @Column(name = "street")
    String street;

    @Column(name ="street_no")
    String street_no;

    @Column(name ="area_sqm")
    Double area_sqm;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "agent_id", nullable = false)
    private EstateAgent agent;
}
