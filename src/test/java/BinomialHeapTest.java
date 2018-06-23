import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import blatt8.BinomialHeap;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class BinomialHeapTest {


  @Test
  public void testHeap() {
    int[] numbers = new int[1<<15];
    Random r = new Random();
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = r.nextInt();
    }

    BinomialHeap heap = new BinomialHeap();
    for (int num : numbers) {
      heap.insert(num);
    }
    int[] res = new int[numbers.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = heap.deleteMin();
    }
    Arrays.sort(numbers);
    assertArrayEquals(numbers, res, String.format("arrays were not equal %s != %s",
        Arrays.toString(numbers), Arrays.toString(res)));
  }

}
