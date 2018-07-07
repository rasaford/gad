package blatt11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS {

  private Map<Graph.Node, Integer> depths;
  private Map<Graph.Node, Graph.Node> parents;

  public BFS() {
    depths = new HashMap<>();
    parents = new HashMap<>();
  }


  /**
   * Führt eine Breitensuche vom Startknoten "start" aus, um das SSSP-Problem zu lösen.
   */
  public void sssp(Graph.Node start) {
    depths.put(start, 0);
    parents.put(start, start);
    Queue<Graph.Node> queue = new LinkedList<>();
    queue.add(start);

    while (!queue.isEmpty()) {
      Graph.Node current = queue.poll();

      for (Graph.Node neighbour : current.getNeighbours()) {
        if (parents.get(neighbour) == null) {
          queue.add(neighbour);
          depths.put(neighbour, depths.get(current) + 1);
          parents.put(neighbour, current);
        }
      }
    }
  }

  /**
   * Nachdem SSSP ausgeführt wurde, kann mit dieser Funktion die Tiefe des Knotens n vom Startknoten
   * überprüft werden.
   */
  public Integer getDepth(Graph.Node n) {
    return depths.get(n);
  }

  /**
   * Nachdem SSSP ausgeführt wurde, kann mit dieser Funktion der Vaterknoten des Knotens n in
   * Richtung Startknoten abgefragt werden.
   */
  public Graph.Node getParent(Graph.Node n) {
    return parents.get(n);
  }

}
