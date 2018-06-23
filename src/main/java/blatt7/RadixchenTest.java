package blatt7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RadixchenTest {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Integer> values = new ArrayList<>();
    while (scanner.hasNextInt()) {
      values.add(scanner.nextInt());
    }
    RadixSort r = new RadixSort();
    Integer[] elements = values.toArray(new Integer[0]);
    r.sort(elements);
    System.err.println(values);
    for (int i = 0; i < elements.length; i++) {
      System.out.print(i == elements.length - 1 ? elements[i] : elements[i] + " ");
    }
    System.out.println();
  }

//  @Test
//  public void testKey() {
//    blatt7.RadixSort r = new blatt7.RadixSort();
//    assertEquals(1, r.key(1234, 0));
//    assertEquals(2, r.key(1234, 1));
//    assertEquals(3, r.key(1234, 2));
//    assertEquals(4, r.key(1234, 3));
//  }
//
//  @Test
//  public void testConcat() {
//    ArrayList<Integer>[] buckets = new ArrayList[]{
//        new ArrayList<>(Arrays.asList(new Integer[]{11, 12, 13, 15})),
//        new ArrayList<>(Arrays.asList(new Integer[]{22, 25})),
//    };
//    Integer[] sorted = new Integer[]{11, 12, 13, 15, 22, 25};
//    Integer[] res = new Integer[6];
//    blatt7.RadixSort r = new blatt7.RadixSort();
//    r.concatenate(buckets, res);
//    assertArrayEquals(sorted, res, String.format("arrays are not the same %s != %s",
//        Arrays.toString(sorted),
//        Arrays.toString(res)));
//  }

//  @Test
//  public void testSort() {
//    Integer[] numbers = new Integer[1<< 15];
//    Random random = new Random();
//    for (int i = 0; i < numbers.length; i++) {
//      numbers[i] = random.nextInt(Integer.MAX_VALUE);
//    }
//    Integer[] radixSorted = new Integer[numbers.length];
//    System.arraycopy(numbers, 0, radixSorted, 0, numbers.length);
//    blatt7.RadixSort r = new blatt7.RadixSort();
//    r.sort(radixSorted);
//    Arrays.sort(numbers);
//    Assertions.assertArrayEquals(numbers, radixSorted, String.format("arrays are not equal %s != %s",
//        Arrays.toString(numbers), Arrays.toString(radixSorted)));
//  }
}
