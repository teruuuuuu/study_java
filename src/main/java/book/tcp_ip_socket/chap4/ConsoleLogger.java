package book.tcp_ip_socket.chap4;

import java.util.Collection;
import java.util.Iterator;

public class ConsoleLogger implements Logger {

  @Override
  public void writeEntry(Collection entry) {
    for(Iterator line = entry.iterator(); line.hasNext();)
      System.out.println(line.next());
    System.out.println();
  }

  @Override
  public void writeEntry(String entry) {
    System.out.println(entry);
    System.out.println();
  }
}
