package parser.combineter1.type;


import java.util.function.BiFunction;

public class Tuple3<X, Y, Z> {
  public final X item1;
  public final Y item2;
  public final Z item3;

  public Tuple3(X item1, Y item2, Z item3) {
    this.item1 = item1;
    this.item2 = item2;
    this.item3 = item3;
  }

  public X item1() {
    return item1;
  }

  public Y item2() {
    return item2;
  }

  public Z item3() {
    return item3;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tuple3<?, ?, ?> tp3 = (Tuple3<?, ?, ?>) o;

    if (!item1.equals(tp3.item1)) return false;
    if (!item2.equals(tp3.item2)) return false;
    return item2.equals(tp3.item3);
  }

  @Override
  public int hashCode() {
    int result = item1.hashCode();
    result = 31 * result + item2.hashCode();
    result = 31 * result + item3.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Tuple3{" + "item1=" + item1 +
            ", item2=" + item2 + ", item3=" + item3 + '}';
  }
}
