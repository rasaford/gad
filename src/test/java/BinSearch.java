import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class BinSearch {

  @Test
  public void searchExample1() {
    int[] sortedData = {-10, 33, 50, 99, 123, 4242};
    int res = BinSea.search(sortedData, 80, false);
    assertEquals(2, res, "Result should be 2");

    res = BinSea.search(sortedData, 80, true);
    assertEquals(3, res, "Result should be 3");
  }


  @Test
  public void binSearchIntervalExample() {
    int[] sortedData = {-10, 33, 50, 99, 123, 4242};
    Interval i = Interval.fromArrayIndices(80, 700);
    Interval expected = Interval.fromArrayIndices(3, 4);
    Interval res = BinSea.search(sortedData, i);
    checkInterval(res, expected, sortedData);
  }

  @Test
  public void binSearchIntervalRandom() {
    for (int runs = 0; runs < 1000; runs++) {
      int size = 10;
      int[] randomNumbers = new int[size];
      Random r = new Random();
      for (int i = 0; i < randomNumbers.length; i++) {
        randomNumbers[i] = r.nextInt(Integer.MAX_VALUE);
      }
      Arrays.sort(randomNumbers);
      int low = r.nextInt(size), high = r.nextInt(size);
      if (low > high) {
        int temp = low;
        low = high;
        high = temp;
      }
      Interval res = BinSea.search(randomNumbers,
          Interval.fromArrayIndices(randomNumbers[low], randomNumbers[high])
      );
      Interval expected = Interval.fromArrayIndices(low, high);
      checkInterval(res, expected, randomNumbers);
    }
  }

  private void checkInterval(Interval res, Interval expected, int[] numbers) {
    assertTrue(expected.getFrom() == res.getFrom() &&
            expected.getTo() == res.getTo(),
        expected + " should be the same as " + res
            + "in " + Arrays.toString(numbers));
  }
}
