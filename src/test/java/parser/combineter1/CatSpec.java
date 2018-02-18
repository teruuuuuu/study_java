package parser.combineter1;

import org.junit.Test;
import parser.combineter1.type.Tuple2;

import static org.junit.Assert.assertEquals;

public class CatSpec {

  @Test
  public void catParser() {
    Parser<String> hello = Parser.string("Hello, ");
    Parser<String> world = Parser.string("World!");
    Parser<Tuple2<String, String>> helloWorld = hello.cat(world);


    boolean exceptAns = true;
    boolean resultAns = true;
    ParseResult<Tuple2<String, String>> ret1 = helloWorld.invoke("Hello, World!");
    if(ret1 instanceof ParseResult.Success<?>) {
      ParseResult.Success<Tuple2<String, String>> ans1 = (ParseResult.Success<Tuple2<String, String>>)ret1;
      assertEquals("Hello, ", ans1.value.item1);
      assertEquals("World!", ans1.value.item2);
      resultAns = true;
    } else {
      resultAns = false;
    }
    assertEquals(exceptAns, resultAns);

    exceptAns = true;
    resultAns = true;
    ParseResult<Tuple2<String, String>> ret2 = helloWorld.invoke("Hello, World!!");
    if(ret2 instanceof ParseResult.Success<?>) {
      resultAns = true;
    } else {
      resultAns = false;
    }
    assertEquals(exceptAns, resultAns);


    exceptAns = false;
    resultAns = true;
    ParseResult<Tuple2<String, String>> ret3 = helloWorld.invoke("Hello!, World!");
    if(ret3 instanceof ParseResult.Success<?>) {
      resultAns = true;
    } else {
      resultAns = false;
    }
    assertEquals(exceptAns, resultAns);
  }
}
