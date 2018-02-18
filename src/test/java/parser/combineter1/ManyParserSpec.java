package parser.combineter1;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManyParserSpec {

  @Test
  public void manyParser() {
    Parser<List<String>> hellos = Parser.string("Hello").many();
    tes(hellos.invoke(""));
    tes(hellos.invoke("Hello"));
    tes(hellos.invoke("HelloHello"));
    tes(hellos.invoke("HelloHelloHello"));


    tes(hellos.invoke("HelloHelloW W Hello"));
  }

  void tes(ParseResult result){
    System.out.println(result);
    assertEquals(true, parseResult(result));
    System.out.println("next: " + ((ParseResult.Success<List<String>>)result).next);
  }

  boolean parseResult(ParseResult<?> result) {
    return result instanceof ParseResult.Success<?> ? true : false;
  }

  String parseNext(ParseResult.Success<?> result) {
    return result.next;
  }
}
