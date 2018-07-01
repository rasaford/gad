
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class ABTreeTest {

  @Test
  public void testInsert() {
    ABTree tree = new ABTree(2, 4);
    for (int i = 0; i < 50; i++) {
      tree.insert(i);
      System.out.println(tree.toString());
      assertTrue(tree.validAB(), tree.toString());
    }
  }


  @Test
  public void testABTree() {
    for (int j = 0; j < 1000; j++) {
      ABTree tree = new ABTree(2, 4);
      Set<Integer> nums = new HashSet<>();
      Random r = new Random();
      for (int i = 0; i < 100; i++) {
        nums.add(r.nextInt(1000));
      }

      for (int n : nums) {
        tree.insert(n);
//        System.out.println(tree.toString());
        assertTrue(tree.validAB(), "error on insert " + n);
      }
      for (int n : nums) {
        assertTrue(tree.find(n), "could not find " + n);
      }
      for (int n : nums) {
        tree.remove(n);
//        System.out.println(tree.toString());
        assertTrue(tree.validAB(), "error on remove " + n);
      }
    }
  }

  @Test
  public void sheetExample() {
    ABTree tree = new ABTree(2, 4);
    for (int i : new int[]{23, 38, 6, 12, 37, 61, 15}) {
      tree.insert(i);
      System.out.println("\n\ninsert " + i);
      System.out.println(tree.toString());
      assertTrue(tree.validAB(), "error on insert " + i);
    }

    for (int i : new int[]{12, 6, 37, 38, 23, 61}) {
      tree.remove(i);
      System.out.println("remove " + i);
      System.out.println(tree.toString());
      assertTrue(tree.validAB(), "error on remove " + i);
    }
  }
}
