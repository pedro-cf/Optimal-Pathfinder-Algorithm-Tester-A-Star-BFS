package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) {
		
		Graph<String> g = new Graph<String>();

		g.addVertex("USA");
		g.addVertex("Brazil");
		g.addVertex("Portugal");
		g.addVertex("France");
		g.addVertex("Russia");
		
		g.addEdge(0, 1, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 3, 1);
		g.addEdge(0, 2, 1);
		g.addEdge(1, 3, 1);
		g.addEdge(0, 4, 1);
		g.addEdge(2, 4, 1);
		g.addEdge(1, 4, 1);
		
		
		
		g.print();
		
		System.out.println("---------------------------------\n");

		
		
	}

}
