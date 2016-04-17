package logic;

public class Node {
	
	public Vertex<Location> vertex;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node(Vertex<Location> vertex, Node parent, double gCost, double hCost) {
		this.vertex = vertex;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}

}
