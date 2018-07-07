package blatt10;

import java.util.ArrayList;

/**
 * Diese Klasse implementiert einen (a,b)-Baum.
 */
public class ABTree {

  /**
   * Diese Variable speichert die untere Schranke des Knotengrades.
   */
  private int a;

  /**
   * Diese Variable speichert die obere Schranke des Knotengrades.
   */
  private int b;

  /**
   * Diese Klasse repräsentiert einen Knoten des Baumes. Es kann sich dabei um einen inneren Knoten
   * oder ein Blatt handeln.
   */
  public abstract class ABTreeNode {

    /**
     * Diese Methode fügt einen Schlüssel in den Baum ein.
     *
     * @param key der Schlüssel, der eingefügt wird
     */
    public abstract void insert(int key);

    /**
     * Diese Methode ermittelt, ob aus dem entsprechenden Knoten gestohlen werden kann oder nicht.
     *
     * @return 'true' falls gestohlen werden kann, 'false' sonst
     */
    public abstract boolean canSteal();

    /**
     * Diese Methode sucht den Schlüssel 'key' im Teilbaum.
     *
     * @param key der Schlüssel, der gesucht wird
     * @return 'true' falls der Schlüssel vorhanden ist, 'false' sonst
     */
    public abstract boolean find(int key);

    /**
     * Diese Methode entfernt den Schlüssel 'key' im Teilbaum, falls es ihn gibt.
     *
     * @param key der Schlüssel, der entfernt werden soll
     * @return 'true' falls der Schlüssel gefunden und entfernt wurde, 'false' sonst
     */
    public abstract boolean remove(int key);

    /**
     * Diese Methode ermittelt die Höhe des Teilbaums unter diesem Knoten.
     *
     * @return die ermittelte Höhe
     */
    public abstract int height();

    /**
     * Diese Methode ermittelt das Minimum im jeweiligen Teilbaum.
     *
     * @return das Minimum
     */
    public abstract Integer min();

    /**
     * Diese Methode ermittelt das Maximum im jeweiligen Teilbaum.
     *
     * @return das Maximum
     */
    public abstract Integer max();

    /**
     * Diese Methode ist zum Debuggen gedacht und prüft, ob es sich um einen validen (a,b)-Baum
     * handelt.
     *
     * @return 'true' falls der Baum ein valider (a,b)-Baum ist, 'false' sonst
     */
    public abstract boolean validAB(boolean root);

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der nächste freie Index für das Graphviz-Format
     */
    public abstract int dot(StringBuilder sb, int from);
  }

  /**
   * Diese Klasse repräsentiert einen inneren Knoten des Baumes.
   */
  private class ABTreeInnerNode extends ABTreeNode {

    private ArrayList<Integer> keys;
    private ArrayList<ABTreeNode> children;

    public ABTreeInnerNode(ArrayList<Integer> keys, ArrayList<ABTreeNode> children) {
      this.keys = keys;
      this.children = children;
    }

    public ABTreeInnerNode(int key, ABTreeNode left, ABTreeNode right) {
      keys = new ArrayList<>();
      children = new ArrayList<>();
      keys.add(key);
      children.add(left);
      children.add(right);
    }

    public ABTreeInnerNode(int key) {
      this(key, new ABTreeLeaf(), new ABTreeLeaf());
    }

    @Override
    public void insert(int key) {
      int index = 0;
      for (int k : keys) {
        if (k < key) {
          index++;
        } else {
          break;
        }
      }
      if (children.get(index) instanceof ABTreeLeaf) {
        // if I just have leaf nodes as children
        children.add(index, new ABTreeLeaf());
        keys.add(index, key);
      } else {
        ABTreeInnerNode child = (ABTreeInnerNode) children.get(index);
        child.insert(key);
        if (child.validAB(false)) {
          return;
        }
        // re-balance necessary
        ArrayList<Integer> leftKeys = new ArrayList<>();
        ArrayList<ABTreeNode> leftChildren = new ArrayList<>();
        ArrayList<Integer> rightKeys = new ArrayList<>();
        ArrayList<ABTreeNode> rightChildren = new ArrayList<>();

        int mid = b / 2;
        for (int i = 0; i < child.keys.size(); i++) {
          if (i < mid - 1) {
            leftChildren.add(child.children.get(i));
            leftKeys.add(child.keys.get(i));
          } else if (i == mid - 1) {
            leftChildren.add(child.children.get(i));
          } else if (i >= mid) {
            rightChildren.add(child.children.get(i));
            rightKeys.add(child.keys.get(i));
          }
        }
        rightChildren.add(child.children.get(child.children.size() - 1));

        ABTreeInnerNode left = new ABTreeInnerNode(leftKeys, leftChildren);
        ABTreeInnerNode right = new ABTreeInnerNode(rightKeys, rightChildren);

        keys.add(index, child.keys.get(mid - 1));
        children.set(index, left);
        children.add(index + 1, right);
      }
    }


    @Override
    public boolean canSteal() {
      return children.size() > a;
    }

