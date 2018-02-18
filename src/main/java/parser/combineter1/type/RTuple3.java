package parser.combineter1.type;

public class RTuple3<X, Y, Z> {
  public final X item1;
  public final Tuple2<RTuple3<X, Y, Z>,Y> item2;
  public final Z item3;

  public RTuple3(X item1, Tuple2<RTuple3<X, Y, Z>,Y> item2, Z item3) {
    this.item1 = item1;
    this.item2 = item2;
    this.item3 = item3;
  }

  public X item1() {
    return item1;
  }

  public Tuple2<RTuple3<X, Y, Z>,Y> item2() {
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
    if (item2.item1 != null){
      result += 31 * item2.item1.hashCode();
    } else {
      result += 31 * item2.item2.hashCode();
    }
    result = 31 * result + item3.hashCode();
    return result;
  }

  @Override
  public String toString() {
    if(item2.item1 != null){
      return "RTuple3{" + "item1=" + item1 +
              ", item2=" + item2.item1 + ", item3=" + item3 + '}';
    } else {
      return "Tuple3{" + "item1=" + item1 +
              ", item2=" + item2.item2 + ", item3=" + item3 + '}';
    }
  }
}
