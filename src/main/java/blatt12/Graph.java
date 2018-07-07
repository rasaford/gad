
import java.util.ArrayList;
import java.util.List;

public class Graph {

  public class Node {

    private List<Node> neighbours = new ArrayList<>();
    private List<Integer> weights = new ArrayList<>();
    private Integer id;

    public Node(Integer id) {
      this.id = id;
    }

    public List<Node> getNeighbours() {
      return neighbours;
    }


    public Integer getWeight(Integer index) {
      return weights.get(index);
    }

    public Integer getID() {
      return id;
    }

    public void addEdge(Node target, Integer weight) {
      neighbours.add(target);
      weights.add(weight);
    }
  }

  private List<Node> nodes;
  private int index;

  public Graph() {
    this.nodes = new ArrayList<>();
  }

  public Integer addNode() {
    int i = index;
    nodes.add(i, new Node(index++));
    return i;
  }

  public Graph.Node getNode(Integer id) {
    return nodes.get(id);
  }

  /**
   * Fügt eine neue direktionale Kante von Knoten a zu Knoten b mit Gewicht weight hinzu. Gewicht
   * darf nicht negativ sein.
   */
  public void addEdge(Graph.Node a, Graph.Node b, Integer weight) {
    if (!nodes.contains(a) || !nodes.contains(b)) {
      throw new RuntimeException("Both nodes have to be in the Graph to add an edge!");
    }
    a.addEdge(b, weight);
  }
}
