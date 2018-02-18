package parser.combineter1;

import org.junit.Test;
import parser.combineter1.type.Tuple2;

import java.util.List;

public class EOFParserSpec {

  @Test
  public void eofParser(){
    Parser<Tuple2<List<String>, String>> hellos = Parser.string("Hello").many().cat(Parser.EOF());
    System.out.println(hellos.invoke("Hello"));
    System.out.println(hellos.invoke("Hello, World!"));
  }
}
