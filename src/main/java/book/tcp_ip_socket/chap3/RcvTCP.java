package book.tcp_ip_socket.chap3;

import java.net.ServerSocket;
import java.net.Socket;

public class RcvTCP {
  int port;

  public RcvTCP(int port){
    this.port = port;
  }

  public static void main(String... args) {
    if(args.length != 1)
      throw new IllegalArgumentException("Parameter(s): <Port>");

    RcvTCP rcvTCP = new RcvTCP(Integer.parseInt(args[0]));
    rcvTCP.run();
  }

  public void run(){
    try {
      ServerSocket servSock = new ServerSocket(port);
      Socket clntSock = servSock.accept();

      ItemQuoteDecoder decoder = new ItemQuoteDecoderText();
      ItemQuote quote = decoder.decode(clntSock.getInputStream());
      System.out.println("Received Text-Encoding Quote:");
      System.out.println(quote);

      clntSock.close();
      servSock.close();
    } catch (Exception e){
      e.printStackTrace();
    }

  }
}
