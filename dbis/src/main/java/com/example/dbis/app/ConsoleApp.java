package com.example.dbis.app;

import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.domain.model.Apartment;
import com.example.dbis.domain.model.House;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

  private void cmdExec(Scanner sc) {

    while (true) {
      System.out.println("\n=== MAIN MENU ===");
      System.out.println("1. Agent Account Administration");
      System.out.println("2. Estate Management");
      System.out.println("3. Contract Management");
      System.out.println("0. Exit");
      System.out.print("Select an option: ");
      var input = sc.nextLine();

        switch (input) {
            case "1" -> agentAccountAdministration(sc);
            case "2" -> estateManagement(sc);
            case "3" -> contractManagement(sc);
            case "0" -> {
              System.out.println("Exiting");
              System.exit(0);
            }
            default -> System.out.println("Invalid choice, try again.");
        }
    }
  }


  // Agent Account Administration (protected by ADMIN_PASSWORD)
  private void agentAccountAdministration(Scanner scanner) {
    System.out.println("=== AGENT ACCOUNT ADMINISTRATION ===");
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

  // Estate Management (agent must log in)
  private void estateManagement(Scanner scanner) {
    //Estate agents log in with their individual accounts

    System.out.print("Username: ");
    String username = scanner.nextLine();

    System.out.print("Password: ");
    String password = scanner.nextLine();

    var account = estateAgentService.findByLoginAndPassword(username, password);

    if (account.isPresent()) {
      System.out.println("Welcome, " + username + "!");
      EstateAgent currentAgent = account.get();
    } else {
      System.out.println("Invalid credentials. Exiting.");
      System.exit(1);
    }

    while (true) {
      System.out.println("\n--- ESTATE MANAGEMENT (Agent: "  + username + ") ---");
      System.out.println("1. List all estates");
      System.out.println("2. Create new estate");
      System.out.println("3. Delete an estate");
      System.out.println("4. Update an estate");
      System.out.println("0. Back to main menu");
      System.out.print("Select an option: ");

      switch (scanner.nextLine().trim()) {
        case "1" -> {
          var es = estateService.findAllEstates();
          for (var estate : es) {
            log.info("estateId={}, estateCity={}", estate.getEstateId(),
                    estate.getCity());
          }
        }
        case "2" -> {
          System.out.print("Choose the type of estate (A = Apartment, B = House): ");
          String type = scanner.nextLine().trim();

          System.out.println("Enter the city:");
          var city = scanner.nextLine();

          System.out.println("Enter the postal code:");
          var postalCode = scanner.nextLine();

          System.out.println("Enter the street:");
          var street = scanner.nextLine();

          System.out.println("Enter the street number:");
          var streetNo = scanner.nextLine();

          System.out.println("Enter the area in sqm:");
          double areaSqm = scanner.nextDouble();
          scanner.nextLine();


          if (type.equalsIgnoreCase("A")) {
            System.out.println("Enter the floor :");
            int floor = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the rent per month :");
            double rent = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the number of rooms :");
            int rooms = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Balcony?(yes/no):");
            var hasbalcony = scanner.nextLine().trim().equalsIgnoreCase("yes");

            System.out.println("Built-in kitchen?(yes/no):");
            var haskitchen = scanner.nextLine().trim().equalsIgnoreCase("yes");

            Apartment apt = Apartment.builder()
                    // base class fields:
                    .estateId(randomId())
                    .city(city)
                    .postal_code(postalCode)
                    .street(street)
                    .street_no(streetNo)
                    .area_sqm(areaSqm)
                    // subclass fields:
                    .floor(floor)
                    .rent(rent)
                    .rooms(rooms)
                    .has_balcony(hasbalcony)
                    .has_kitchen(haskitchen)
                    .build();

            estateService.saveApartment(apt);
            System.out.println("Apartment created with ID " + apt.getEstateId());

          }else if (type.equalsIgnoreCase("B")) {

            System.out.print("Number of floors: ");
            int floors = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Sale price: ");
            double price = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Garden? (yes/no): ");
            boolean hasGarden = scanner.nextLine().trim().equalsIgnoreCase("yes");

            House house = House.builder()
                    // base class fields:
                    .estateId(randomId())
                    .city(city)
                    .postal_code(postalCode)
                    .street(street)
                    .street_no(streetNo)
                    .area_sqm(areaSqm)
                    // subclass fields:
                    .floors(floors)
                    .price(price)
                    .has_garden(hasGarden)

                    .build();


            estateService.saveHouse(house);
            System.out.println("House created with ID " + house.getEstateId());
          } else {
            System.out.println("Unknown type, returning to menu.");
          }

        }
        case "3" -> {
          System.out.print("Enter estate ID to delete: ");
          Integer id = Integer.valueOf(scanner.nextLine().trim());
          try {
            estateService.deleteEstate(id);
            System.out.println("Estate deleted successfully (ID=" + id + ").");
          } catch (org.springframework.dao.EmptyResultDataAccessException ex) {
            System.out.println("Estate not found with ID=" + id + ".");
          } catch (Exception ex) {
            System.out.println("Error deleting estate: " + ex.getMessage());
          }
        }
        case "4" -> {
          System.out.println("Enter the Estate id you want to update:");
          var id = scanner.nextInt();
          scanner.nextLine();

          var estateOptional = estateService.findEstateById(id);

          if (estateOptional.isEmpty()) {
            System.out.println("Estate not found!");
            return;
          }

          var estate = estateOptional.get();

          System.out.println("Enter the updated city:");
          var city = scanner.nextLine().trim();

          System.out.println("Enter the updated postal code:");
          var postalCode = scanner.nextLine().trim();

          System.out.println("Enter the updated street:");
          var street = scanner.nextLine().trim();

          System.out.println("Enter the updated street number:");
          var streetNo = scanner.nextLine().trim();

          System.out.println("Enter the updated area in sqm:");
          double areaSqm = scanner.nextDouble();
          scanner.nextLine();

          estate.setCity(city);
          estate.setPostal_code(postalCode);
          estate.setStreet(street);
          estate.setStreet_no(streetNo);
          estate.setArea_sqm(areaSqm);

          if (estate instanceof Apartment apartment) {
            System.out.println("Enter the updated floor:");
            var floor = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the updated rent:");
            var rent = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter the updated number of rooms:");
            var rooms = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Does it have a balcony? (yes/no):");
            var hasBalcony = scanner.nextLine().trim().equalsIgnoreCase("yes");

            System.out.println("Does it have a kitchen? (yes/no):");
            var hasKitchen = scanner.nextLine().trim().equalsIgnoreCase("yes");

            apartment.setFloor(floor);
            apartment.setRent(rent);
            apartment.setRooms(rooms);
            apartment.setHas_balcony(hasBalcony);
            apartment.setHas_kitchen(hasKitchen);

            estateService.saveApartment(apartment);
            System.out.println("Apartment has been updated!");
          } else if (estate instanceof House house) {
            System.out.println("Enter the updated number of floors:");
            var floors = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the updated price:");
            var price = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Does it have a garden? (yes/no):");
            var hasGarden = scanner.nextLine().trim().equalsIgnoreCase("yes");

            house.setFloors(floors);
            house.setPrice(price);
            house.setHas_garden(hasGarden);

            estateService.saveHouse(house);
            System.out.println("House has been updated!");
          } else {
            System.out.println("Unknown estate type. Update canceled.");
          }
        }
        case "0" -> { return; }
        default -> System.out.println("Invalid choice.");
      }
    }
  }


  // Contract Management
  private void contractManagement(Scanner scanner) {

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

