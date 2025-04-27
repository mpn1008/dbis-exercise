package com.example.dbis.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "estate")
public class Estate {
    @Id
    @Column(name = "estate_id")
    Integer estateId;

    @Column(name = "city")
    String city;

    @Column(name = "postal_code")
    String postalCode;

    @Column(name = "street")
    String street;

    @Column(name ="street_no")
    String street_no;

    @Column(name ="area_sqm")
    String area_sqm;
}
