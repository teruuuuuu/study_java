package book.tcp_ip_socket.chap2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

public class ByteArray {
  static byte[] fromObject(Object o) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutput out = new ObjectOutputStream(bos);
    out.writeObject(o);
    byte[] bytes = bos.toByteArray();
    out.close();
    bos.close();
    return bytes;
  }
  static Object toObject(byte[] bytes) throws OptionalDataException, StreamCorruptedException, ClassNotFoundException, IOException{
    return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
  }
}
