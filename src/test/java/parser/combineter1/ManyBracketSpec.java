package parser.combineter1;

import org.junit.Test;

public class ManyBracketSpec {

  @Test
  public void manyBracketParser() {
    Parser<?> hellos = Parser.string("world").manyBracket(Parser.string("Hello("), Parser.string(")"));
    //System.out.println(hellos.invoke("Hello(world)"));
    System.out.println(hellos.invoke("Hello(Hello(world))"));

    //Parser.string("world").bracket(Parser.string("Hello("), Parser.string(")")).cat(Parser.EOF());
  }
}
