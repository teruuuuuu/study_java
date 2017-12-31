package book.tcp_ip_socket.chap4.thread;

import book.tcp_ip_socket.chap2.TcpEchoClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class TcpEchoMain2 {
  public static void main(String... args) throws IOException, InterruptedException{
    int port = 50190;

    // ExecutorService exec = Executors.newSingleThreadExecutor();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.submit(new TCPEchoServerThread(port));


    sleep(1000);
    new TcpEchoClient("localhost", "hello echo serverrrrrrrrrrrrr".getBytes(), port).run();
    sleep(1000);
    new TcpEchoClient("localhost", "hello echo server123".getBytes(), port).run();
  }
}
