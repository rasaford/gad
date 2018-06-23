package blatt7;

import java.util.ArrayList;
import java.util.LinkedList;

public class RadixSort {

  private int key(Integer element, int digit) throws IllegalArgumentException {
    if (element < 0) {
      throw new IllegalArgumentException();
    }
    if (element == 0) {
      return 0;
    }
    LinkedList<Integer> digits = new LinkedList<>();
    Integer e = element;
    while (e > 0) {
      digits.addLast(e % 10);
      e /= 10;
    }
    int totalDigits = (int) Math.log10(element);
    return (digit > totalDigits && digit != 0) ? 0 : digits.get(digit);
  }

  private void concatenate(ArrayList<Integer>[] buckets, Integer[] elements) {
    int j = 0, k = 0;
    for (int i = 0; i < elements.length; i++) {
      while (j < buckets.length && buckets[j].isEmpty()) {
        j++;
      }
      elements[i] = buckets[j].get(k++);
      if (k == buckets[j].size()) {
        k = 0;
        j++;
      }
    }
  }

  private void kSort(Integer[] elements, int digit) {
    ArrayList<Integer>[] buckets = new ArrayList[10];
    for (int i = 0; i < buckets.length; i++) {
      buckets[i] = new ArrayList<>();
    }
    for (Integer i : elements) {
      int key = key(i, digit);
      buckets[key].add(i);
    }
    concatenate(buckets, elements);
  }


  public void sort(Integer[] elements) {
    for (int i = 0; i < Math.log10(Integer.MAX_VALUE); i++) {
      kSort(elements, i);
    }
  }
}
