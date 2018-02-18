package parser.combineter1;

import org.junit.Test;
import parser.combineter1.type.Tuple2;

import static org.junit.Assert.assertEquals;

public class OrSpec {

  @Test
  public void orParser() {
    Parser<String> helloOrWorld = Parser.string("Hello").or(Parser.string("World"));
    Parser<Tuple2<String, String>> hw = helloOrWorld.cat(helloOrWorld);

    assertEquals(true, parseResult(hw.invoke("HelloWorld")));
    assertEquals(true, parseResult(hw.invoke("HelloHello")));
    assertEquals(true, parseResult(hw.invoke("WorldHello")));
    assertEquals(true, parseResult(hw.invoke("WorldWorld")));
    assertEquals(false, parseResult(hw.invoke("Heloooowooooorld")));

    System.out.println(hw.invoke("WorldWorld"));
    System.out.println(hw.invoke("Heloooowooooorld"));
  }

  boolean parseResult(ParseResult<?> result) {
    return result instanceof ParseResult.Success<?> ? true : false;
  }
}
