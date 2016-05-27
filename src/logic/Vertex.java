package logic;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> implements Comparable<Vertex<T>>{
	
	private int id;
	private T obj;
	private List<Edge<T>> edges = new ArrayList<Edge<T>>();
	private List<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
	protected static int x_inc = 50;
	protected static int y_inc = 50;
	private int x;
	private int y;
	private final static int yspacing = 75;
	private final static int xspacing = 75;
	private final static int x_initial_spacing = 50;
	private final static int xlimit = 1050;
	
	public Vertex(T obj) {
		this.obj = obj;
		this.x = x_inc;
		this.y = y_inc;
		
		x_inc += xspacing;
		if (x_inc > xlimit) {
			x_inc = xspacing;
			y_inc += yspacing;
		}
		
	}
	
	protected Vertex(T obj, int id) {
		this.obj = obj;
		this.id = id;
		
		this.x = x_inc;
		this.y = y_inc;
		
		x_inc += xspacing;
		if (x_inc > xlimit) {
			x_inc = x_initial_spacing;
			y_inc += yspacing;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public T getValue() {
		return obj;
	}
	
	public List<Edge<T>> getEdges() {
		return edges;
	}
	
	protected void addEdge(Edge<T> e) {
		edges.add(e);
	}
	
	protected void addNeighbor(Vertex<T> v) {
		neighbors.add(v);
	}
	
	public void print() {
		System.out.println("[id="+id+"][obj="+obj.toString()+"]");
	}
	
	public List<Vertex<T>> getNeighbors() {
		return this.neighbors;
	}

	/**
	 * get the edge that contains the current node and node v
	 */
	public Edge<T> getEdge(Vertex<T> v) {
		for (Edge<T> edge : edges) {
			if (edge.contains(this) && edge.contains(v)) {
				return edge;
			}
		}
		return null;
	}
	
	public double distance(Vertex<T> v) {
		return Math.sqrt((this.x-v.x)*(this.x-v.x) + (this.y-v.y)*(this.y-v.y));
	}

	@Override
	public int compareTo(Vertex<T> arg0) {
		if (this.getId() == arg0.getId())
			return 0;
		return 1;
	}

	public void clearEdges() {
		this.edges.clear();
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vertex)) return false;
		Vertex<T> ver = (Vertex<T>) object;
		if (ver.id == this.id) return true;
		return false;
	}

}
