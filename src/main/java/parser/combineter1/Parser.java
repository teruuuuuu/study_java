package parser.combineter1;

import parser.combineter1.type.Tuple2;
import parser.combineter1.type.Tuple3;

import java.util.List;
import java.util.Optional;

public interface Parser<T> {
  ParseResult<T> invoke(String input);
  static Parser<String> string(String literal) {
    return new StringParser(literal);
  }

  default <U> Parser<Tuple2<T, U>> cat(Parser<U> rhs) {
    return new Cat<>(this, rhs);
  }
  default <U, V> Parser<Tuple3<T, U, V>> bracket(Parser<U> ihs, Parser<V> ehs ) {
    return new Bracket<>(this, ihs, ehs);
  }
  default Parser<T> or(Parser<T> rhs) {
    return new Or<>(this, rhs);
  }
  default Parser<List<T>> many() {
    return new ManyParser<T>(this);
  }
  static Parser<String> EOF() {
    return new EOFParser();
  }
  default Parser<Optional<T>> option() {
    return new OptionParser<>(this);
  }
}

