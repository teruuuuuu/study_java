package book.tcp_ip_socket.chap2.dto;

import java.io.Serializable;

public class TcpMessage implements Serializable{
  int messageType;
  String message;

  public TcpMessage(int messageType, String message) {
    this.messageType = messageType;
    this.message = message;
  }

  public int getMessageType() {
    return messageType;
  }
  public void setMessageType(int messageType) {
    this.messageType = messageType;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
