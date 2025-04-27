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
@Table(name = "house")
@PrimaryKeyJoinColumn(name = "estate_id")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class House extends Estate {
    @Id
    @Column(name = "estate_id")
    Integer estateId;

    @Column(name = "floors")
    Integer floors;

    @Column(name = "price")
    Double price;

    @Column(name ="has_garden")
    Boolean has_garden;

}
