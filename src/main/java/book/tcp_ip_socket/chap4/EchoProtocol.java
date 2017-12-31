package book.tcp_ip_socket.chap4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
// import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
  static public final int BUFSIZE = 32;

  private Socket clntSock;
  private Logger logger;

  public EchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
  }

  @Override
  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = + " + clntSock.getInetAddress().getHostAddress() + ":" + clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());
    logger.writeEntry(entry);

    try {
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize;
      int totalBytesEchoed = 0;
      byte[] echoBuffer = new byte[BUFSIZE];
      // クライアントが接続をクローズするまで受信する
      entry.add("Received message = ");
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        entry = new ArrayList();
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
        entry.add(new String(echoBuffer, "UTF-8"));
        logger.writeEntry(entry);
      }
      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    }catch (IOException e){
      entry.add("Exception=" + e.getMessage());
      e.printStackTrace();
    }
    logger.writeEntry(entry);
  }
}
