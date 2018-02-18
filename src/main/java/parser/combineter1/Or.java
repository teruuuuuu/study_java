package parser.combineter1;

public class Or<X> implements Parser<X> {
  private Parser<X> lhs;
  private Parser<X> rhs;
  public Or(Parser<X> lhs, Parser<X> rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public ParseResult<X> invoke(String input) {
    ParseResult<X> lresult = lhs.invoke(input);
    if(lresult instanceof ParseResult.Failure<?>) {
      return rhs.invoke(input);
    } else {
      return lresult;
    }
  }
}