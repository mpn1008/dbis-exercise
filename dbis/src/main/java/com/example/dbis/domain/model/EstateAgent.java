package com.example.dbis.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "estate_agent")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstateAgent {
  @Id
  @Column(name = "agent_id")
  Integer agentId;

  @Column(name = "name")
  String name;

  @Column(name = "address")
  String address;

  @Column(name = "login")
  String login;

  @Column(name = "password")
  String password;

  @OneToMany(mappedBy = "agent")
  private List<Estate> estates = new ArrayList<>();
}
