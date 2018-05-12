package blatt5;

import java.util.ArrayList;
import java.util.Random;

/**
 * Die Klasse {@link DoubleHashString} kann dazu verwendet werden, Strings zu hashen.
 */
public class DoubleHashString implements DoubleHashable<String> {

  private final int size;
  private final int blockSize;
  private ArrayList<Integer> vectorH;
  private ArrayList<Integer> vectorHPrime;

  private Random random;

  /**
   * Dieser Konstruktor initialisiert ein {@link DoubleHashString} Objekt für einen gegebenen
   * Maximalwert (size - 1) der gehashten Werte.
   *
   * @param size die Größe der Hashtabelle
   */
  public DoubleHashString(int size) {
    this.size = size;
    this.blockSize = (int) (Math.log10(size) / Math.log10(2));
    this.vectorH = new ArrayList<>();
    this.vectorHPrime = new ArrayList<>();
    this.random = new Random();
  }

  /**
   * general 1-universal hash function. To get an specific hash function of the 1-universal family a
   * vector needs to be passed.
   *
   * @param key String to hash
   * @param vector random vector defining the hash function to be used.
   * @param size size of the hash table
   * @return the hashed value;
   */
  private long universalHash(String key, ArrayList<Integer> vector, int size) {
    long scalar = 0;
    for (int i = 0; i < key.length(); i++) {
      if (i == vector.size()) {
        vector.add(random.nextInt(size));
      }
      scalar += vector.get(i) * key.charAt(i);
    }
    return scalar % size;
  }

  /**
   * Diese Methode berechnet h(key) für einen String.
   *
   * @param key der Schlüssel, der gehasht werden soll
   * @return der Hashwert des Schlüssels
   */
  public long hash(String key) {
    return universalHash(key, vectorH, size);
  }

  /**
   * Diese Methode berechnet h'(key) für einen String.
   *
   * @param key der Schlüssel, der gehasht werden soll
   * @return der Hashwert des Schlüssels
   */
  public long hashTick(String key) {
    return universalHash(key, vectorHPrime, size);
  }
}
