package blatt5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class DoubleHashieTest {


  @Test
  public void testHashTick() {
    final int size = 255;
    DoubleHashable h = new DoubleHashInt(size);
    Set<Integer> keys = new HashSet<>();
    Set<Long> hashes = new HashSet<>();
    Random r = new Random();

    while (keys.size() != 100) {
      keys.add(r.nextInt(Integer.MAX_VALUE));
    }

    for (int key : keys) {
      for (int i = 0; i < 10; i++) {
        long hash = (h.hash(key) + i * h.hashTick(key)) % size;
        assertTrue(hashes.add(hash));
      }
      hashes.clear();
    }
  }

  @Test
  public void testInt() {
    DoubleHashTable<Integer, Integer> ht = new DoubleHashTable<>(5347, new IntHashableFactory());

    Hashtable<Integer, Integer> htJava = new Hashtable<>();

    Random r = new Random();

    for (int i = 0; i < 5000; i++) {
      int key = r.nextInt(Integer.MAX_VALUE);
      while (htJava.contains(key)) {
        key = r.nextInt(Integer.MAX_VALUE);
      }
      int value = r.nextInt(Integer.MAX_VALUE);

      assertTrue(ht.insert(key, value), String.format("unable to insert {%d,%d}", key, value));
      htJava.put(key, value);
    }

    Iterator<Entry<Integer, Integer>> it = htJava.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Integer, Integer> entry = it.next();
      assertEquals(entry.getValue(), ht.find(entry.getKey()).get());
    }
  }

  @Test
  public void testString() {
    DoubleHashTable<String, Integer> ht = new DoubleHashTable<>(997, new StringHashableFactory());

    assertTrue(ht.insert("Hugo", 99));
    assertTrue(ht.insert("Inge", 22));
    assertTrue(ht.insert("Egon", 100));
    assertEquals(Optional.empty(), ht.find("Atomfriedolina"));
    assertEquals(Optional.of(99), ht.find("Hugo"));
    assertTrue(ht.insert("Oliver", 111));
    assertTrue(ht.insert("Rosa", 12));
    assertEquals(Optional.of(12), ht.find("Rosa"));
    assertEquals(Optional.of(22), ht.find("Inge"));
    assertEquals(Optional.of(100), ht.find("Egon"));
    assertTrue(ht.insert("Egon", 333));
    assertEquals(Optional.of(333), ht.find("Egon"));
    assertEquals(Optional.of(22), ht.find("Inge"));
    assertEquals(Optional.empty(), ht.find("Rosamunde"));
    assertTrue(ht.insert("Bert", 88));
    assertEquals(Optional.empty(), ht.find("Peter"));
    assertTrue(ht.insert("Peter", 66));
    assertEquals(Optional.of(12), ht.find("Rosa"));
    assertEquals(Optional.of(88), ht.find("Bert"));
    assertEquals(Optional.of(333), ht.find("Egon"));
    assertEquals(Optional.of(66), ht.find("Peter"));
  }

}
