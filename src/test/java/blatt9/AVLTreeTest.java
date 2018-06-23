import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class AVLTreeTest {

  // not quite the same as this avl tree does not allow for deletions
  @Test
  public void sheetTest() {
    AVLTree tree = new AVLTree();
    tree.insert(10);

    for (int i : new int[]{5, 17, 3, 1, 4, 8, 2, 7, 6, 9}) {
      tree.insert(i);
      System.out.println();
      System.out.println(tree.dot());
    }
    assertTrue(tree.validAVL());
  }

  @Test
  public void randomTest() {
    for (int k = 0; k < 100; k++) {
      AVLTree tree = new AVLTree();
      tree.insert(10);
      Random r = new Random();
      Set<Integer> numbers = new HashSet<>();
      for (int i = 0; i < 100; i++) {
        int num = r.nextInt(1000);
        if (num != 10) {
          numbers.add(num);
        }
      }

      for (int i : numbers) {
        tree.insert(i);
        System.out.println("Inserted: " + i);
        System.out.println(tree.dot());
        assertTrue(tree.validAVL(), tree.dot());
      }
    }
  }
}
