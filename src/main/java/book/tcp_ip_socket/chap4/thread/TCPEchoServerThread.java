package book.tcp_ip_socket.chap4.thread;


import book.tcp_ip_socket.chap4.ConsoleLogger;
import book.tcp_ip_socket.chap4.EchoProtocol;
import book.tcp_ip_socket.chap4.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServerThread implements Runnable{
  int port;
  public TCPEchoServerThread(int port){
    this.port = port;
  }
  public static void main(String... args) throws IOException {
    if (args.length != 1)
      throw new IllegalArgumentException("Parameter(s): <Port>");

    new TCPEchoServerThread(Integer.parseInt(args[0])).run();

  }

  public void run(){
    try{
      ServerSocket servSock = new ServerSocket(this.port);
      Logger logger = new ConsoleLogger();

      for (;;) {
        try {
          // クライアントの接続ごとにスレッドを生成する
          Socket clntSock = servSock.accept();
          EchoProtocol protocol = new EchoProtocol(clntSock, logger);
          Thread thread = new Thread(protocol);
          thread.start();
          logger.writeEntry("Created and started Thread = " + thread.getName());
        } catch (IOException e){
          e.printStackTrace();
        }
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }
}
