
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
  public void testInsert2() {
    ABTree tree = new ABTree(2, 4);
    Set<Integer> nums = new HashSet<>();
    Random r = new Random();
    for (int i = 0; i < 100; i++) {
      nums.add(r.nextInt(1000));
    }
    for (int n : nums) {
      tree.insert(n);
      System.out.println(tree.toString());
      assertTrue(tree.validAB(), tree.toString());
    }
  }

  @Test
  public void testInsert3() {
    for (int i = 0; i < 100; i++) {
      testInsert2();
    }
  }



}
