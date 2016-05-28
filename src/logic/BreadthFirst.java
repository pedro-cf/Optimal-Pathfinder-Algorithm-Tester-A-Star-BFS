package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BreadthFirst {
	
	private static List<Vertex<Location>> shortestPath = new ArrayList<Vertex<Location>>();
	
	public static List<Vertex<Location>> bfs(Vertex<Location> start, Vertex<Location> goal) {
		shortestPath.clear();
		
		List<Vertex<Location>> path = new ArrayList<Vertex<Location>>();
		
		if (start.equals(goal)) {
			path.add(start);
			return path;
		}
		
		ArrayDeque<Vertex<Location>> queue = new ArrayDeque<Vertex<Location>>();
		
		ArrayDeque<Vertex<Location>> visited = new ArrayDeque<Vertex<Location>>();
		
		queue.offer(start);
		while(!queue.isEmpty()) {
			
			Vertex<Location> vertex = queue.poll();
			visited.offer(vertex);

			for (Vertex<Location> neighbour : vertex.getNeighbors()) {
				
				path.add(neighbour);
				path.add(vertex);
				
				if (neighbour.equals(goal)) {
					return processPath(start, goal, path);
				} else {
					if (!visited.contains(neighbour)) {
						queue.offer(neighbour);
					}
				}
				
			}
			
			
		}
		
		
		return null;

	}
	
	private static List<Vertex<Location>> processPath(Vertex<Location> src, Vertex<Location> destination, List<Vertex<Location>> path) {

		// Finds out where the destination node directly comes from.
		int index = path.indexOf(destination);
		Vertex<Location> source = path.get(index + 1);

		// Adds the destination node to the shortestPath.
		shortestPath.add(0, destination);

		if (source.equals(src)) {
			// The original source node is found.
			shortestPath.add(0, src);
			return shortestPath;
		} else {
			// We find where the source node of the destination node
			// comes from.
			// We then set the source node to be the destination node.
			return processPath(src, source, path);
		}
	}
	
	
}
