package parser.combineter1;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringParserSpec {

  @Test
  public void stringParser() {
    ParseResult<String> result = Parser.string("Hello, World!").invoke("Hello, World!");
    ParseResult.Success<String> ret = (ParseResult.Success<String>)result;
    System.out.println(ret.value);
    assertEquals("Hello, World!", ret.value);
  }
}
