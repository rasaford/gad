package blatt3;

/**
 * Die Klasse RingQueue soll eine zirkuläre Warteschlange auf Basis der Klasse {@link DynamicArray}
 * implementieren.
 */
public class RingQueue {

  private DynamicArray dynArr;

  private int size;

  private int from;

  private int to;

  public int getSize() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Dieser Konstruktor erzeugt eine neue Ringschlange. Ein leere Ringschlange habe stets eine Größe
   * von 0, sowie auf 0 gesetzte Objektvariablen to und from.
   *
   * @param growthFactor der Wachstumsfaktor des zugrundeliegenden dynamischen Feldes
   * @param maxOverhead der maximale Overhead des zugrundeliegenden dynamischen Feldes
   */
  public RingQueue(int growthFactor, int maxOverhead) {
    dynArr = new DynamicArray(growthFactor, maxOverhead);
    size = 0;
    from = 0;
    to = 0;
  }

  /**
   * Diese Methode reiht ein Element in die Schlange ein.
   *
   * @param value der einzufügende Wert
   */
  public void enqueue(int value) {
    dynArr.reportUsage(
        isEmpty() ? new EmptyInterval() : new NonEmptyInterval(from, to),
        size + 1
    );
    size++;
    to = size == 1 ? to : to + 1;
    dynArr.set(to, value);
  }

  /**
   * Diese Methode entfernt ein Element aus der Warteschlange.
   *
   * @return das entfernte Element
   */
  public int dequeue() {
    int result = dynArr.get(from);
    dynArr.set(from, 0);
    Interval range = dynArr.reportUsage(
        isEmpty() ? new EmptyInterval() : new NonEmptyInterval(from + 1, to),
        0
    );
    size--;
    from = range.getFrom();
    to = range.getTo();
    return result;
  }
}
