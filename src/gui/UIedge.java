package gui;

import java.awt.Color;

import logic.Edge;
import logic.Location;

public class UIedge {
	
	private UIvertex v1, v2;
	private Edge<Location> edge;
	private boolean highlighted = false;
	
	public Color getColor() {
		return edge.getType();
	}
	
	public boolean isHighlighted() {
		return highlighted;
	}

	public void highlight() {
		this.highlighted = true;
	}
	
	public void unhighlight() {
		this.highlighted = false;
	}

	UIedge(UIvertex v1, UIvertex v2, Edge<Location> edge) {
		this.v1 = v1;
		this.v2 = v2;
		this.edge = edge;
	}

	public UIvertex getV1() {
		return v1;
	}

	public UIvertex getV2() {
		return v2;
	}

	public Edge<?> getEdge() {
		return edge;
	}


	
	
	
}
