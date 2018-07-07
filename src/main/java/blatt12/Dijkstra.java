import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

  private class DijkstraNode {

    public Graph.Node node;
    public int priority;

    public DijkstraNode(Graph.Node node, int priority) {
      this.node = node;
      this.priority = priority;
    }
  }

  private PriorityQueue<DijkstraNode> queue;
  private Map<Graph.Node, Integer> distances;
  private Map<Graph.Node, Graph.Node> parents;

  private Graph.Node start;
  private Graph.Node end;

  public Dijkstra() {
    this.queue = new PriorityQueue<>(Comparator.comparingInt(x -> x.priority));
    this.distances = new HashMap<>();
    this.parents = new HashMap<>();
  }

  public void findRoute(Graph g, Graph.Node a, Graph.Node b) {
    queue.clear();
    end = b;
    start = a;

    queue.add(new DijkstraNode(a, 0));
    distances.put(a, 0);

    while (!queue.isEmpty()) {
      DijkstraNode current = queue.poll();
      for (int i = 0; i < current.node.getNeighbours().size(); i++) {
        Graph.Node neigbour = current.node.getNeighbours().get(i);
        int newDist = distances.get(current.node) + current.node.getWeight(i);

        if (!distances.containsKey(neigbour) || newDist < distances.get(neigbour)) {
          parents.put(neigbour, current.node);
          queue.add(new DijkstraNode(neigbour, newDist));
          distances.put(neigbour, newDist);
        }
      }
    }
  }

  public List<Graph.Node> getShortestPath() {
    LinkedList<Graph.Node> list = new LinkedList<>();
    Graph.Node parent = end;
    do {
      list.addFirst(parent);
      parent = parents.get(parent);
    } while (parent != null && !parent.equals(start));
    if (parent != null) {
      list.addFirst(parent);
    }
    return list;
  }

  public Integer getShortestPathLength() {
    return distances.get(end);
  }
}