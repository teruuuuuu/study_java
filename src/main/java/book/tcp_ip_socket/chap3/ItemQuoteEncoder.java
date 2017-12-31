package book.tcp_ip_socket.chap3;

public interface ItemQuoteEncoder {
  byte[] encode(ItemQuote item) throws Exception;
}
