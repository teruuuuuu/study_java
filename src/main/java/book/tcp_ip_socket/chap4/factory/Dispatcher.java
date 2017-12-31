package book.tcp_ip_socket.chap4.factory;

import book.tcp_ip_socket.chap4.Logger;

import java.net.ServerSocket;

public interface Dispatcher {
  void startDispatching(ServerSocket serverSocket,
                               Logger loggre, ProtocolFactory protocolFactory);
}
