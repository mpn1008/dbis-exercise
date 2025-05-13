package com.example.dbis.app.ex3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class StdSchedule {
  private final ConnectionFactory connectionFactory;

  public void triggerS3() throws SQLException, InterruptedException {

    Connection i1 = connectionFactory.newConnection();
    Statement cs = i1.createStatement();
    cs.execute("DROP TABLE if exists dissheet3;" +
        "CREATE TABLE dissheet3 (" +
        "id integer primary key," +
        "name VARCHAR(50));" +
        "INSERT INTO dissheet3 (id, name) VALUES (1, 'A'),(2, 'B'),(3, 'C')," +
        "                                  (4, 'D'),(5, 'E');");
    i1.close();

    Connection c1 = connectionFactory.newConnection();
    c1.setAutoCommit(false);
    //c1.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    //c1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    Connection c2 = connectionFactory.newConnection();
    c2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    c2.setAutoCommit(false);

    List<RunnableOperation> operations = new ArrayList<>(Arrays.asList(
        new RunnableOperation(c2, 'r', "SELECT name FROM dissheet3 WHERE id = 1;"), // r2(x)
        new RunnableOperation(c1, 'w', "UPDATE dissheet3 SET name = 'A_T1' WHERE id = 1;"), // w1(x)
        new RunnableOperation(c1, 'w', "UPDATE dissheet3 SET name = 'B_T1' WHERE id = 2;"), // w1(y)
        new RunnableOperation(c1, 'c', "COMMIT;"), // c1
        new RunnableOperation(c2, 'r', "SELECT name FROM dissheet3 WHERE id = 2;"), // r2(y)
        new RunnableOperation(c2, 'w', "UPDATE dissheet3 SET name = 'A_T2' WHERE id = 1;"), // w2(x)
        new RunnableOperation(c2, 'w', "UPDATE dissheet3 SET name = 'B_T2' WHERE id = 2;"), // w2(y)
        new RunnableOperation(c2, 'c', "COMMIT;") // c2
    ));

    ExecutorService executor_t1 = Executors.newFixedThreadPool(1);
    ExecutorService executor_t2 = Executors.newFixedThreadPool(1);
    for (RunnableOperation op : operations) {

      if (op.c == c1)
        executor_t1.execute(op);

      if (op.c == c2)
        executor_t2.execute(op);

      Thread.sleep(250);  // Sleep, so the threads in both pools get executed in the desired order

    }
    executor_t1.shutdown();
    executor_t2.shutdown();

    while (!executor_t1.isTerminated() && !executor_t2.isTerminated()) {
      Thread.sleep(1000);
      System.out.println("Waiting for threads");
    }

    System.out.println("Finished all threads");


    // GET Table at the end
    Connection i2 = connectionFactory.newConnection();
    Statement cs2 = i2.createStatement();
    ResultSet rs = cs2.executeQuery("SELECT id, name FROM dissheet3 ORDER BY id");
    while (rs.next())
      System.out.println(Integer.toString(rs.getInt("id")) + "," + rs.getString("name"));
    cs2.close();
  }

  public void triggerS1() throws SQLException, InterruptedException {
    Connection i1 = connectionFactory.newConnection();
    Statement cs = i1.createStatement();
    cs.execute("DROP TABLE if exists dissheet3;" +
        "CREATE TABLE dissheet3 (" +
        "id integer primary key," +
        "name VARCHAR(50));" +
        "INSERT INTO dissheet3 (id, name) VALUES (1, 'A'),(2, 'B'),(3, 'C')," +
        "                                  (4, 'D'),(5, 'E');");
    i1.close();

    Connection c1 = connectionFactory.newConnection();
    c1.setAutoCommit(false);
//    c1.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    //c1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    Connection c2 = connectionFactory.newConnection();
    c2.setAutoCommit(false);
    c2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    //c2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);


    //S1 = r1(x) w2(x) c2 w1(x) r1(x) c1
    List<RunnableOperation> operations = new ArrayList<>(Arrays.asList(

        new RunnableOperation(c1, 'r', "SELECT name FROM dissheet3 WHERE id = 1;"),
        new RunnableOperation(c2, 'w', "UPDATE dissheet3 SET name = 'A_T2' WHERE id = 1;"),
        new RunnableOperation(c2, 'c', "COMMIT;"),
        new RunnableOperation(c1, 'w', "UPDATE dissheet3 SET name = name || ' + T1' WHERE id = 1;"),
        new RunnableOperation(c1, 'r', "SELECT name FROM dissheet3 WHERE id = 1;"),
        new RunnableOperation(c1, 'c', "COMMIT;"))
    );
    ExecutorService executor_t1 = Executors.newFixedThreadPool(1);
    ExecutorService executor_t2 = Executors.newFixedThreadPool(1);
    for (RunnableOperation op : operations) {

      if (op.c == c1)
        executor_t1.execute(op);

      if (op.c == c2)
        executor_t2.execute(op);

      Thread.sleep(250);  // Sleep, so the threads in both pools get executed in the desired order

    }
    executor_t1.shutdown();
    executor_t2.shutdown();

    while (!executor_t1.isTerminated() && !executor_t2.isTerminated()) {
      Thread.sleep(1000);
      System.out.println("Waiting for threads");
    }

    System.out.println("Finished all threads");


    // GET Table at the end
    Connection i2 = connectionFactory.newConnection();
    Statement cs2 = i2.createStatement();
    ResultSet rs = cs2.executeQuery("SELECT id, name FROM dissheet3 ORDER BY id");
    while (rs.next())
      System.out.println(Integer.toString(rs.getInt("id")) + "," + rs.getString("name"));
    cs2.close();
  }

  public static class RunnableOperation implements Runnable {

    public final char readwrite;
    public final String op;
    public final Connection c;

    public RunnableOperation(Connection connection, char readwrite, String operation) {

      this.readwrite = readwrite;
      this.op = operation;
      this.c = connection;
    }

    public void run(){
      System.out.println(Thread.currentThread().getName()+"-sql = " + op);
      Statement st;
      try {
        st = c.createStatement();
        if (readwrite == 'r') {
          ResultSet rs = st.executeQuery(op);
          while (rs.next())
            System.out.println(rs.getString("name"));
        } else if (readwrite == 'w') {
          st.execute(op);
        } else if (readwrite == 'c') {

          c.commit();
        }

      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
