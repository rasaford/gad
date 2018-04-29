package blatt3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import org.junit.Test;

public class RingQueueTest {

  private int[] getDynArraySize(RingQueue stack) {
    try {
      Field f = stack.getClass().getDeclaredField("dynArr");
      f.setAccessible(true);
      DynamicArray dynamicArray = (DynamicArray) f.get(stack);
      Field array = dynamicArray.getClass().getDeclaredField("elements");
      array.setAccessible(true);
      return (int[]) array.get(dynamicArray);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      System.err.println(e);
    }
    return null;
  }

  @Test
  public void problemsetExample() {
    RingQueue r = new RingQueue(2, 3);
    assertArrayEquals(new int[]{}, getDynArraySize(r));
    r.enqueue(1);
    assertEquals(1, r.getSize());
    assertArrayEquals(new int[]{1, 0}, getDynArraySize(r));
    r.enqueue(9);
    assertEquals(2, r.getSize());
    assertArrayEquals(new int[]{1, 9}, getDynArraySize(r));
    r.enqueue(7);
    assertEquals(3, r.getSize());
    assertArrayEquals(new int[]{1, 9, 7, 0, 0, 0}, getDynArraySize(r));
    r.dequeue();
    assertEquals(2, r.getSize());
    assertArrayEquals(new int[]{0, 9, 7, 0, 0, 0}, getDynArraySize(r));
    r.dequeue();
    assertEquals(1, r.getSize());
    assertArrayEquals(new int[]{7, 0}, getDynArraySize(r));
    r.enqueue(4);
    assertEquals(2, r.getSize());
    assertArrayEquals(new int[]{7, 4}, getDynArraySize(r));
    r.enqueue(8);
    assertEquals(3, r.getSize());
    assertArrayEquals(new int[]{7, 4, 8, 0, 0, 0}, getDynArraySize(r));
  }

}
