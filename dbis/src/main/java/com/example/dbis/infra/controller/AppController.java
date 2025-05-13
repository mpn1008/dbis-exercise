package com.example.dbis.infra.controller;

import com.example.dbis.app.EstateService;
import com.example.dbis.app.ex3.StdSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

  private final EstateService service;
  private final StdSchedule schedule;

  @GetMapping("/estates")
  public String getEstates() {
    return service.findAllEstates().toString();
  }

  @GetMapping("/s3")
  public String runS3() throws Exception {
    schedule.triggerS3();
    return "Schedule S3 completed";
  }

  @GetMapping("/s1")
  public String runS1() throws Exception {
    schedule.triggerS1();
    return "Schedule S3 completed";
  }
}
