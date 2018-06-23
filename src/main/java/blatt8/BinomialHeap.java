package blatt8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BinomialHeap {

  // Sorted list of binomial trees in decreasing rank
  private List<BinomialTreeNode> trees;

  /**
   * Dieser Konstruktor baut einen leeren Haufen.
   */
  public BinomialHeap() {
    trees = new ArrayList<>();
  }

  /**
   * Diese Methode ermittelt das minimale Element im binomialen Haufen. Wenn es dieses nicht gibt
   * (bei leerem Haufen), soll eine RuntimeException geworfen werden.
   *
   * @return das minimale Element
   */
  public int min() {
    return findMin().min();
  }

  private BinomialTreeNode findMin() {
    if (trees.isEmpty()) {
      throw new RuntimeException("the tree is empty. Therefore unable to find a minimum");
    }
    BinomialTreeNode min = trees.get(0);
    for (BinomialTreeNode tree : trees) {
      if (tree.min() < min.min()) {
        min = tree;
      }
    }
    return min;
  }

  /**
   * Diese Methode fügt einen Schlüssel in den Haufen ein.
   *
   * @param key der einzufügende Schlüssel
   */
  public void insert(int key) {
    List<BinomialTreeNode> node = new LinkedList<>();
    node.add(new BinomialTreeNode(key));
    merge(node);
  }

  private void merge(List<BinomialTreeNode> nodes) {
    if (nodes.isEmpty()) {
      return;
    }
    int maxRank = 0;
    List<BinomialTreeNode> newNodes = new ArrayList<>();
    if (trees.size() > 0 && trees.get(trees.size() - 1).rank() > maxRank) {
      maxRank = trees.get(trees.size() - 1).rank();
    }
    if (nodes.size() > 0 && nodes.get(nodes.size() - 1).rank() > maxRank) {
      maxRank = nodes.get(nodes.size() - 1).rank();
    }
    int i = 0, j = 0;
    BinomialTreeNode c = null;
    for (int rank = 0; rank <= maxRank; rank++) {
      BinomialTreeNode tree = null, node = null;
      // get trees of rank from the two sources
      if (i < trees.size() && trees.get(i).rank() == rank) {
        tree = trees.get(i++);
      }
      if (j < nodes.size() && nodes.get(j).rank() == rank) {
        node = nodes.get(j++);
      }
      BinomialTreeNode[] result = add(tree, node, c);
      // add result to the list of binomial trees
      if (result[0] != null) {
        newNodes.add(result[0]);
      }
      // update the carry
      c = result[1];
    }
    if (c != null) {
      newNodes.add(c);
    }
    trees = newNodes;
  }

  /**
   * @return result of the addition [0] is res, [1] is carry
   */
  private BinomialTreeNode[] add(BinomialTreeNode a, BinomialTreeNode b, BinomialTreeNode c) {
    BinomialTreeNode[] result = new BinomialTreeNode[2];
    if (c == null) {
      if (a != null && b != null) {
        result[1] = BinomialTreeNode.merge(a, b);
      } else {
        result[0] = (a == null) ? b : a;
      }
      return result;
    } else {
      if (a != null && b != null) {
        result[1] = BinomialTreeNode.merge(a, b);
        result[0] = c;
      } else if (a != null || b != null) {
        BinomialTreeNode notNull = (a == null) ? b : a;
        result[1] = BinomialTreeNode.merge(notNull, c);
      } else {
        result[0] = c;
      }
      return result;
    }
  }

  /**
   * Diese Methode entfernt das minimale Element aus dem binomialen Haufen und gibt es zurück.
   *
   * @return das minimale Element
   */
  public int deleteMin() {
    BinomialTreeNode min = findMin();
    BinomialTreeNode[] nodes = min.deleteMin();
    trees.remove(min);
    merge(Arrays.asList(nodes));
    return min.min();
  }
}
