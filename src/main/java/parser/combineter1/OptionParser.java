package parser.combineter1;

import java.util.Optional;

public class OptionParser<T> implements Parser<Optional<T>> {
  private Parser<T> parser;
  public OptionParser(Parser<T> parser) {
    this.parser = parser;
  }

  @Override
  public ParseResult<Optional<T>> invoke(String input) {
    ParseResult<T> result = parser.invoke(input);
    if(result instanceof ParseResult.Failure<?>) {
      return new ParseResult.Success<>(Optional.ofNullable(null), input);
    } else {
      ParseResult.Success<T> success = (ParseResult.Success<T>)result;
      return new ParseResult.Success<>(Optional.of(success.value), success.next);
    }
  }
}