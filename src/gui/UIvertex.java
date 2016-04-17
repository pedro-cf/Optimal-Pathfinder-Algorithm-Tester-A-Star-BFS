package gui;

import java.awt.Color;
import java.awt.Point;

import logic.Location;
import logic.Vertex;
import static gui.GraphPanel.*;

public class UIvertex {
	
	private int id;
	
	private int x;
	private int y;
	private Vertex<Location> vertex;
	private boolean dragging = false;
	private Color color = Color.white;
	private boolean highlighted = false;
	
	public boolean isHighlighted() {
		return highlighted;
	}


	public Color getColor() {
		return color;
	}


	public void highlight() {
		this.color = Color.red;
		highlighted = true;
	}
	
	public void unhighlight() {
		this.color = Color.white;
		highlighted = false;
	}


	public UIvertex(Vertex<Location> v) {
		this.x = v.getX();
		this.y = v.getY();
		vertex = v;
		id = vertex.getId();
	}
	

	public boolean isDragging() {
		return dragging;
	}


	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}


	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
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

	public Vertex<Location> getVertex() {
		return vertex;
	}

	public boolean onTop(int x, int y) {
		if (x < this.x || x > this.x + diameter) {
			return false;
		}

		if (x >= this.x && x <= this.x + diameter && y >= this.y && y <= this.y + diameter) {
			return true;
		}

		return false;
	}
}
