package blatt3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import org.junit.Test;

public class DynamicStackTest {


  private int[] getDynArraySize(DynamicStack stack) {
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
    DynamicStack stack = new DynamicStack(2, 4);
    stack.pushBack(3);
    assertEquals(1, stack.getLength());
    assertArrayEquals(new int[]{3, 0}, getDynArraySize(stack));
    stack.pushBack(8);
    assertEquals(2, stack.getLength());
    assertArrayEquals(new int[]{3, 8}, getDynArraySize(stack));
    stack.pushBack(1);
    assertEquals(3, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 0, 0, 0}, getDynArraySize(stack));
    stack.pushBack(3);
    assertEquals(4, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 0, 0}, getDynArraySize(stack));
    stack.pushBack(6);
    assertEquals(5, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 6, 0}, getDynArraySize(stack));
    stack.popBack();
    assertEquals(4, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 0, 0}, getDynArraySize(stack));
    stack.pushBack(7);
    assertEquals(5, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 7, 0}, getDynArraySize(stack));
    stack.pushBack(3);
    assertEquals(6, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 7, 3}, getDynArraySize(stack));
    stack.pushBack(5);
    assertEquals(7, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 7, 3, 5, 0, 0, 0, 0, 0, 0, 0}, getDynArraySize(stack));
    stack.popBack();
    assertEquals(6, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 7, 3, 0, 0, 0, 0, 0, 0, 0, 0}, getDynArraySize(stack));
    stack.popBack();
    assertEquals(5, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0}, getDynArraySize(stack));
    stack.popBack();
    assertEquals(4, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, getDynArraySize(stack));
    stack.popBack();
    assertEquals(3, stack.getLength());
    assertArrayEquals(new int[]{3, 8, 1, 0, 0, 0}, getDynArraySize(stack));
  }

}
