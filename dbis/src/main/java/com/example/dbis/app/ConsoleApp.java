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
    Scanner scanner;
    String command;
    while (true) {
      if (System.console() == null) {
        System.out.println("No console available. Running in non-interactive mode.");
        // maybe run some automated commands instead?
      } else {
        scanner = new Scanner(System.in);
        System.out.println("Enter command (save, list, exit): ");
        command = scanner.nextLine();
        // handle input

        switch (command) {
          case "save" -> {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter age: ");
            System.out.println("Saved!");
          }
          case "list" -> {
            estateService.findAllEstates().forEach(p ->
                log.info("estateId={}", p.getEstateId())
            );
          }
          case "exit" -> {
            System.out.println("Goodbye!");
            System.exit(0);
          }
          default -> System.out.println("Unknown command");
        }
      }


//      if (scanner.hasNextLine()) {
//        command = scanner.nextLine();
//      } else {
//        command = "default"; // fallback if no input
//      }

    }
  }
}
