package com.example.dbis.domain.model.ex4.log;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WriteAheadLog {
  long lsn;
  int taid;
  int pageId;
  String data;
  boolean isCommit;

  @Override
  public String toString() {
    return isCommit
        ? String.format("%d,%d,EOT", lsn, taid)
        : String.format("%d,%d,%d,%s", lsn, taid, pageId, data);
  }
}
