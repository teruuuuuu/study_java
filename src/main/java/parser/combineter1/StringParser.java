package parser.combineter1;

public class StringParser implements Parser<String> {
  public final String literal;
  public StringParser(String literal) {
    this.literal = literal;
  }

  public String getLiteral(){
    return this.literal;
  }

  @Override
  public ParseResult<String> invoke(String input) {
    if(input.startsWith(literal)) {
      return new ParseResult.Success<>(literal, input.substring(literal.length()));
    }else {
      return new ParseResult.Failure<>("expect: " + literal, input);
    }
  }
}