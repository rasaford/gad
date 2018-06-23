package blatt6;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Program {

  public static void main(String[] args) {
    // Anzahl der zu sortierenden Elemente
    // int n = 10;
    // int n = 100;
    // int n = 1000;
    // int n = 10000;
    // int n = 100000;
    // int n = 1000000;
    // int n = 10 000 000;
    for (int n = 10; n < 1e5; n *= 10) {
      Random r = new Random();
      int[] numbers = new int[n];
      LinkedList<SortingBase> implementations = new LinkedList<>();
      implementations.add(new Mergesort());
      implementations.add(new Quicksort());

      for (int type = 0; type < 3; type++) {
        switch (type) {
          case 0:
            System.out.println("random numbers");
            for (int i = 0; i < n; i++) {
              numbers[i] = r.nextInt();
            }
            break;
          case 1:
            System.out.println("sorted random numbers");
            for (int i = 0; i < n; i++) {
              numbers[i] = r.nextInt();
            }
            Arrays.sort(numbers);
            break;
          case 2:
            System.out.println("inverse sorted random numbers");
            for (int i = 0; i < n; i++) {
              numbers[i] = r.nextInt();
            }
            Arrays.sort(numbers);
            for (int i = 0; i < numbers.length / 2; i++) {
              int tmp = numbers[i];
              numbers[i] = numbers[numbers.length - i - 1];
              numbers[numbers.length - i - 1] = tmp;
            }
            break;
        }
        printArray("vorher: ", numbers);
        for (SortingBase sorter : implementations) {
          int[] numbersImpl = Arrays.copyOf(numbers, numbers.length);
          long startTime = System.currentTimeMillis();
          sorter.sort(numbersImpl);
          long estimatedTime = System.currentTimeMillis() - startTime;

          printArray(sorter.getName() + ": ", numbersImpl);
          if (estimatedTime > 0) {
            System.out.println("Zeit: " + estimatedTime + "ms");
          } else {
            System.out.println("Zeit: <1ms");
          }
          checkSorting(numbersImpl);
          System.out.println();
        }
      }
    }
  }

  public static void printArray(String prefix, int[] numbers) {
    int displayCount = numbers.length;
    String ext = "";
    if (numbers.length > 15) {
      displayCount = 15;
      ext = "...";
    }
    System.out.print(prefix);
    for (int i = 0; i < displayCount - 1; i++) {
      System.out.print(numbers[i] + ", ");
    }
    System.out.println(numbers[displayCount - 1] + ext);
  }

  public static void checkSorting(int[] numbers) {
    for (int i = 1; i < numbers.length; i++) {
      if (numbers[i - 1] > numbers[i]) {
        System.out.println("! FEHLER: " + numbers[i - 1] + " > " + numbers[i]);
      }
    }
  }
}
