package parser.combineter1;

import parser.combineter1.type.RTuple3;
import parser.combineter1.type.Tuple2;
import parser.combineter1.type.Tuple3;

import java.util.ArrayList;
import java.util.List;

public class ManyBracket<X, Y, Z> implements Parser<Tuple3<X, Y, Z>> {
  private Parser<X> ohs;
  private Parser<Y> ihs;
  private Parser<Z> ehs;

  public ManyBracket(Parser<X> shs, Parser<Y> ihs, Parser<Z> ehs) {
    this.ohs = shs;
    this.ihs = ihs;
    this.ehs = ehs;
  }

  @Override
  public ParseResult invoke(String input) {
    return parseSeq(input);
  }


  ParseResult<RTuple3<X, Y, Z>> parseSeq(String input) {
    ParseResult<X> oresult = ohs.invoke(input);
    if(oresult instanceof ParseResult.Success<?>) {
      X value1 = ((ParseResult.Success<X>) oresult).value;
      String next = ((ParseResult.Success<X>) oresult).next;


      ParseResult<RTuple3<X, Y, Z>> ioresult = parseSeq(next);
      if (ioresult instanceof ParseResult.Success<?>) {
        next = ((ParseResult.Success) ioresult).next;
        ParseResult<Z> eoresult = ehs.invoke(next);
        if (eoresult instanceof ParseResult.Success<?>) {
          Z value3 = ((ParseResult.Success<Z>) eoresult).value;
          return new ParseResult.Success(
                  new RTuple3<>(value1, new Tuple2(ioresult, null), value3),
                  ((ParseResult.Success<Z>) eoresult).next
          );
        } else {
          return new ParseResult.Failure<>(next + " is not match", input);
        }
      } else if (ioresult instanceof ParseResult.Failure<?>) {
        // 再帰的に出ない
        ParseResult<Y> iresult = ihs.invoke(next);
        Y value2 = ((ParseResult.Success<Y>) iresult).value;
        next = ((ParseResult.Success<Y>) iresult).next;
        ParseResult<Z> eresult = ehs.invoke(next);
        if (eresult instanceof ParseResult.Success<?>) {
          Z value3 = ((ParseResult.Success<Z>) eresult).value;
          return new ParseResult.Success(
                  new RTuple3<>(value1, new Tuple2(null, value2), value3),
                  ((ParseResult.Success<Z>) eresult).next
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


