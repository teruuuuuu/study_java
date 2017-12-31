package book.tcp_ip_socket.chap3;

import java.net.InetAddress;
import java.net.Socket;

public class SendTCP {
  public static void main(String... args) throws Exception {
    if (args.length != 2)
      throw new IllegalAccessException("Parameger(S): <Description> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);
    int destPort = Integer.parseInt(args[1]);

    Socket sock = new Socket(destAddr, destPort);
    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
            1000, 12999, true, false);

    ItemQuoteEncoder coder = new ItemQuoteEncoderText();
    byte[] codedQuote = coder.encode(quote);
    System.out.println("Sending Text-Encoded Quote (" +
            codedQuote.length + " bytes):");
    System.out.println(quote);
    sock.getOutputStream().write(codedQuote);

    //ItemQuoteDecoder decoder = new ItemQuoteDecoderBin();

    sock.close();
  }
}
