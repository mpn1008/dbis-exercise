package com.example.dbis.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tenancy_contract")
@PrimaryKeyJoinColumn(name = "contract_no")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class TenancyContract extends Contract {
    @Id
    @Column(name = "contract_no")
    Integer contractNo;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "duration_months")
    Integer durationMonths;

    @Column(name = "extra_costs")
    Double extraCosts;

}
