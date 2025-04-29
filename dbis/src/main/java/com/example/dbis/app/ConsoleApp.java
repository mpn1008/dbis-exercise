package com.example.dbis.app;

import com.example.dbis.domain.model.Apartment;
import com.example.dbis.domain.model.EstateAgent;
import com.example.dbis.domain.model.House;
import com.example.dbis.domain.model.Person;
import com.example.dbis.domain.model.PurchaseContract;
import com.example.dbis.domain.model.TenancyContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j

public class ConsoleApp implements CommandLineRunner {

  private final EstateService estateService;
  private final EstateAgentService estateAgentService;
  private final ContractService contractService;
  private final ApartmentService apartmentService;
  private static final Random random = new Random();
  private final HouseService houseService;

  private int randomId() {
    return random.nextInt(10000000);
  }

  protected void signContract(Scanner scanner) {
    System.out.print("Is this a Tenancy Contract or Purchase Contract? (T = Tenancy, P = Purchase): ");
    var type = scanner.nextLine().trim();

    System.out.println("Enter the person ID:");
    var personId = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Enter the estate ID:");
    var estateId = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Enter contract date (YYYY-MM-DD):");
    var contractDate = scanner.nextLine().trim();

    System.out.println("Enter place:");
    var place = scanner.nextLine().trim();

    if (type.equalsIgnoreCase("T")) {
      System.out.println("Enter start date (YYYY-MM-DD):");
      var startDate = scanner.nextLine().trim();

      System.out.println("Enter duration in months:");
      var durationMonths = scanner.nextInt();
      scanner.nextLine();

      System.out.println("Enter extra costs:");
      var extraCosts = scanner.nextDouble();
      scanner.nextLine();

      TenancyContract tenancyContract = TenancyContract.builder()
          .contractNo(randomId())
          .contractDate(LocalDate.parse(contractDate))
          .place(place)
          .personId(personId)
          .estateId(estateId)
          .startDate(LocalDate.parse(startDate))
          .durationMonths(durationMonths)
          .extraCosts(extraCosts)
          .build();
      try {
        contractService.saveTenancy(tenancyContract);
        System.out.println("Tenancy Contract signed with Contract No " + tenancyContract.getContractNo());

      } catch (Exception e) {
        System.out.println("Error saving tenancy, Make sure you have correct person id and estate id");
      }

    } else if (type.equalsIgnoreCase("P")) {
      System.out.println("Enter number of installments:");
      var installments = scanner.nextInt();
      scanner.nextLine();

      System.out.println("Enter interest rate:");
      var interestRate = scanner.nextDouble();
      scanner.nextLine();

      PurchaseContract purchaseContract = PurchaseContract.builder()
          .contractNo(randomId())
          .contractDate(LocalDate.parse(contractDate))
          .place(place)
          .personId(personId)
          .estateId(estateId)
          .installments(installments)
          .interestRate(interestRate)
          .build();

      try {
        contractService.savePurchase(purchaseContract);
        System.out.println("Purchase Contract signed with Contract No " + purchaseContract.getContractNo());
      } catch (Exception e) {
        System.out.println("Error saving purchase, Make sure you have correct person id");
      }
    } else {
      System.out.println("Unknown contract type. Operation canceled.");
    }
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


    System.out.print("Enter admin password: ");
    String password = scanner.nextLine();
    if (!password.equals("admin")) {
      System.out.println("Invalid password. Exiting.");
      System.exit(1);
    }
    System.out.println("Welcome, admin!");
    System.out.println("Enter command :");
    System.out.println("1. Delete account by id");
    System.out.println("2. Create account");
    System.out.println("3. Update account by id");
    System.out.println("0. Exit");

    var command = scanner.nextLine();

    switch (command) {
      case "1" -> {
        System.out.println("Enter Id to delete account:");
        var id = scanner.nextInt();
        scanner.nextLine();
        estateAgentService.deleteById(id);
      }
      case "2" -> {
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

      case "3" -> {
        System.out.println("Enter the agent id you want to update:");
        var id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter your update name:");

        var name = scanner.nextLine().trim();

        System.out.println("Enter your address:");
        var address = scanner.nextLine().trim();

        System.out.println("Enter your Login:");
        var login = scanner.nextLine().trim();

        System.out.println("Enter your password:");
        var pass = scanner.nextLine().trim();

        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Login: " + login);
        System.out.println("Password: " + pass);

        var acc = EstateAgent.builder()
            .agentId(id)
            .name(name)
            .address(address)
            .login(login)
            .password(pass)
            .build();

        if (estateAgentService.update(id, acc)) {
          System.out.println("Account has been updated!");
        } else {
          System.out.println("Can't find account!");
        }
      }

      case "0" -> {
        return;
      }
    }
  }

  // Estate Management (agent must log in)
  private void estateManagement(Scanner scanner) {
    //Estate agents log in with their individual accounts

    System.out.print("Username: ");
    String username = scanner.nextLine();

    System.out.print("Password: ");
    String password = scanner.nextLine();

    var maybeAgent = estateAgentService.findByLoginAndPassword(username, password);
    if (maybeAgent.isEmpty()) {
      System.out.println("Invalid credentials. Exiting.");
      System.exit(1);
    }
    EstateAgent currentAgent = maybeAgent.get();
    System.out.println("Welcome, " + currentAgent.getName() + "!");

    while (true) {
      System.out.println("\n--- ESTATE MANAGEMENT (Agent: " + currentAgent.getName() + ") ---");
      System.out.println("1. List all estates");
      System.out.println("2. Create new estate");
      System.out.println("3. Delete an estate");
      System.out.println("4. Update an estate");
      System.out.println("0. Back to main menu");
      System.out.print("Select an option: ");

      switch (scanner.nextLine().trim()) {
        case "1" -> {
          var es = estateService.findAllEstates();
          log.info("Found {} estates!", es.size());
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

          System.out.println("Enter the estate agent id:");
          Integer agentId = scanner.nextInt();
          scanner.nextLine();

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

            var req = CreateApartmentRequest.builder()
                .estateId(randomId())
                .city(city)
                .postalCode(postalCode)
                .street(street)
                .streetNo(streetNo)
                .areaSqm(areaSqm)
                .agentId(agentId)
                .floor(floor)
                .rent(rent)
                .rooms(rooms)
                .hasBalcony(hasbalcony)
                .hasKitchen(haskitchen)
                .build();

            if (apartmentService.createNewApartment(req)) {
              System.out.println("Apartment created with ID " + req.getEstateId());
            } else {
              System.out.println("Apartment could not be created!");
            }

          } else if (type.equalsIgnoreCase("B")) {

            System.out.print("Number of floors: ");
            int floors = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Sale price: ");
            double price = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Garden? (yes/no): ");
            boolean hasGarden = scanner.nextLine().trim().equalsIgnoreCase("yes");

            CreateHouseRequest house = CreateHouseRequest.builder()
                // base class fields:
                .estateId(randomId())
                .city(city)
                .postalCode(postalCode)
                .street(street)
                .streetNo(streetNo)
                .agentId(agentId)
                .areaSqm(areaSqm)
                // subclass fields:
                .floor(floors)
                .price(price)
                .hasGarden(hasGarden)
                .build();

            if (houseService.createNewHouse(house)) {
              System.out.println("House created with ID " + house.getEstateId());
            } else {
              System.out.println("House could not be created!");
            }
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
          int id = Integer.parseInt(scanner.nextLine());

          var estateOptional = estateService.findEstateById(id);

          if (estateOptional.isEmpty()) {
            System.out.println("Estate not found!");
            return;
          }

          var estate = estateOptional.get();


          System.out.printf("Current city is \"%s\". Enter new city (leave blank to keep):%n", estate.getCity());
          String newcity = scanner.nextLine().trim();
          if (!newcity.isBlank()) {
            estate.setCity(newcity);
          }

          System.out.printf("Current postal code is \"%s\". Enter new postal code (leave blank to keep):%n", estate.getPostal_code());
          String newpostalcode = scanner.nextLine().trim();
          if (!newpostalcode.isBlank()) {
            estate.setPostal_code(newpostalcode);
          }

          System.out.printf("Current street is \"%s\". Enter new street (leave blank to keep):%n", estate.getStreet());
          var newstreet = scanner.nextLine().trim();
          if (!newstreet.isBlank()) {
            estate.setStreet(newstreet);
          }

          System.out.printf("Current street number is \"%s\". Enter new street number (leave blank to keep):%n", estate.getStreet_no());
          var newstreetno = scanner.nextLine().trim();
          if (!newstreetno.isBlank()) {
            estate.setStreet_no(newstreetno);
          }

          System.out.printf("Current area is %.2f sqm. Enter new area (leave blank to keep):%n",
              estate.getArea_sqm());
          var newsqm = scanner.nextLine().trim();
          if (!newsqm.isBlank()) {
            try {
              estate.setArea_sqm(Double.parseDouble(newsqm));
            } catch (NumberFormatException e) {
              System.out.println("Invalid number, keeping old area.");
            }
          }

          if (estate instanceof Apartment apartment) {
            System.out.printf("Current floor is %d. Enter new floor (leave blank to keep):%n", apartment.getFloor());
            String newFloor = scanner.nextLine().trim();
            if (!newFloor.isBlank()) {
              try {
                apartment.setFloor(Integer.parseInt(newFloor));
              } catch (NumberFormatException e) {
                System.out.println("Invalid floor, keeping old value.");
              }
            }

            System.out.printf("Current rent is %.2f. Enter new rent (leave blank to keep):%n", apartment.getRent());
            String newRent = scanner.nextLine().trim();
            if (!newRent.isBlank()) {
              try {
                apartment.setRent(Double.parseDouble(newRent));
              } catch (NumberFormatException e) {
                System.out.println("Invalid rent, keeping old value.");
              }
            }

            System.out.printf("Current rooms count is %d. Enter new rooms (leave blank to keep):%n", apartment.getRooms());
            String newRooms = scanner.nextLine().trim();
            if (!newRooms.isBlank()) {
              try {
                apartment.setRooms(Integer.parseInt(newRooms));
              } catch (NumberFormatException e) {
                System.out.println("Invalid rooms, keeping old value.");
              }
            }

            System.out.printf("Has balcony? (currently %s) Enter yes/no (leave blank to keep):%n",
                apartment.getHas_balcony() ? "yes" : "no");
            String newBalcony = scanner.nextLine().trim();
            if (newBalcony.equalsIgnoreCase("yes")) {
              apartment.setHas_balcony(true);
            } else if (newBalcony.equalsIgnoreCase("no")) {
              apartment.setHas_balcony(false);
            }

            System.out.printf("Has kitchen? (currently %s) Enter yes/no (leave blank to keep):%n",
                apartment.getHas_kitchen() ? "yes" : "no");
            String newKitchen = scanner.nextLine().trim();
            if (newKitchen.equalsIgnoreCase("yes")) {
              apartment.setHas_kitchen(true);
            } else if (newKitchen.equalsIgnoreCase("no")) {
              apartment.setHas_kitchen(false);
            }

            estateService.saveApartment(apartment);
            System.out.println("Apartment has been updated!");

          } else if (estate instanceof House house) {
            System.out.printf("Current floors count is %d. Enter new floors (leave blank to keep):%n", house.getFloors());
            String newFloors = scanner.nextLine().trim();
            if (!newFloors.isBlank()) {
              try {
                house.setFloors(Integer.parseInt(newFloors));
              } catch (NumberFormatException e) {
                System.out.println("Invalid number, keeping old floors.");
              }
            }

            System.out.printf("Current price is %.2f. Enter new price (leave blank to keep):%n", house.getPrice());
            String newPrice = scanner.nextLine().trim();
            if (!newPrice.isBlank()) {
              try {
                house.setPrice(Double.parseDouble(newPrice));
              } catch (NumberFormatException e) {
                System.out.println("Invalid price, keeping old value.");
              }
            }

            System.out.printf("Has garden? (currently %s) Enter yes/no (leave blank to keep):%n",
                house.getHas_garden() ? "yes" : "no");
            String newGarden = scanner.nextLine().trim();
            if (newGarden.equalsIgnoreCase("yes")) {
              house.setHas_garden(true);
            } else if (newGarden.equalsIgnoreCase("no")) {
              house.setHas_garden(false);
            }

            estateService.saveHouse(house);
            System.out.println("House has been updated!");
          } else {
            System.out.println("Unknown estate type. Update canceled.");
          }
        }
        case "0" -> {
          return;
        }
        default -> System.out.println("Invalid choice.");
      }
    }
  }


  // Contract Management
  private void contractManagement(Scanner scanner) {
    while (true) {
      System.out.println("\n--- CONTRACT MANAGEMENT ---");
      System.out.println("1. Insert new Person");
      System.out.println("2. Sign a Contract");
      System.out.println("3. View all Contracts");
      System.out.println("0. Back to main menu");
      System.out.print("Select an option: ");

      switch (scanner.nextLine().trim()) {
        case "1" -> {
          System.out.println("Enter first name:");
          var firstName = scanner.nextLine().trim();

          System.out.println("Enter last name:");
          var lastName = scanner.nextLine().trim();

          System.out.println("Enter address:");
          var address = scanner.nextLine().trim();

          var person = Person.builder()
              .personId(randomId())
              .first_name(firstName)
              .last_name(lastName)
              .address(address)
              .build();

          contractService.save(person);
          System.out.println("Person created with ID " + person.getPersonId());
        }
        case "2" -> {
          signContract(scanner);
        }
        case "3" -> {
          var contracts = contractService.findAllContracts();
          for (var contract : contracts) {
            System.out.println("Contract No: " + contract.getContractNo() +
                ", Date: " + contract.getContractDate() +
                ", Place: " + contract.getPlace() +
                ", Person ID: " + contract.getPersonId() +
                ", Estate ID: " + contract.getEstateId());
          }
        }
        case "0" -> {
          return;
        }
        default -> System.out.println("Invalid choice.");
      }
    }
  }

  @Override
  public void run(String... args) throws Exception {

    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome!");
    cmdExec(scanner);
  }
}





