package book.tcp_ip_socket.chap4.factory;

import book.tcp_ip_socket.chap4.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerDispatcher implements Dispatcher, Runnable{
  ServerSocket serverSocket;
  Logger logger;
  ProtocolFactory protocolFactory;

  public ThreadPerDispatcher(ServerSocket serverSocket, Logger logger, ProtocolFactory protocolFactory) {
    this.serverSocket = serverSocket;
    this.logger = logger;
    this.protocolFactory = protocolFactory;
  }

  @Override
  public void startDispatching(ServerSocket serverSocket,
                               Logger logger, ProtocolFactory protocolFactory) {
    new ThreadPerDispatcher(serverSocket, logger, protocolFactory).run();
  }


  @Override
  public void run() {
    for (;;) {
      try{
        Socket clntSock = serverSocket.accept();
        Runnable protocol = protocolFactory.createProtocol(clntSock, logger);
        Thread thread = new Thread(protocol);
        thread.start();
        logger.writeEntry("Created and started Thread = " + thread.getName());
      } catch (IOException e){
        logger.writeEntry("Exception = " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
