package com.example.dbis.app.ex2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApartmentRequest {
  Integer estateId;

  Integer floor;

  Double rent;

  Integer rooms;

  Boolean hasBalcony;

  @Builder.Default
  Integer agentId = 1;

  Boolean hasKitchen;

  String city;

  String postalCode;

  String street;

  String streetNo;

  Double areaSqm;
}
