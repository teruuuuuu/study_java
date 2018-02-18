package parser.combineter1;

import parser.combineter1.type.Tuple2;

public class Cat<X, Y> implements Parser<Tuple2<X, Y>> {
  private Parser<X> lhs;
  private Parser<Y> rhs;
  public Cat(Parser<X> lhs, Parser<Y> rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public ParseResult<Tuple2<X, Y>> invoke(String input) {
    ParseResult<X> lresult = lhs.invoke(input);

    if(lresult instanceof ParseResult.Success<?>) {
      X value1 = ((ParseResult.Success<X>)lresult).value;
      String next = ((ParseResult.Success<X>)lresult).next;
      ParseResult<Y> rresult = rhs.invoke(next);

      if(rresult instanceof ParseResult.Success<?>) {
        Y value2 = ((ParseResult.Success<Y>)rresult).value;
        return new ParseResult.Success(
                new Tuple2<>(value1, value2),
                ((ParseResult.Success<Y>)rresult).next
        );
      } else {
        return new ParseResult.Failure<>(next + " is not match", input);
      }
    } else {
      return new ParseResult.Failure<>(input + " is not match", input);
    }
  }
}


