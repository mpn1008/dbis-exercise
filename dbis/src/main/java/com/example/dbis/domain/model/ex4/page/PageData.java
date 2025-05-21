package com.example.dbis.domain.model.ex4.page;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageData {
  public long pageLsn;
  public String data;

  @Override
  public String toString() {
    return String.format("%d,%s", pageLsn, data);
  }
}