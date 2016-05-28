package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;



public class Astar {
	
	public static int SUBWAY = 0;
	public static int BUS = 1;
	public static int TAXI = 2;
	public static int TRAIN = 3;
	
	private static Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static List<Node> getPath(Vertex<Location> start, Vertex<Location> goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, heuristic(start, goal));
		openList.add(current);
		
		while (openList.size() > 0) {
			System.out.println("----------------------------------");
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			
			if (current.vertex.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				path.add(current);
				openList.clear();
				closedList.clear();
				Collections.reverse(path);
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			System.out.println("Scanning neighbor nodes for: " +current.vertex.getValue().getName() + " g="+current.gCost + " h="+current.hCost);
			List<Vertex<Location>> neighbors = current.vertex.getNeighbors();
			for (Vertex<Location> cur_neighbor : neighbors) {
				double gCost = current.gCost + current.vertex.getEdge(cur_neighbor).getWeight();
				if (gui.Gui.getMinimize() == 1) {
					if (current.parent != null && current.vertex.getEdge(current.parent.vertex).getType() != current.vertex.getEdge(cur_neighbor).getType()) {
						gCost += 9999;
					}
				} else if (gui.Gui.getMinimize() > 1 && gui.Gui.getMinimize() < 6)
					if (current.vertex.getEdge(cur_neighbor).getType() == gui.Gui.getMinimize() - 2) gCost += 9999;
				
				double hCost = heuristic(cur_neighbor, goal);
				Node node = new Node(cur_neighbor, current, gCost, hCost);
				if (verInList(closedList, cur_neighbor) && gCost >= current.gCost){
					System.out.println("\t" + cur_neighbor.getValue().getName() + ": g="+gCost+ " h="+hCost);
					continue;
				}
				if (!verInList(openList, cur_neighbor) || gCost < current.gCost) {
					System.out.println("\t" + cur_neighbor.getValue().getName() + ": g="+gCost+ " h="+hCost + " +++");
					openList.add(node);
				}
			}
			
			
		}
		closedList.clear();
		return null;
	}
	
	private static boolean verInList(List<Node> list, Vertex<Location> vertex) {
		for (Node n : list) {
			if (n.vertex.equals(vertex)) return true;
		}
		return false;
	}
	
	private static int heuristic(Vertex<Location> cur, Vertex<Location> goal) {
		if (gui.Gui.getAlgorithm() == 0 && gui.Gui.getMinimize() == 0)
			return (int)cur.distance(goal) / 15;
		return 0;
	}

}
