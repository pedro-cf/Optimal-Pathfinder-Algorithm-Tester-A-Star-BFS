package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Pedro
 */
public class Graph<T> {
	
	private List<Vertex<T>> vertexes = new ArrayList<Vertex<T>>();
	private List<Edge<T>> edges = new ArrayList<Edge<T>>();
	
	private int vertexCount;
	private int edgeCount;
	
	public Graph() {
		this.vertexCount = 0;
		this.edgeCount = 0;
	}
	
	public static void reset() {
		logic.Vertex.x_inc = 50;
		logic.Vertex.y_inc = 50;
	}
	
	private void removeVertex(Vertex<T> v) {
		for(Edge<T> e : v.getEdges()) {
			this.edges.remove(e);
		}
		v.clearEdges();
	}

	public int getTotalWeight() {
		int sum = 0;
		for (Edge<T> e : edges) {
			sum += e.getWeight();
		}
		return sum;
	}
	
	public Vertex<T> addVertex(T obj) {
		Vertex<T> v = new Vertex<T>(obj, vertexCount++);
		vertexes.add(v);
		return v;
	}
	
	//TODO tirar
	public void addVertex(Vertex<T> v) {
		vertexes.add(v);
	}
	
	public boolean addEdge(Vertex<T> v1, Vertex<T> v2, int weight) {
		Edge<T> edge = new Edge<T>(v1, v2, weight, edgeCount++);
		if (!edges.contains(edge)) {
			edges.add(edge);
			return true;
		}
		
		return false;
	}

	public boolean addEdge(int id1, int id2, int weight) {
		Edge<T> edge = new Edge<T>(vertexes.get(id1), vertexes.get(id2), weight, edgeCount++);
		
		if (!edges.contains(edge)) {
			edges.add(edge);
			return true;
		}

		return false;
	}

	public List<Vertex<T>> getVertexes() {
		return vertexes;
	}

	public List<Edge<T>> getEdges() {
		return edges;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getEdgeCount() {
		return edgeCount;
	}
	
	public Vertex<T> getVertex(int id) {
		for (Vertex<T> v: vertexes) {
			if (id == v.getId())
				return v;
		}
		return null;
	}
	
	public void print() {
		System.out.println("Vertexes:");
		for (int i = 0; i < vertexes.size(); i++) {
			vertexes.get(i).print();
		}
		System.out.println("Edges:");
		for (int i = 0; i < edges.size(); i++) {
			edges.get(i).print();
		}
	}

}
