package blatt6;

public class Mergesort implements SortingBase {

  public void sort(int[] numbers) {
    sort(numbers, new int[numbers.length], 0, numbers.length - 1);
  }

  private void sort(int[] numbers, int[] work, int low, int high) {
    if (high == low) {
      return;
    }
    int mid = (high + low) / 2;
    sort(numbers, work, low, mid);
    sort(numbers, work, mid + 1, high);

    int left = low, right = mid + 1;
    for (int i = 0; i <= high - low; i++) {
      if (left > mid) {
        // left empty
        work[i] = numbers[right];
        right++;
      } else if (right > high || numbers[left] <= numbers[right]) {
        // right empty
        work[i] = numbers[left];
        left++;
      } else {
        work[i] = numbers[right];
        right++;
      }
    }
    for (int i = 0; i <= high - low; i++) {
      numbers[low + i] = work[i];
    }
  }


  public String getName() {
    return "Mergesort";
  }
}
