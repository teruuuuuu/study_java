package parser.combineter1.type;


import java.util.function.BiFunction;

public class Tuple2<X, Y> {
  public final X item1;
  public final Y item2;

  public Tuple2(X item1, Y item2) {
    this.item1 = item1;
    this.item2 = item2;
  }

  public X item1() {
    return item1;
  }

  public Y item2() {
    return item2;
  }

  public <U> U extract(BiFunction<X, Y, U> f) {
    return f.apply(item1, item2);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tuple2<?, ?> tp2 = (Tuple2<?, ?>) o;

    if (!item1.equals(tp2.item1)) return false;
    return item2.equals(tp2.item2);
  }

  @Override
  public int hashCode() {
    int result = item1.hashCode();
    result = 31 * result + item2.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Tuple2{" + "item1=" + item1 + ", item2=" + item2 + '}';
  }
}
