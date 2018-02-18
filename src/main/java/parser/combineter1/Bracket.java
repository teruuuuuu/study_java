package parser.combineter1;

import parser.combineter1.type.Tuple2;
import parser.combineter1.type.Tuple3;

public class Bracket<X, Y, Z> implements Parser<Tuple3<X, Y, Z>> {
  private Parser<X> ohs;
  private Parser<Y> ihs;
  private Parser<Z> ehs;

  public Bracket(Parser<X> ohs, Parser<Y> ihs, Parser<Z> ehs) {
    this.ohs = ohs;
    this.ihs = ihs;
    this.ehs = ehs;
  }

  @Override
  public ParseResult<Tuple3<X, Y, Z>> invoke(String input) {
    ParseResult<X> oresult = ohs.invoke(input);


    if(oresult instanceof ParseResult.Success<?>) {
      X value1 = ((ParseResult.Success<X>)oresult).value;
      String next = ((ParseResult.Success<X>)oresult).next;
      ParseResult<Y> iresult = ihs.invoke(next);

      if(iresult instanceof ParseResult.Success<?>) {
        Y value2 = ((ParseResult.Success<Y>)iresult).value;
        next = ((ParseResult.Success<Y>)iresult).next;
        ParseResult<Z> eresult = ehs.invoke(next);

        if(eresult instanceof ParseResult.Success<?>) {
          Z value3 = ((ParseResult.Success<Z>)eresult).value;
          return new ParseResult.Success(
                new Tuple3<>(value1, value2, value3),
                ((ParseResult.Success<Z>)eresult).next
          );
        } else {
          return new ParseResult.Failure<>(next + " is not match", input);
        }
      } else {
        return new ParseResult.Failure<>(next + " is not match", input);
      }
    } else {
      return new ParseResult.Failure<>(input + " is not match", input);
    }
  }
}


