package blatt4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class HashStringTest {


  @Test
  public void basicHashing() {
    HashString h = new HashString(269);
    String[] strings = new String[]{
        "test",
        "this is a very very long string to test the hashing with",
        "keine Umlaute",
        "Äpfel",
        "å∂®ªå∂®~‚€øƒ€∞çå‚€ç‚å€πå‚®⁄©µå∂†ª@ø"
    };
    for (String word : strings) {
      System.out.printf("h(%s)=%d\n",word, h.hash(word));
    }
  }

  @Test
  public void bitExtraction() {
    HashString h = new HashString(1 << 8);
    byte[] data = new byte[]{0x11, 0x12, 0x34};
    assertEquals(0x11, h.getBlock(0, data));
    assertEquals(0x12, h.getBlock(1, data));
    assertEquals(0x34, h.getBlock(2, data));

    h = new HashString(1 << 6);
    assertEquals(0x04, h.getBlock(0, data));
    assertEquals(0x11, h.getBlock(1, data));
//    assertEquals(0x04, h.getBlock(2, data));
//    assertEquals(0x11, h.getBlock(3, data));
//    assertEquals(0x04, h.getBlock(4, data));
//    assertEquals(0x11, h.getBlock(5, data));
    h = new HashString(1 << 10);
    assertEquals(0x44, h.getBlock(0, data));
  }
}
