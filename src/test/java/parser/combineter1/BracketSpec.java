package parser.combineter1;

import org.junit.Test;
import parser.combineter1.type.Tuple2;
import parser.combineter1.type.Tuple3;

import java.util.List;

public class BracketSpec {

  @Test
  public void bracketParser() {
    Parser<Tuple3<String, String, String>> hellos = Parser.string("Hello(").bracket(Parser.string("world"), Parser.string(")"));
    System.out.println(hellos.invoke("Hello(world)"));
  }
}
