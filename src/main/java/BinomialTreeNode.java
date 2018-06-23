import java.util.Iterator;
import java.util.LinkedList;

public class BinomialTreeNode {

  private LinkedList<BinomialTreeNode> children;
  private int key;

  public BinomialTreeNode(int key) {
    this.children = new LinkedList<>();
    this.key = key;
  }

  /**
   * Ermittelt das minimale Element im Teilbaum.
   *
   * @return das minimale Element
   */
  public int min() {
    return key;
  }

  /**
   * Gibt den Rang des Teilbaumes zurück.
   *
   * @return der Rang des Teilbaumes
   */
  public int rank() {
    return children.size();
  }

  /**
   * Entfernt den minimalen Knoten (= Wurzel) und gibt eine Menge von Teilbäumen zurück, in die der
   * aktuelle Baum zerfällt, wenn man den Knoten entfernt.
   *
   * @return die Menge von Teilbäumen
   */
  public BinomialTreeNode[] deleteMin() {
    BinomialTreeNode[] res = new BinomialTreeNode[children.size()];
    Iterator<BinomialTreeNode> it = children.descendingIterator();
    int j = 0;
    while (it.hasNext()) {
      res[j++] = it.next();
    }
    return res;
  }

  /**
   * Diese Methode vereint zwei Bäume des gleichen Ranges.
   *
   * @param a der erste Baum
   * @param b der zweite Baum
   * @return denjenigen der beiden Bäume, an den der andere angehängt wurde
   */
  public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
    if (a.rank() != b.rank()) {
      throw new RuntimeException("unable to merge trees of unequal rank");
    }
    if (a.min() <= b.min()) {
      a.children.addFirst(b);
      return a;
    } else {
      b.children.addFirst(a);
      return b;
    }
  }

  @Override
  public String toString() {
    return super.toString() + " rank: " + rank();
  }
}