    @Override
    public boolean find(int key) {
      int index = 0;
      for (int k : keys) {
        if (k < key) {
          index++;
        } else if (key == k) {
          return true;
        } else {
          break;
        }
      }
      return children.get(index).find(key);
    }

    public boolean remove(int key) {
      // find the node with the key in it
      int index = 0;
      for (int k : keys) {
        if (k < key) {
          index++;
        } else if (k == key) {
          break;
        }
      }

      if (index < keys.size() && keys.get(index) == key) {
        if (children.get(index) instanceof ABTreeLeaf) {
          keys.remove(index);
          children.remove(index);
        } else {
          keys.set(index, ((ABTreeInnerNode) children.get(index)).popMaxKey());
          balance(index);
        }
        return true;
      } else {
        ABTreeInnerNode node = (ABTreeInnerNode) children.get(index);
        boolean r = node.remove(key);
        if (node.validAB(false)) {
          return true;
        }
        balance(index);
        return r;
      }
    }

    private void balance(int index) {
      ABTreeInnerNode node = (ABTreeInnerNode) children.get(index);
      // was this call justified?
      if (node.children.size() >= a) {
        return;
      }
      ABTreeInnerNode left = (ABTreeInnerNode) children.get(
          Math.max(index - 1, 0));
      ABTreeInnerNode right = (ABTreeInnerNode) children.get(
          Math.min(index + 1, children.size() - 1));

      if (left.canSteal()) {
        // move max of left Neighbour to this node
        int maxChild = left.children.size() - 1;
        int maxKey = left.keys.size() - 1;

        node.children.add(0, left.children.get(maxChild));
        left.children.remove(maxChild);

        node.keys.add(0, keys.get(index - 1));
        keys.set(index - 1, left.keys.get(maxKey));
        left.keys.remove(maxKey);
      } else if (right.canSteal()) {
        // move min of right Neighbour to this node

        node.children.add(right.children.get(0));
        right.children.remove(0);

        node.keys.add(keys.get(index));
        keys.set(index, right.keys.get(0));
        right.keys.remove(0);
      } else if (!node.equals(right)) {
        mergeSubtrees(index);
      } else {
//        System.err.println("no right child!!!");
        mergeSubtrees(index - 1);
      }
    }

    private int popMaxKey() {
      int maxChild = children.size() - 1;
      int maxKey = keys.size() - 1;
      if (children.get(maxChild) instanceof ABTreeLeaf) {
        children.remove(maxChild);
        return keys.remove(maxKey);
      } else {
        int k = ((ABTreeInnerNode) children.get(maxChild)).popMaxKey();
        balance(maxChild);
        return k;
      }
    }

    private void mergeSubtrees(int index) {
      ABTreeInnerNode left = (ABTreeInnerNode) children.get(index);
      ABTreeInnerNode right = (ABTreeInnerNode) children.get(index + 1);

      ArrayList<Integer> newKeys = new ArrayList<>();
      for (int key : left.keys) {
        newKeys.add(key);
      }
      newKeys.add(keys.get(index));
      for (int key : right.keys) {
        newKeys.add(key);
      }

      ArrayList<ABTreeNode> newChildren = new ArrayList<>();
      for (ABTreeNode child : left.children) {
        newChildren.add(child);
      }
      for (ABTreeNode child : right.children) {
        newChildren.add(child);
      }

      ABTreeInnerNode newNode = new ABTreeInnerNode(newKeys, newChildren);
      children.set(index, newNode);
      children.remove(index + 1);
      keys.remove(index);
    }

    @Override
    public int height() {
      return children.get(0).height() + 1;
    }

    @Override
    public Integer min() {
      Integer res = children.get(0).min();
      return res == null ? keys.get(0) : res;
    }

    @Override
    public Integer max() {
      Integer res = children.get(children.size() - 1).max();
      return res == null ? keys.get(keys.size() - 1) : res;
    }

    @Override
    public boolean validAB(boolean root) {
      // height is the same for all subtrees
      int height = children.get(0).height();
      for (ABTreeNode node : children) {
        if (height != node.height()) {
          return false;
        }
      }
      // every node has to have a <= children.size() <= b nodes
      int tmpA = root ? 2 : a, tmpB = b;
      if (children.size() < tmpA || children.size() > tmpB ||
          keys.size() + 1 < tmpA || keys.size() + 1 > tmpB) {
        return false;
      }
      // key have to be sorted
      int prev = keys.get(0);
      for (int key : keys) {
        if (key < prev) {
          return false;
        }
        prev = key;
      }
      for (int i = 0; i < keys.size(); i++) {
        ABTreeNode left = children.get(i);
        ABTreeNode right = children.get(i + 1);
        Integer key = keys.get(i);
        if (left.max() != null && left.max() > key ||
            right.max() != null && right.min() < key) {
          return false;
        }
      }
      return children
          .stream()
          .allMatch(tree -> tree.validAB(false));
    }

