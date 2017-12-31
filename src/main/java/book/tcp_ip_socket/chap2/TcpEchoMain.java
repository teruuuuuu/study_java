package book.tcp_ip_socket.chap2;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class TcpEchoMain {

  public static void main(String... args) throws IOException, InterruptedException{
    int port = 50190;

    // ExecutorService exec = Executors.newSingleThreadExecutor();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.submit(new TcpEchoServer(port));

    sleep(1000);
    new TcpEchoClient("localhost", "hello echo serverrrrrrrrrrrrr".getBytes(), port).run();
    sleep(1000);
    new TcpEchoClient("localhost", "hello echo server123".getBytes(), port).run();
  }
}
