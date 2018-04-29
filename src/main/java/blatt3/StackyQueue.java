package blatt3;

/**
 * Die Klasse StackyQueue soll eine Warteschlange auf Basis der Klasse {@link DynamicStack}
 * implementieren. Es soll ausschließlich die Klasse {@link DynamicStack} zur Datenspeicherung
 * verwendet werden.
 */
public class StackyQueue {

  private DynamicStack in;
  private DynamicStack out;

  /**
   * Diese Methode ermittelt die Länge der Warteschlange.
   *
   * @return die Länge der Warteschlange
   */
  public int getLength() {
    return in.getLength() + out.getLength();
  }

  /**
   * Dieser Kontruktor initialisiert eine neue Schlange.
   */
  public StackyQueue(int growthFactor, int maxOverhead) {
    in = new DynamicStack(growthFactor, maxOverhead);
    out = new DynamicStack(growthFactor, maxOverhead);

  }

  /**
   * Diese Methode reiht ein Element in die Schlange ein.
   *
   * @param value der einzufügende Wert
   */
  public void enqueue(int value) {
    in.pushBack(value);
  }


  /**
   * Diese Methode entfernt ein Element aus der Warteschlange.
   *
   * @return das entfernte Element
   */
  public int dequeue() {
    if (out.getLength() == 0) {
      while (in.getLength() != 0) {
        out.pushBack(in.popBack());
      }
    }
    return out.popBack();
  }
}
