package book.tcp_ip_socket.chap4.factory;

import book.tcp_ip_socket.chap2.TcpEchoClient;
import book.tcp_ip_socket.chap4.ConsoleLogger;
import book.tcp_ip_socket.chap4.Logger;
import book.tcp_ip_socket.chap4.thread.TCPEchoServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class TcpEchoMain3 {

  public static void main(String... args) throws IOException, InterruptedException{
    int port = 50190;

    ServerSocket servSock = new ServerSocket(port);
    ConsoleLogger logger = new ConsoleLogger();
    EchoProtocolFactory echoProtocolFactory = new EchoProtocolFactory();

    ExecutorService exec = Executors.newCachedThreadPool();
    exec.submit(new ThreadPerDispatcher(servSock, logger, echoProtocolFactory));

    sleep(1000);
    new TcpEchoClient("localhost", "hello echo serverrrrrrrrrrrrr".getBytes(), port).run();
    sleep(1000);
    new TcpEchoClient("localhost", "hello echo server123".getBytes(), port).run();
  }
}
