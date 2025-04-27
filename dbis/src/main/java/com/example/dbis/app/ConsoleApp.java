package com.example.dbis.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleApp implements CommandLineRunner {

  private final EstateService estateService;

  @Override
  public void run(String... args) throws Exception {

    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter command (save, list, exit): ");
      String command = scanner.nextLine();

      switch (command) {
        case "save" -> {
          System.out.print("Enter name: ");
          String name = scanner.nextLine();
          System.out.print("Enter age: ");
          System.out.println("Saved!");
        }
        case "list" -> {
          var es = estateService.findAllEstates();
          for (var estate : es) {
            log.info("estateId={}, estateCity={}", estate.getEstateId(),
                estate.getCity());
          }
        }
        case "exit" -> {
          System.out.println("Goodbye!");
          System.exit(0);
        }
        default -> System.out.println("Unknown command");
      }
    }
  }
}
