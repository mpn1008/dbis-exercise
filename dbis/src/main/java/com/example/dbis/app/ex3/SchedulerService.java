package com.example.dbis.app.ex3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
  private final TransactionService transactionService;

  //S3 =  r2(x) w1(x) w1(y) c1 r2(y) w2(x) w2(y) c2
  public void executeScheduleS3() throws InterruptedException {
    CountDownLatch latch1 = new CountDownLatch(1); // After r2(x)
    CountDownLatch latch2 = new CountDownLatch(1); // After c1
    CountDownLatch latch3 = new CountDownLatch(1); // After r2(y)

    // Simulate T2
    Thread t2 = new Thread(() -> {
      try {
        // r2(x)
        transactionService.readX_T2();
        latch1.countDown(); // Signal T1 to proceed

        latch2.await(); // Wait for T1 to commit
        // r2(y)
        transactionService.readY_T2();
        latch3.countDown();

        // w2(x), w2(y)
        transactionService.writeX_T2(700);
        transactionService.writeY_T2(800);
        System.out.println("T2 committed.");
      } catch (Exception e) {
        log.error(e.getLocalizedMessage());
      }
    });

    // Simulate T1
    Thread t1 = new Thread(() -> {
      try {
        latch1.await(); // Wait until T2 has done r2(x)

        // w1(x), w1(y), c1
        transactionService.writeX_T1(500);
        transactionService.writeY_T1(600);
        System.out.println("T1 committed.");
        latch2.countDown(); // Signal T2 to continue
      } catch (Exception e) {
        log.error(e.getLocalizedMessage());
      }
    });

    t2.start();
    t1.start();

    latch3.await(); // Ensure schedule is completed before returning
    t1.join();
    t2.join();

    System.out.println("Schedule S3 complete.");
  }
}
