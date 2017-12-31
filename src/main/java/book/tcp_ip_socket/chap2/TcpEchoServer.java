package book.tcp_ip_socket.chap2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpEchoServer implements Runnable{
  int port;
  public TcpEchoServer(int port){
    this.port = port;
  }

  private static final int BUFSIZE = 32;

  public static void main(String... args) throws IOException {
    if(args.length != 1)
      throw new IllegalArgumentException("Parameter(s): <Port>");
    TcpEchoServer tcpEchoServer = new TcpEchoServer(Integer.parseInt(args[0]));
    tcpEchoServer.run();
  }

  @Override
  public void run(){
    try{
      ServerSocket servSocket = new ServerSocket(port);
      int recvMsgSize;
      byte[] byteBuffer = new byte[BUFSIZE];


      while (true){ // 繰り返します実行で接続を受けて処理する
        Socket clntSock = servSocket.accept(); // クライアント接続を取得する

        System.out.println("Handling client at " + clntSock.getPort());

        InputStream in = clntSock.getInputStream();
        OutputStream out = clntSock.getOutputStream();

        while ((recvMsgSize = in.read(byteBuffer)) != -1){
          out.write(byteBuffer, 0, recvMsgSize);
          System.out.println(new String(byteBuffer, "UTF-8") );
          System.out.println("");
        }

        out.write("server recevied massage\n".getBytes());
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }
}
