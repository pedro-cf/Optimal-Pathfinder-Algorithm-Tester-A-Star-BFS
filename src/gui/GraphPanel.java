package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import logic.Edge;
import logic.Location;
import logic.Node;
import logic.Vertex;
import static gui.Gui.*;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	protected static boolean draggingAllowed = false;
	
	protected static int startNodeID = -1;
	protected static int goalNodeID = -1;
	
	protected final static int diameter = 30;
	protected final static int edge_offset = diameter / 2;
	protected final static int vertex_offset_x = diameter / 3;
	protected final static int vertex_offset_y = diameter / 2;
	private final Font f = new Font("Dialog", Font.PLAIN, diameter / 2);
	private final Font f2 = new Font("Dialog", Font.BOLD, diameter / 2);

	private List<Vertex<Location>> vertexes;
	private List<Edge<Location>> edges;

	private Vector<UIvertex> ui_vertexes = new Vector<UIvertex>();
	private Vector<UIedge> ui_edges = new Vector<UIedge>();

	public GraphPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		
		initialize();

	}

	public void initialize() {
		startNodeID = -1;
		goalNodeID = -1;
		ui_vertexes.clear();
		ui_edges.clear();
		vertexes = graph.getVertexes();
		edges = graph.getEdges();

		for (Vertex<Location> v : vertexes) {
			ui_vertexes.add(new UIvertex(v));
		}

		for (Edge<Location> e : edges) {
			Vertex<Location> vs[] = e.getVertexes();
			Vector<UIvertex> uivs = new Vector<UIvertex>();
			for (UIvertex uiv : ui_vertexes) {
				if (uiv.getVertex() == vs[0] || uiv.getVertex() == vs[1])
					uivs.add(uiv);
			}
			ui_edges.add(new UIedge(uivs.get(0), uivs.get(1), e));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (UIedge e : ui_edges) {
			UIvertex v1 = e.getV1();
			UIvertex v2 = e.getV2();
			
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(4.0f));

			int i = v1.getX() + edge_offset;
			int j = v1.getY() + edge_offset;	
			int k = v2.getX() + edge_offset;
			int l = v2.getY() + edge_offset;
			
			g2.drawLine(i, j, k, l);

			if (e.isHighlighted()) {
				g2.setColor(Color.red);
			} else {
				g2.setColor(Color.white);
			}
			
			g2.setStroke(new BasicStroke(3.0f));
			g2.drawLine(i, j, k, l);
			
			
			int mx = (i + k)/2;
		    int my = (j + l)/2;
		    
		    
		    g2.setColor(Color.black);
		    g2.setFont(f);
		    g2.drawString(( (Integer) e.getEdge().getWeight()).toString(), mx, my);
			
		}

		g2.setStroke(new BasicStroke(1.0f));
		g2.setFont(f2);
		for (UIvertex v : ui_vertexes) {
			g2.setColor(v.getColor());
			int x = v.getX();
			int y = v.getY();
			g2.fillOval(x, y, diameter, diameter);
					
			g2.setColor(Color.black);
			g2.drawOval(x, y, diameter, diameter);
			
			if (v.getId() == startNodeID)
				g2.drawString("S", x
						+ vertex_offset_x, y + vertex_offset_y+5);
			else if (v.getId() == goalNodeID)
				g2.drawString("G", x
						+ vertex_offset_x, y + vertex_offset_y+5);
			
			//g2.drawString(v.getVertex().getValue().getName(), x
			//		+ vertex_offset_x, y + vertex_offset_y);
		}

	}

	
	public void highlightPath(List<Node> path) {
		for (UIvertex uiv : ui_vertexes)
			uiv.unhighlight();
		for (UIedge uie : ui_edges)
			uie.unhighlight();
		
		for (UIvertex uiv : ui_vertexes) {
			
			for (int i=0; i < path.size(); i++) {
				if (uiv.getVertex().equals(path.get(i).vertex)) {
					uiv.highlight();
					if (i<path.size()-1) {
						for (UIedge uie : ui_edges) {
							if (uie.getEdge().containsLoc(path.get(i).vertex) && uie.getEdge().containsLoc(path.get(i+1).vertex)) {
								uie.highlight();
							}
						}
					}
				}
			}
		}
		repaint();
	}


	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}



	@Override
	public void mousePressed(MouseEvent e) {
		int x = (int) e.getX(),
		y = (int) e.getY();
		
		for (UIvertex v : ui_vertexes) {
			if (v.onTop(x, y)) {
				v.setDragging(true);
				
				if (SwingUtilities.isLeftMouseButton(e)) {
					startNodeID = v.getId();
					if (startNodeID == goalNodeID)
						goalNodeID = -1;
					repaint();
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					goalNodeID = v.getId();
					if (startNodeID == goalNodeID)
						startNodeID = -1;
					repaint();
				}
				
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (UIvertex v : ui_vertexes) {
			v.setDragging(false);
		}
	}

	
	public static int clamp(int val, int min, int max) {
	    return Math.max(min, Math.min(max, val));
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if (!draggingAllowed) return;
		for (UIvertex v : ui_vertexes) {
			if (v.isDragging()) {
				v.setPos(clamp(e.getX() - vertex_offset_x, 0, scrw-diameter - 40), clamp(e.getY() - vertex_offset_y, 0, scrh-diameter - 120));
				repaint();
				break;
			}
		}
	}



	@Override
	public void mouseMoved(MouseEvent e) {}
	
}
