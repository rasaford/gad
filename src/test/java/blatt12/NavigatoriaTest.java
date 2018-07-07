import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;

public class NavigatoriaTest {


  @Test
  public void example() throws IOException {
    System.setIn(new FileInputStream(this.getClass().getResource("6_1.in").getFile()));
    Navigatoria.main(null);
//    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//    PrintStream out = new PrintStream(byteOut);
//    System.setOut(out);
//
//    String[] stringOut = out.toString().split("\n");
//    int index = 0;
//    try (BufferedReader r = new BufferedReader(
//        new FileReader(this.getClass().getResource("6_1.out").getFile()))) {
//      String line;
//      while ((line = r.readLine()) != null) {
//
//      }
//    }
  }
}
