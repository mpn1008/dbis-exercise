package com.example.dbis.app.ex4;

import com.example.dbis.domain.model.ex4.client.ClientManager;
import com.example.dbis.infra.ex4.RecoveryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleApp implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    System.out.println("\n--- Starting Logging & Recovery Program ---\n");

    if (getConsoleInput("[1]    Start the recovery?"))
      RecoveryManager.getInstance().startRecovery();

    System.out.println("");

    if (getConsoleInput("[2]    Start the clients?"))
      ClientManager.getInstance().startClients();

    System.out.println("");
  }

  static private boolean getConsoleInput(String label) {
    // Ask user if recovery should be started
    String ret = "";
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    do {
      try {
        System.out.print(label + " [y/n]: ");
        ret = stdin.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } while (!(ret.equals("n") || ret.equals("y")));

    return ret.equals("y");
  }
}