package parser.combineter1;

import java.util.function.BiFunction;

public class BracketParser<X, Y> {
  public final X outer;
  public final Y inner;

  public BracketParser(X outer, Y inner) {
    this.outer = outer;
    this.inner = inner;
  }

  public X outer() {
    return outer;
  }

  public Y inner() {
    return inner;
  }

  public <U> U extract(BiFunction<X, Y, U> f) {
    return f.apply(outer, inner);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BracketParser<?, ?> bp2 = (BracketParser<?, ?>) o;

    if (!outer.equals(bp2.outer)) return false;
    return inner.equals(bp2.inner);
  }

  @Override
  public int hashCode() {
    int result = outer.hashCode();
    result = 31 * result + inner.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "BracketParser{" + "outer=" + outer + ", inner=" + inner + '}';
  }
}
