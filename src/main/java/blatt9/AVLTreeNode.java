
/**
 * Diese Klasse implementiert einen Knoten in einem AVL-Baum.
 */
public class AVLTreeNode {

  /**
   * Diese Variable enthält den Schlüssel, den der Knoten speichert.
   */
  private int key;

  /**
   * Diese Variable speichert die Balancierung des Knotens - wie in der Vorlesung erklärt - ab. Ein
   * Wert von -1 bedeutet, dass der linke Teilbaum um eins tiefer ist als der rechte Teilbaum. Ein
   * Wert von 0 bedeutet, dass die beiden Teilbäume gleich hoch sind. Ein Wert von 1 bedeutet, dass
   * der rechte Teilbaum tiefer ist.
   */
  private int balance = 0;

  private AVLTreeNode left = null;
  private AVLTreeNode right = null;

  public AVLTreeNode(int key) {
    this.key = key;
  }

  /**
   * Diese Methode ermittelt die Höhe des Teilbaums unter diesem Knoten.
   *
   * @return die ermittelte Höhe
   */
  public int height() {
    if (left == null && right == null) {
      return 1;
    }
    if (left == null) {
      return right.height() + 1;
    }
    if (right == null) {
      return left.height() + 1;
    }
    return Math.max(left.height(), right.height()) + 1;
  }

  public boolean validAVL() {
    if (left == null && right == null) {
      return true;
    }
    if (left == null) {
      int height = right.height();
      return height <= 1 && right.key > key && balance == height && right.validAVL();
    }
    if (right == null) {
      int height = left.height();
      return height <= 1 && left.key <= key && balance == -height && left.validAVL();
    }
    int leftHeight = left.height(), rightHeight = right.height();
    if (Math.abs(leftHeight - rightHeight) > 1) {
      return false;
    }
    if (left.key > key && right.key <= key) {
      return false;
    }
    if (rightHeight - leftHeight != balance) {
      return false;
    }
    return left.validAVL() && right.validAVL();
  }


  /**
   * Diese Methode sucht einen Schlüssel im Baum.
   *
   * @param key der zu suchende Schlüssel
   * @return 'true', falls der Schlüssel gefunden wurde, 'false' sonst
   */
  public boolean find(int key) {
    if (key == this.key) {
      return true;
    }
    if (key < this.key && left != null) {
      return left.find(key);
    }
    if (key > this.key && right != null) {
      return right.find(key);
    }
    return false;
  }

  public AVLTreeNode insert(int key) {
    if (key >= this.key) {
      right = insertWithBalanceUpdate(right, key, 1);
    } else {
      left = insertWithBalanceUpdate(left, key, -1);
    }
    AVLTreeNode node = this;
    if (balance == -2) {
      if (-1 <= left.balance && left.balance <= 0) {
        node = rotateRight(this);
      } else {
        node = rotateLeftRight(this);
      }
    } else if (balance == 2) {
      if (0 <= right.balance && right.balance <= 1) {
        node = rotateLeft(this);
      } else {
        node = rotateRightLeft(this);
      }
    }
    return node;
  }

  private AVLTreeNode insertWithBalanceUpdate(AVLTreeNode node, int key, int balance) {
    if (node == null) {
      this.balance += balance;
      return new AVLTreeNode(key);
    } else {
      int b = node.balance;
      AVLTreeNode n = node.insert(key);
      if (b == 0 && node.balance != 0) {
        this.balance += balance;
      }
      return n;
    }
  }

  private AVLTreeNode rotateLeft(AVLTreeNode node) {
    AVLTreeNode right = node.right;
    AVLTreeNode left = right.left;
    right.left = node;
    node.right = left;
    // rebalance
    if (right.balance == 0) {
      node.balance = 1;
      right.balance = -1;
    } else {
      node.balance = 0;
      right.balance = 0;
    }
    return right;
  }

  private AVLTreeNode rotateLeftRight(AVLTreeNode node) {
    AVLTreeNode left = node.left;
    AVLTreeNode newRoot = left.right;
    node.left = newRoot.right;
    left.right = newRoot.left;
    newRoot.left = left;
    newRoot.right = node;
    // rebalance
    if (newRoot.balance == 0 || newRoot.balance == 1) {
      if (newRoot.balance == 1) {
        left.balance = -1;
      } else {
        left.balance = 0;
      }
      node.balance = 0;
    } else {
      node.balance = 1;
      left.balance = 0;
    }
    newRoot.balance = 0;
    return newRoot;
  }

  public AVLTreeNode rotateRight(AVLTreeNode node) {
    AVLTreeNode left = node.left;
    AVLTreeNode right = left.right;
    left.right = node;
    node.left = right;
    // rebalance
    if (left.balance == 0) {
      node.balance = -1;
      left.balance = 1;
    } else {
      node.balance = 0;
      left.balance = 0;
    }
    return left;
  }

  public AVLTreeNode rotateRightLeft(AVLTreeNode node) {
    AVLTreeNode right = node.right;
    AVLTreeNode newRoot = right.left;
    node.right = newRoot.left;
    right.left = newRoot.right;
    newRoot.right = right;
    newRoot.left = node;
    // rebalance
    if (newRoot.balance == -1 || newRoot.balance == 0) {
      if (newRoot.balance == -1) {
        right.balance = 1;
      } else {
        right.balance = 0;
      }
      node.balance = 0;
    } else {
      node.balance = -1;
      right.balance = 0;
    }
    newRoot.balance = 0;
    return newRoot;
  }


  /**
   * Diese Methode wandelt den Baum in das Graphviz-Format um.
   *
   * @param sb der StringBuilder für die Ausgabe
   */
  public void dot(StringBuilder sb) {
    dotNode(sb, 0);
  }

  private int dotNode(StringBuilder sb, int idx) {
    sb.append(String.format("\t%d [label=\"%d, b=%d\"];\n", idx, key, balance));
    int next = idx + 1;
    if (left != null) {
      next = left.dotLink(sb, idx, next, "l");
    }
    if (right != null) {
      next = right.dotLink(sb, idx, next, "r");
    }
    return next;
  }

  private int dotLink(StringBuilder sb, int idx, int next, String label) {
    sb.append(String.format("\t%d -> %d [label=\"%s\"];\n", idx, next, label));
    return dotNode(sb, next);
  }
}
