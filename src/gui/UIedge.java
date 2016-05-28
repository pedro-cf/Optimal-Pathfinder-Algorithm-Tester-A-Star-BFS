package gui;

import java.awt.Color;

import logic.Edge;
import logic.Location;
import static logic.Astar.*;

public class UIedge {
	
	private UIvertex v1, v2;
	private Edge<Location> edge;
	private boolean highlighted = false;
	private int offset = 0;

	public void incOffset(int inc) {
		offset += inc;
	}
	
	public int getOffset() {
		return this.offset;
	}
	

	public Color getColor() {
		if (edge.getType() == logic.Astar.SUBWAY) return Color.green;
		if (edge.getType() == logic.Astar.BUS) return Color.blue;
		if (edge.getType() == logic.Astar.TAXI) return Color.yellow;
		if (edge.getType() == logic.Astar.TRAIN) return Color.magenta;
		
		return Color.black;
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
	
	public boolean isHorizontal() {
		if (v1.getY() == v2.getY()) return true;
		return false;
	}
	
}
