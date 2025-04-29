package com.example.dbis.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "apartment")
@PrimaryKeyJoinColumn(name = "estate_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Apartment extends Estate {
    @Id
    @Column(name = "estate_id")
    Integer estateId;

    @Column(name = "floor")
    Integer floor;

    @Column(name = "rent")
    Double rent;

    @Column(name = "rooms")
    Integer rooms;

    @Column(name ="has_balcony")
    Boolean has_balcony;

    @Column(name ="has_kitchen")
    Boolean has_kitchen;

}
