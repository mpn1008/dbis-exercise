package com.example.dbis.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "purchase_contract")
@PrimaryKeyJoinColumn(name = "contract_no")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseContract extends Contract {
    @Id
    @Column(name = "contract_no")
    Integer contractNo;

    @Column(name = "Installments")
    Integer installments;

    @Column(name = "interest_rate")
    double interestRate;

}
