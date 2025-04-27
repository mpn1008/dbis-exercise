package com.example.dbis.app;

import com.example.dbis.domain.model.EstateAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleApp implements CommandLineRunner {

  private final EstateService estateService;
  private final EstateAgentService estateAgentService;
  private static final Random random = new Random();

  private int randomId() {
    return random.nextInt(10000000);
  }

  private void cmdExec(Scanner scanner) {
    while (true) {
      System.out.println("Enter command :");
      System.out.println("1. List all estates");
      System.out.println("2. Delete account by id");
      System.out.println("3. Create account");
      System.out.println("4. Update account by id");
      System.out.println("5. Exit");

      var command = scanner.nextLine();

      switch (command) {
        case "1" -> {
          var es = estateService.findAllEstates();
          for (var estate : es) {
            log.info("estateId={}, estateCity={}", estate.getEstateId(),
                estate.getCity());
          }
        }
        case "2" -> {
          System.out.println("Enter Id to delete account:");
          var id = scanner.nextInt();
          scanner.nextLine();
          estateAgentService.deleteById(id);
        }
        case "3" -> {
          System.out.println("Enter your name:");

          var name = scanner.nextLine();

          System.out.println("Enter your address:");
          var address = scanner.nextLine();

          System.out.println("Enter your login id:");
          var loginId = scanner.nextLine();

          System.out.println("Enter your password:");
          var pass = scanner.nextLine();

          var acc = EstateAgent.builder()
              .agentId(randomId())
              .name(name)
              .address(address)
              .login(loginId)
              .password(pass)
              .build();

          estateAgentService.save(acc);
          System.out.println("Account has been created");
        }

        case "4" -> {
          System.out.println("Enter the agent id you want to update:");
          var id = scanner.nextInt();
          scanner.nextLine();

          System.out.println("Enter your update name:");

          var name = scanner.nextLine().trim();

          System.out.println("Enter your address:");
          var address = scanner.nextLine().trim();

          System.out.println("Enter your password:");
          var pass = scanner.nextLine().trim();

          System.out.println("Name: " + name);
          System.out.println("Address: " + address);
          System.out.println("Password: " + pass);

          var acc = EstateAgent.builder()
              .agentId(id)
              .name(name)
              .address(address)
              .password(pass)
              .build();

          if(estateAgentService.update(id, acc)) {
            System.out.println("Account has been updated!");
          }
          else {
            System.out.println("Can't find account!");
          }
        }

        case "5" -> {
          System.out.println("Exiting");
          System.exit(0);
        }

//        default -> System.out.println("Unknown command");
      }
    }

  }

  @Override
  public void run(String... args) throws Exception {

    Scanner scanner = new Scanner(System.in);
//      System.out.println("Enter command (save, list, exit): ");
//      String command = scanner.nextLine();

    System.out.print("Username: ");
    String username = scanner.nextLine();

    System.out.print("Password: ");
    String password = scanner.nextLine();

    var account = estateAgentService.findByLoginAndPassword(username, password);

    if (account.isPresent()) {
      System.out.println("Welcome, " + username + "!");
      cmdExec(scanner);
    } else {
      System.out.println("Invalid credentials. Exiting.");
      System.exit(1);
    }
  }
}