    @Override
    public int dot(StringBuilder sb, int from) {
      int mine = from++;
      sb.append(String.format("\tstruct%s [label=\"", mine));
      for (int i = 0; i < 2 * keys.size() + 1; i++) {
        if (i > 0) {
          sb.append("|");
        }
        sb.append(String.format("<f%d> ", i));
        if (i % 2 == 1) {
          sb.append(keys.get(i / 2));
        }
      }
      sb.append("\"];\n");
      for (int i = 0; i < children.size(); i++) {
        int field = 2 * i;
        sb.append(String.format("\tstruct%d:<f%d> -> struct%d;\n", mine, field, from));
        from = children.get(i).dot(sb, from);
      }
      return from;
    }
  }

  /**
   * Diese Klasse repräsentiert ein Blatt des Baumes.
   */
  private class ABTreeLeaf extends ABTreeNode {

    @Override
    public void insert(int key) {
      throw new RuntimeException("Cannot insert on a leaf node");
    }

    @Override
    public boolean canSteal() {
      return false;
    }

    @Override
    public boolean find(int key) {
      return false;
    }

    @Override
    public boolean remove(int key) {
      return false;
    }

    @Override
    public int height() {
      return 0;
    }

    @Override
    public Integer min() {
      return null;
    }

    @Override
    public Integer max() {
      return null;
    }

    @Override
    public boolean validAB(boolean root) {
      return true;
    }

    @Override
    public int dot(StringBuilder sb, int from) {
      sb.append(String.format("\tstruct%d [label=leaf, shape=ellipse];\n", from));
      return from + 1;
    }
  }

  public ABTree(int a, int b) {
    if (a < 2) {
      throw new RuntimeException("a has to be greater or equal to 2 is " + a);
    }
    if (b < 2 * a - 1) {
      throw new RuntimeException("b has to be greater or equal to 2 * a - 1, is " + b);
    }
    this.a = a;
    this.b = b;
  }

  /**
   * Diese Objektvariable speichert die Wurzel des Baumes.
   */
  private ABTreeInnerNode root = null;

  public boolean validAB() {
    return root == null || root.validAB(true);
  }

  /**
   * Diese Methode ermittelt die Höhe des Baumes.
   *
   * @return die ermittelte Höhe
   */
  public int height() {
    return root == null ? 0 : root.height();
  }

  /**
   * Diese Methode sucht einen Schlüssel im (a,b)-Baum.
   *
   * @param key der Schlüssel, der gesucht werden soll
   * @return 'true', falls der Schlüssel gefunden wurde, 'false' sonst
   */
  public boolean find(int key) {
    return root != null && root.find(key);
  }

  /**
   * Diese Methode fügt einen neuen Schlüssel in den (a,b)-Baum ein.
   *
   * @param key der einzufügende Schlüssel
   */
  public void insert(int key) {
    if (root == null) {
      root = new ABTreeInnerNode(key);
    } else {
      root.insert(key);
      if (root.validAB(true)) {
        return;
      }
      // re-balance necessary
      ArrayList<Integer> leftKeys = new ArrayList<>();
      ArrayList<ABTreeNode> leftChildren = new ArrayList<>();
      ArrayList<Integer> rightKeys = new ArrayList<>();
      ArrayList<ABTreeNode> rightChildren = new ArrayList<>();

      int mid = b / 2;
      for (int i = 0; i < root.keys.size(); i++) {
        if (i < mid - 1) {
          leftChildren.add(root.children.get(i));
          leftKeys.add(root.keys.get(i));
        } else if (i == mid - 1) {
          leftChildren.add(root.children.get(i));
        } else if (i >= mid) {
          rightChildren.add(root.children.get(i));
          rightKeys.add(root.keys.get(i));
        }
      }
      rightChildren.add(root.children.get(root.children.size() - 1));

      ABTreeInnerNode left = new ABTreeInnerNode(leftKeys, leftChildren);
      ABTreeInnerNode right = new ABTreeInnerNode(rightKeys, rightChildren);
      root = new ABTreeInnerNode(root.keys.get(mid - 1), left, right);
    }
  }

  /**
   * Diese Methode löscht einen Schlüssel aus dem (a,b)-Baum.
   *
   * @param key der zu löschende Schlüssel
   * @return 'true' falls der Schlüssel gefunden und gelöscht wurde, 'false' sonst
   */
  public boolean remove(int key) {
    if (!find(key)) {
      return false;
    }
    boolean res = root.remove(key);
    if (root.children.size() < 2) {
      if (root.children.get(0) instanceof ABTreeLeaf) {
        root = null;
      } else {
        root = (ABTreeInnerNode) root.children.get(0);
      }
    }
    return res;
  }

  /**
   * Diese Methode wandelt den Baum in das Graphviz-Format um.
   *
   * @return der Baum im Graphiz-Format
   */
  String dot() {
    StringBuilder sb = new StringBuilder();
    sb.append("digraph {\n");
    sb.append("\tnode [shape=record];\n");
    if (root != null) {
      root.dot(sb, 0);
    }
    sb.append("}");
    return sb.toString();
  }

  @Override
  public String toString() {
    return dot();
  }
}
