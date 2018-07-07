package blatt11;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  public class Node {

    private List<Node> edges;

    public Node() {
      edges = new ArrayList<>();
    }

    public void addEdge(Node node) {
      if (!edges.contains(node)) {
        edges.add(node);
      }
    }

    public List<Node> getNeighbours() {
      return edges;
    }
  }

  private List<Node> nodes;
  private int index;

  public Graph() {
    nodes = new ArrayList<>();
  }

  /**
   * Erstellt einen neuen Knoten, und gibt den Index dieses Knotens zurück. Der erste erstellte
   * Knoten sollte Index 0 haben. Knoten, die direkt nacheinander erstellt werden, sollten
   * aufsteigende Indices haben.
   */
  public Integer addNode() {
    int i = index;
    nodes.add(index++, new Node());
    return i;
  }

  /**
   * Liefert den Knoten zum angegebenen Index zurück
   */
  public Graph.Node getNode(Integer id) {
    return nodes.get(id);
  }

  /**
   * Fügt zwischen den beiden angegebenen Knoten eine (ungerichtete) Kante hinzu.
   */
  public void addEdge(Graph.Node a, Graph.Node b) {
    if (!nodes.contains(a) || !nodes.contains(b)) {
      throw new RuntimeException("nodes not in graph");
    }
    a.addEdge(b);
    b.addEdge(a);
  }


  public List<Node> getNodes() {
    return nodes;
  }
}
