package book.tcp_ip_socket.chap4;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

public class FileLogger implements Logger {

  PrintWriter out;
  public FileLogger(String filename) throws IOException {
    out = new PrintWriter(new FileWriter(filename), true);
  }
  @Override
  public void writeEntry(Collection entry) {
    for (Iterator line = entry.iterator(); line.hasNext();)
      out.println(line.next());
    out.println();
  }

  @Override
  public void writeEntry(String entry) {
    out.println(entry);
    out.println();
  }
}
