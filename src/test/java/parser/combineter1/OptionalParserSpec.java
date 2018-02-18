package parser.combineter1;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class OptionalParserSpec {

  @Test
  public void optionalParser(){
    Parser<Optional<String>> hello = Parser.string("Hello").option();
    System.out.println(hello.invoke(""));
    System.out.println(hello.invoke("Hello"));
    System.out.println(hello.invoke("HelloHello"));
    System.out.println(hello.invoke("HelloHelloHello"));
  }
}
