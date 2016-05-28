package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class DepthFirst {
	public static List<Vertex<Location>> dfs(Vertex<Location> start, Vertex<Location> goal) {
		
		List<Vertex<Location>> path = new ArrayList<Vertex<Location>>();
		
		Vector<Integer> visited = new Vector<Integer>();
		Stack<Vertex<Location>> stack = new Stack();
		
		stack.push(start);
		visited.add(start.getId());
		while (!stack.empty()) {
			Vertex<Location> current = (Vertex<Location>) stack.pop();
			if (current.equals(goal)) break;
			System.out.print(current.getId() + " - ");
			
			List<Vertex<Location>> neighbors = current.getNeighbors();
			for (Vertex<Location> v : neighbors) {
				if (!visited.contains(v.getId())) {
					stack.push(v);
					visited.add(v.getId());
				}
			}
		}
		
		System.out.println();
		//System.out.println(Arrays.toString(stack.toArray()));
		
		
		return null;
	}

}
