package blatt6;

public class Quicksort implements SortingBase {

  public void sort(int[] numbers) {
    sort(numbers, 0, numbers.length - 1);
  }

  private void sort(int[] numbers, final int low, final int high) {
    if (low >= high) {
      return;
    }
    int pivot = numbers[high];
    int left = low - 1, right = high;
    do {
      do {
        left++;
      } while (numbers[left] < pivot);
      do {
        right--;
      } while (right >= low && numbers[right] > pivot);
      if (left < right) {
        swap(numbers, left, right);
      }
    } while (left < right);
    swap(numbers, left, high);
    sort(numbers, low, left - 1);
    sort(numbers, left + 1, high);
  }

  private void swap(int[] numbers, int low, int high) {
    int temp = numbers[low];
    numbers[low] = numbers[high];
    numbers[high] = temp;
  }

//  private int partition(int[] numbers, int low, int high) {
//    int pivot = numbers[high],
//        i = low - 1;
//    for (int j = low; j < high; j++) {
//      if (numbers[j] < pivot) {
//        i++;
//        int temp = numbers[i];
//        numbers[i] = numbers[j];
//        numbers[j] = temp;
//      }
//    }
//    int temp = numbers[i + 1];
//    numbers[i + 1] = numbers[high];
//    numbers[high] = temp;
//    return i + 1;
//  }


  public String getName() {
    return "Quicksort";
  }
}
