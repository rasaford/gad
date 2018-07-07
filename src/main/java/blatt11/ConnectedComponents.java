package blatt11;

public class ConnectedComponents {

  private BFS search;

  public ConnectedComponents() {
    search = new BFS();
  }

  /**
   * Zählt alle Zusammenhangskomponenten im gegebenen Graphen g
   */
  public int countConnectedComponents(Graph g) {
    int components = 0;

    for (Graph.Node node : g.getNodes()) {
      if (search.getDepth(node) == null) {
        search.sssp(node);
        components++;
      }
    }
    return components;
  }
}