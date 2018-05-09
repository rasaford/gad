package blatt4;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

/**
 * Die Klasse {@link HashString} kann dazu verwendet werden, Strings zu hashen.
 */
public class HashString {

  private int size;
  private int blockSize;
  private ArrayList<Integer> coefficients;

  private Random random;


  /**
   * Dieser Konstruktor initialisiert ein {@link HashString} Objekt für einen gegebenen Maximalwert
   * (size - 1) der gehashten Werte.
   *
   * @param size die Größe der Hashtabelle
   */
  public HashString(int size) {
    this.size = size;
    this.blockSize = (int) (Math.log10(size) / Math.log10(2));
    this.coefficients = new ArrayList<>();
    this.random = new Random();
  }

  /**
   * getBlock() extracts the bits from [index * blockSize, (index+1)*blockSize -1] and stores them
   * in an int.
   */
  public int getBlock(int index, byte[] bytes) {
    int result = 0;
    int i = index * blockSize / 8;
    int offset = (index * blockSize) % 8;
    int size = blockSize;
    int mask;
    while (size > 0) {
      for (; offset < 8 && size > 0; offset++) {
        result <<= 1;
        size--;
        mask = 0x80 >>> offset;
        int bit = bytes[i] & mask;
        result |= bit >> (7 - offset);
      }
      offset = 0;
      i++;
    }
    return result;
  }


  /**
   * Diese Methode berechnet den Hashwert für einen String.
   *
   * @param key der Schlüssel, der gehasht werden sollen
   * @return der Hashwert des Schlüssels
   */
  public int hash(String key) {
    long scalar = 0;
    byte[] bytes = key.getBytes(Charset.defaultCharset());
    for (int i = 0; i * blockSize < bytes.length * 8; i++) {
      if (i == coefficients.size()) {
        coefficients.add(random.nextInt(size));
      }
      scalar += coefficients.get(i) * getBlock(i, bytes);
    }
    return (int) (scalar % size);
  }
}
