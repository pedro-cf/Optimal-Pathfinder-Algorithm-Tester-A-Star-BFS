package logic;

import java.awt.Color;

public class Edge<T> {
	
	private int id;
	private Vertex<T> v1, v2;
	private int weight;
	private Color type;

	public Edge(Vertex<T> v1, Vertex<T> v2, int weight, int id, Color type) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
		this.id = id;
		this.type = type;
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}
	
	public Color getType() {
		return type;
	}

	public void setType(Color type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getWeight() {
		return weight;
	}
	
	@SuppressWarnings("rawtypes")
	public Vertex[] getVertexes() {
		return new Vertex[]{v1, v2};
	}
	
	public Vertex<?> otherVertex(Vertex<?> v) {
		return (v == v1) ? v2 : v1;
	}
	
	public void print() {
		System.out.println("[id="+id+"][v1="+v1.getId()+"][v2="+v2.getId()+"][weight="+weight+"]");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {

		Edge<T> e = (Edge<T>) obj;
		
		Vertex<T>[] arr =  (Vertex<T>[]) e.getVertexes();
		
		return (arr[0].getId() == v1.getId()  && arr[1].getId() == v2.getId()) 
				|| (arr[0].getId() == v2.getId()  && arr[1].getId() == v1.getId()) ;
	}

	public boolean contains(Vertex<T> v) {
		if (this.v1 == v || this.v2 == v)
			return true;
		return false;
	}


	public boolean containsLoc(Vertex<Location> v) {
		if (this.v1.equals(v) || this.v2.equals(v))
			return true;
		return false;
	}

}
