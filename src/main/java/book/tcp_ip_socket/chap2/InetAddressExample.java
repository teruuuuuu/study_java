package book.tcp_ip_socket.chap2;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {

  public static void main(String... args) {

    try {
      InetAddress address = InetAddress.getLocalHost();
      System.out.println("Local Host:");
      System.out.println("\t" + address.getHostName());
      System.out.println("\t" + address.getHostAddress());
    }catch (UnknownHostException e){
      e.printStackTrace();
    }

    for(int i = 0; i < args.length; i++) {
      try {
        InetAddress[] addressList = InetAddress.getAllByName(args[i]);
        System.out.println(args[i] + ":");
        System.out.println("\t" + addressList[0].getHostName());
        for(int j = 0; j < addressList.length; j++){
          System.out.println("\t" + addressList[j].getHostAddress());
        }
      }catch (UnknownHostException e){
        e.printStackTrace();
      }
    }
  }
}
