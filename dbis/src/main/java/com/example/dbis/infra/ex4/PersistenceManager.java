package com.example.dbis.infra.ex4;

import com.example.dbis.domain.model.ex4.log.WriteAheadLog;
import com.example.dbis.domain.model.ex4.page.PageData;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PersistenceManager {

  static final private PersistenceManager _manager;
  private final Hashtable<Integer, PageData> buffer = new Hashtable<>();
  private final Set<Integer> committedTransactions = ConcurrentHashMap.newKeySet();
  private final Object logLock = new Object();
  private final List<WriteAheadLog> _writeAheadLogs = new ArrayList<>();
  private volatile long flushedLSN = getMaxLSNFromLogFile();
  // TODO Add class variables if necessary
  private final AtomicInteger lsnCounter = new AtomicInteger(1);
  private final AtomicInteger transactionCounter = new AtomicInteger(1);
  private final File logFile = new File("log.txt");

  public PersistenceManager() throws IOException {
  }
  static {
    try {
      _manager = new PersistenceManager();
    } catch (Throwable e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  static public PersistenceManager getInstance() {
    return _manager;
  }

  public synchronized int beginTransaction() {

    return transactionCounter.getAndIncrement();
  }

  public void commit(int taid) throws IOException {
    // TODO handle commits
    long lsn = lsnCounter.getAndIncrement();
    var wal = WriteAheadLog.builder()
        .pageId(-1)
        .taid(taid)
        .lsn(lsn)
        .isCommit(true)
        .build();
    _writeAheadLogs.add(wal);
    appendLogsToFile(_writeAheadLogs, "log.txt");
    _writeAheadLogs.clear();
    committedTransactions.add(taid);
    flush();
  }

  public synchronized void flush() throws IOException {
    if (buffer.size() <= 5) return;

    Iterator<Map.Entry<Integer, PageData>> it = buffer.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<Integer, PageData> entry = it.next();
      int pageId = entry.getKey();
      PageData pd = entry.getValue();
      if (pd.pageLsn <= flushedLSN) {
        flushPageToDisk(pageId, pd);
        it.remove();
      }
    }
  }

  private void flushPageToDisk(int pageId, PageData pd) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("page_" + pageId + ".txt"))) {
      writer.write(pd.toString());
      writer.newLine();
      writer.write(pd.data);
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void writeLog(WriteAheadLog log) throws IOException {
    synchronized (logLock) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
        writer.write(log.toString());
        writer.newLine();
      }
    }
  }

  private void appendLogsToFile(List<WriteAheadLog> logs, String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
      long maxLsnInBatch = -1;
      for (WriteAheadLog log : logs) {
        writer.write(log.toString());
        writer.newLine();
        if (maxLsnInBatch > flushedLSN) {
          flushedLSN = maxLsnInBatch;
        }
      }
      writer.flush(); // optional but ensures logs are pushed to OS immediately
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private long getMaxLSNFromLogFile() throws IOException {
    long maxLSN = -1;
    try (BufferedReader reader = new BufferedReader(new FileReader("log.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        long lsn = Long.parseLong(parts[0]);
        if (lsn > maxLSN) {
          maxLSN = lsn;
        }
      }
    }
    return maxLSN;
  }

  public void write(int taid, int page, String data) {
    long lsn = lsnCounter.getAndIncrement();
    PageData pageData = PageData.builder()
        .pageLsn(lsn)
        .data(data)
        .build();
    WriteAheadLog log = WriteAheadLog.builder()
        .pageId(page)
        .taid(taid)
        .lsn(lsn)
        .isCommit(false)
        .data(data)
        .build();
    _writeAheadLogs.add(log);
    buffer.put(page, pageData);
  }
}
