package com.example.dbis.app.ex3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public int readX_T2() {
    int x = jdbcTemplate.queryForObject("SELECT x FROM test_table WHERE id = 1", Integer.class);
    System.out.println("T2 reads x: " + x);
    return x;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void writeX_T1(int value) {
    jdbcTemplate.update("UPDATE test_table SET x = ? WHERE id = 1", value);
    System.out.println("T1 writes x: " + value);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void writeY_T1(int value) {
    jdbcTemplate.update("UPDATE test_table SET y = ? WHERE id = 1", value);
    System.out.println("T1 writes y: " + value);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public int readY_T2() {
    int y = jdbcTemplate.queryForObject("SELECT y FROM test_table WHERE id = 1", Integer.class);
    System.out.println("T2 reads y: " + y);
    return y;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void writeX_T2(int value) {
    jdbcTemplate.update("UPDATE test_table SET x = ? WHERE id = 1", value);
    System.out.println("T2 writes x: " + value);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void writeY_T2(int value) {
    jdbcTemplate.update("UPDATE test_table SET y = ? WHERE id = 1", value);
    System.out.println("T2 writes y: " + value);
  }

}
