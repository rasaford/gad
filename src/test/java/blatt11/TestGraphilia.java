package blatt11;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;

public class TestGraphilia {

  @Test
  public void example() throws IOException {
    System.setIn(new FileInputStream(this.getClass().getResource("test.in").getFile()));

    Graphilia.main(null);
  }

}
