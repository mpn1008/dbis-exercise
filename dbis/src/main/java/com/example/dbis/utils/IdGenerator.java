package com.example.dbis.utils;

import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {
  public static int generate() {
    return ThreadLocalRandom.current().nextInt(1000, 9999); // range: [1000, 9999)
  }
}
