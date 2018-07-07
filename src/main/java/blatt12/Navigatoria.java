import java.util.ArrayList;
import java.util.Scanner;

public class Navigatoria {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Graph g = new Graph();

		int n = scanner.nextInt(); // Zeile 1: wie viele Knoten sind vorhanden?
		for (int i = 0; i < n; i++) {
			g.addNode(); // Bereite n Knoten mit aufsteigenden Indices vor
		}
		for (int i = 0; i < n; i++) {
			// nächste n Zeilen: erste Zahl e = Anzahl der Kanten...
			Graph.Node node = g.getNode(i);
			int e = scanner.nextInt();
			for (int j = 0; j < e; j++) {
				// ...jede folgende Zahl entspricht dem Knotenindex,
				// zu dem eine Kante existiert, gefolgt vom Kantengewicht
				Graph.Node neighbor = g.getNode(scanner.nextInt());
				g.addEdge(node, neighbor, scanner.nextInt());
			}
		}

		int start = scanner.nextInt(); // ID des Ausgangsknotens
		int target = scanner.nextInt(); // ID des Zielknotens
		scanner.close();

		// Aufgabe: Berechne mithilfe des Dijkstra Algorithmus
		// den kürzesten Pfad von start nach target, und gebe ihn
		// auf die Standardausgabe aus, jeweils eine Knoten-ID pro
		// Zeile, beginnend mit start und endend mit target.
		// Abschließend geben Sie die Pfadlänge aus.

		Dijkstra d = new Dijkstra();
		d.findRoute(g, g.getNode(start), g.getNode(target));
		for (Graph.Node step : d.getShortestPath()) {
			System.out.println(step.getID());
		}
		System.out.println(d.getShortestPathLength());
	}
}
