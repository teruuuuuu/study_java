package book.tcp_ip_socket.chap4.factory;

import book.tcp_ip_socket.chap4.EchoProtocol;
import book.tcp_ip_socket.chap4.Logger;

import java.net.Socket;

public class EchoProtocolFactory implements ProtocolFactory {
  @Override
  public Runnable createProtocol(Socket clntSock, Logger logger) {
    return new EchoProtocol(clntSock, logger);
  }
}
