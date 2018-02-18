package parser.combineter1;

import org.junit.Test;
import parser.combineter1.type.Tuple2;
import parser.combineter1.type.Tuple3;

import java.util.List;

public class BracketSpec {

  @Test
  public void bracketParser() {
    Parser<?> hellos = Parser.string("world").bracket(Parser.string("Hello("), Parser.string(")")).cat(Parser.EOF());
    System.out.println(hellos.invoke("Hello(world)"));
    System.out.println(hellos.invoke("Hello(Hello(world))"));

    Parser.string("world").bracket(Parser.string("Hello("), Parser.string(")")).cat(Parser.EOF());
  }
}
