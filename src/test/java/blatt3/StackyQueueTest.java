package blatt3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Field;
import org.junit.Test;

public class StackyQueueTest {

  private int[] get(StackyQueue queue, String field) {
    try {
      Field f = queue.getClass().getDeclaredField(field);
      f.setAccessible(true);
      DynamicStack stack = (DynamicStack) f.get(queue);
      Field s = stack.getClass().getDeclaredField("dynArr");
      s.setAccessible(true);
      DynamicArray array = (DynamicArray) s.get(stack);
      Field a = array.getClass().getDeclaredField("elements");
      a.setAccessible(true);
      return (int[]) a.get(array);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      System.err.println(e);
    }
    return null;
  }

  @Test
  public void problemsetExample() {
    StackyQueue r = new StackyQueue(4, 8);
    assertArrayEquals(new int[]{}, get(r, "in"));
    assertArrayEquals(new int[]{}, get(r, "out"));
    r.enqueue(4);
    assertArrayEquals(new int[]{4, 0, 0, 0}, get(r, "in"));
    assertArrayEquals(new int[]{}, get(r, "out"));
    r.enqueue(1);
    assertArrayEquals(new int[]{4, 1, 0, 0}, get(r, "in"));
    assertArrayEquals(new int[]{}, get(r, "out"));
    r.enqueue(5);
    assertArrayEquals(new int[]{4, 1, 5, 0}, get(r, "in"));
    assertArrayEquals(new int[]{}, get(r, "out"));
    r.enqueue(6);
    assertArrayEquals(new int[]{4, 1, 5, 6}, get(r, "in"));
    assertArrayEquals(new int[]{}, get(r, "out"));
    r.dequeue();
    assertArrayEquals(new int[]{}, get(r, "in"));
    assertArrayEquals(new int[]{6, 5, 1, 0}, get(r, "out"));
    r.dequeue();
    assertArrayEquals(new int[]{}, get(r, "in"));
    assertArrayEquals(new int[]{6, 5, 0, 0}, get(r, "out"));
    r.enqueue(2);
    assertArrayEquals(new int[]{2, 0, 0, 0}, get(r, "in"));
    assertArrayEquals(new int[]{6, 5, 0, 0}, get(r, "out"));
    r.dequeue();
    assertArrayEquals(new int[]{2, 0, 0, 0}, get(r, "in"));
    assertArrayEquals(new int[]{6, 0, 0, 0}, get(r, "out"));
  }

}
