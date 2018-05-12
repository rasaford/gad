package blatt5;

import java.util.Optional;

/**
 * Die Klasse DoubleHashTable implementiert eine Hashtabelle, die doppeltes Hashing verwendet.
 *
 * @param <K> der Typ der Schlüssel, die in der Hashtabelle gespeichert werden
 * @param <V> der Typ der Werte, die in der Hashtabelle gespeichert werden
 */
public class DoubleHashTable<K, V> {

  private int size;
  private int collisions;
  private int maxRehashes;
  private DoubleHashable<K> hashable;

  private Pair<K, V>[] table;

  /**
   * Diese Methode implementiert hashable(x, i).
   *
   * @param key der Schlüssel, der gehasht werden soll
   * @param i der Index, der angibt, der wievielte Hash für den gegebenen Schlüssel berechnet werden
   * soll
   * @return der generierte Hash
   */
  private int hash(K key, int i) {
    long a = hashable.hash(key);
    if (a < 0) {
      System.out.println(a);
    }
    long b = hashable.hashTick(key);
    if (b < 0) {
      System.out.println(b);
    }
    return (int) ((a + i * b) % size);
  }

  /**
   * Dieser Konstruktor initialisiert die Hashtabelle.
   *
   * @param primeSize die Größe 'm' der Hashtabelle; es kann davon ausgegangen werden, dass es sich
   * um eine Primzahl handelt.
   * @param hashableFactory Fabrik, die aus einer Größe ein DoubleHashable<K>-Objekt erzeugt.
   */
  @SuppressWarnings("unchecked")
  public DoubleHashTable(int primeSize, HashableFactory<K> hashableFactory) {
    this.size = primeSize;
    this.hashable = hashableFactory.create(size);
    this.table = new Pair[size];
    this.maxRehashes = 0;
    this.collisions = 0;
  }

  /**
   * Diese Methode fügt entsprechend des doppelten Hashens ein Element in die Hashtabelle ein.
   *
   * @param k der Schlüssel des Elements, das eingefügt wird
   * @param v der Wert des Elements, das eingefügt wird
   * @return 'true' falls das einfügen erfolgreich war, 'false' falls die Hashtabelle voll ist.
   */
  public boolean insert(K k, V v) {
    int rehashes = 0;
    int hash = hash(k, rehashes);
    boolean hasCollision = false;
    while (table[hash] != null && !table[hash]._1.equals(k)) {
      if (rehashes == size) {
        // all slots of the table have been probed and none are empty. The table is full.
        return false;
      }
      hash = hash(k, ++rehashes);
      hasCollision = true;
    }
    maxRehashes = (rehashes > maxRehashes) ? rehashes : maxRehashes;
    collisions = hasCollision ? collisions + 1 : collisions;
    table[hash] = new Pair<>(k, v);
    return true;
  }

  /**
   * Diese Methode sucht ein Element anhand seines Schlüssels in der Hashtabelle und gibt den
   * zugehörigen Wert zurück, falls der Schlüssel gefunden wurde.
   *
   * @param k der Schlüssel des Elements, nach dem gesucht wird
   * @return der Wert des zugehörigen Elements, sonfern es gefunden wurde
   */
  public Optional<V> find(K k) {
    int rehashes = 0;
    int hash = hash(k, rehashes);
    Pair<K, V> result = null;
    while (table[hash] != null && !(result = table[hash])._1.equals(k)) {
      if (rehashes == size) {
        // the entire table has been checked and the key cannot be found
        return Optional.empty();
      }
      hash = hash(k, ++rehashes);
    }
    return (result == null || !result._1.equals(k)) ? Optional.empty() : Optional.of(result._2);
  }

  /**
   * Diese Methode ermittelt die Anzahl der Kollisionen, also die Anzahl der Elemente, die nicht an
   * der 'optimalen' Position in die Hashtabelle eingefügt werden konnten. Die optimale Position ist
   * diejenige Position, die der erste Aufruf der Hashfunktion (i = 0) bestimmt.
   *
   * @return die Anzahl der Kollisionen
   */
  public int collisions() {
    return collisions;
  }

  /**
   * Diese Methode berechnet die maximale Anzahl von Aufrufen der Hashfunktion, die nötig waren, um
   * ein einziges Element in die Hashtabelle einzufügen.
   *
   * @return die berechnete Maximalzahl von Aufrufen
   */
  public int maxRehashes() {
    return maxRehashes;
  }
}
