/**
 * Die Klasse BinSea stellt Methoden bereit, in sortierten Feldern binär nach Wertebereichen zu
 * suchen.
 */
public class BinSea {

  /*
   * Diese Methode sucht nach einem Wert in einem einem sortierten Feld. Sie
   * soll dabei dazu verwendet werden können, den ersten bzw. letzten Index in
   * einem Intervall zu finden. Wird kein passender Index gefunden, wird
   * -1 zurückgegeben.
   *
   * Beispiel:
   *
   *             0 <-----------------------> 5
   * sortedData: [-10, 33, 50, 99, 123, 4242 ]
   * value: 80             ^   ^
   *                       |   |
   *                       |   `- Ergebnis (3) für ersten Index im Intervall (lower == true),
   *                       |      da 99 als erster Wert im Feld größer oder gleich 80 ist
   *                       |
   *                       `- Ergebnis (2) für letzten Index im Intervall (lower == false),
   *                          da 50 als letzter Wert kleiner oder gleich 80 ist
   */

  /**
   * @param sortedData das sortierte Feld, in dem gesucht wird
   * @param value der Wert der Intervallbegrenzung, nach dem gesucht wird
   * @param lower true für untere Intervallbegrenzung, false für obere Intervallbegrenzung
   * @return der passende Index, -1 wenn dieser nicht gefunden werden kann
   */
  public static int search(int[] sortedData, int value, boolean lower) {
    int index = binSearch(sortedData, value, 0, sortedData.length - 1);
    if (sortedData[index] != value && lower) {
      return index == sortedData.length - 1 ? -1 : index + 1;
    }
    return index;
  }

  private static int binSearch(int[] data, int value, int low, int high) {
    if (low >= high) {
      return low;
    }
    if (low + 1 == high) {
      return (data[high] == value) ? high : low;
    }
    int mid = (high - low) / 2;
    if (data[low + mid] == value) {
      return low + mid;
    } else if (value < data[low + mid]) {
      return binSearch(data, value, low, low + mid);
    } else {
      return binSearch(data, value, low + mid, high);
    }
  }

  /*
   * Diese Methode sucht ein Intervall von Indices eines sortierten Feldes, deren Werte
   * in einem Wertebereich liegen. Es soll dabei binäre Suche verwendet werden.
   * 
   * Beispiel:
   *                     0 <-----------------------> 5
   * sortedData:         [-10, 33, 50, 99, 123, 4242 ]
   * valueRange: [80;700]              ^   ^
   *                                   |   |
   *                                   |   `- Ergebnis (4) für obere Intervallbegrenzung,
   *                                   |      da 123 als letzter Wert kleiner oder gleich 700 ist
   *                                   |
   *                                   `- Ergebnis (3) für untere Intervallbegrenzung,
   *                                      da 99 als erster Wert größer oder gleich 80 ist
   */

  /**
   * @param sortedData das sortierte Feld, in dem gesucht wird
   * @param valueRange der Wertebereich, zu dem Indices gesucht werden
   */
  public static Interval search(int[] sortedData, Interval valueRange) {
    int low = search(sortedData, valueRange.getFrom(), true);
    int high = search(sortedData, valueRange.getTo(), false);
    return Interval.fromArrayIndices(low, high);
  }

}
