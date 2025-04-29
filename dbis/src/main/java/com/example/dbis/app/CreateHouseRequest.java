package com.example.dbis.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateHouseRequest {
  Integer estateId;

  Integer floor;

  Double price;

  Boolean hasGarden;

  @Builder.Default
  Integer agentId = 1;

  String city;

  String postalCode;

  String street;

  String streetNo;

  Double areaSqm;
}
