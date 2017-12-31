package book.tcp_ip_socket.chap4.factory;

import book.tcp_ip_socket.chap4.Logger;

import java.net.Socket;

public interface ProtocolFactory {
  public Runnable createProtocol(Socket clntSock, Logger logger);

}
