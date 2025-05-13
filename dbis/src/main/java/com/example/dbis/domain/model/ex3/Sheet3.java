package com.example.dbis.domain.model.ex3;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "sheet3")
public class Sheet3 {
  @Id
  @Column(name = "id")
  Integer id;

  @Column(name = "name")
  String name;
}
