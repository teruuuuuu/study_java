package book.tcp_ip_socket.chap2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TcpEchoClient implements Runnable{
  String server;
  byte[] byteBuffer;
  int port;

  public TcpEchoClient(String server, byte[] byteBuffer, int port){
    this.server = server;
    this.byteBuffer = byteBuffer;
    this.port = port;
  }

  public static void main(String... args) throws IOException {
    if ((args.length < 2) || (args.length > 3))
      throw new IllegalArgumentException("Parameter(s):<Server> <Word> [<Port>]");

    TcpEchoClient tcpEchoClient = new TcpEchoClient(args[0], args[1].getBytes(), Integer.parseInt(args[2]));
    tcpEchoClient.run();
  }

  public void run() {
    try{

      Socket socket = new Socket(server, port);
      System.out.println("Connected to server...sending echo String");

      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();

      out.write(byteBuffer);

      int totalByteRcvd = 0;
      int bytesRcvd;
      while(totalByteRcvd < byteBuffer.length) {
        if((bytesRcvd = in.read(byteBuffer, totalByteRcvd,
                byteBuffer.length - totalByteRcvd)) == -1)
          throw new SocketException("Connecton closed parameturely");
        totalByteRcvd += bytesRcvd;
      }

    }catch (IOException e){
      e.printStackTrace();
    }
  }
}
