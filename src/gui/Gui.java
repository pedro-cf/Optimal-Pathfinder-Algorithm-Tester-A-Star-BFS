package gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Graph;
import logic.Location;
import logic.Node;
import logic.Vertex;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import logic.Astar;
import static gui.GraphPanel.*;
import java.awt.Color;

public class Gui {
	
	public final static int scrw = 1080;
	public final static int scrh = 670;
	

	private static JFrame frame;
	private static GraphPanel graphPanel;
	private static JButton highlightButton;
	private static JComboBox algorithmCBox;
	private static JComboBox minimizeCBox;
	
	private static Vertex<Location> start;
	private static Vertex<Location> goal;
	
	protected static Graph<Location> graph;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					initializeGraph();
					
					Gui window = new Gui();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, scrw, scrh);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		highlightButton = new JButton("Highlight Path");
		highlightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (startNodeID == -1 || goalNodeID == -1) { 
					JOptionPane.showMessageDialog(null, "Please select a start and goal node first.");
					return;	
				}
				highlightPath();
			}
		});
		highlightButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		highlightButton.setFocusable(false);
		highlightButton.setBounds(554, 14, 171, 30);
		frame.getContentPane().add(highlightButton);
		
		
		graphPanel = new GraphPanel();
		graphPanel.setBounds(10, 78, scrw-210, scrh-64);
		frame.getContentPane().add(graphPanel);
		
		graphPanel.setFocusTraversalKeysEnabled(false);
		graphPanel.setFocusable(true);
		
		JLabel lblAlgorithm = new JLabel("Algorithm:");
		lblAlgorithm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAlgorithm.setBounds(10, 9, 94, 35);
		frame.getContentPane().add(lblAlgorithm);
		
		algorithmCBox = new JComboBox();
		algorithmCBox.setBounds(114, 14, 146, 30);
		algorithmCBox.setFocusable(false);
		algorithmCBox.addItem("  A*");
		frame.getContentPane().add(algorithmCBox);
		
		JLabel lblMinimize = new JLabel("Minimize:");
		lblMinimize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMinimize.setBounds(285, 9, 89, 35);
		frame.getContentPane().add(lblMinimize);
		
		minimizeCBox = new JComboBox();
		minimizeCBox.setBounds(384, 14, 146, 30);
		minimizeCBox.setFocusable(false);
		minimizeCBox.addItem("  Time/Cost");
		minimizeCBox.addItem("  Transport Changes");
		frame.getContentPane().add(minimizeCBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 66, 1044, 9);
		frame.getContentPane().add(separator);
		
		JLabel label = new JLabel(" ?");
		label.setToolTipText("Left click a node to set it as the start node. \r\nRight click a node to set it as the goal node.");
		label.setBackground(new Color(255, 0, 0));
		label.setFont(new Font("Tahoma", Font.BOLD, 30));
		label.setBounds(816, 9, 38, 35);
		frame.getContentPane().add(label);
		
		JButton btnR = new JButton("R");
		btnR.setToolTipText("Remake Graph");
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graph = null;
				initializeGraph();
				graphPanel.initialize();
				graphPanel.repaint();
			}
		});
		btnR.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnR.setFocusable(false);
		btnR.setBounds(735, 14, 56, 30);
		frame.getContentPane().add(btnR);
		graphPanel.requestFocus();
		
	}
	
	private static int random(int min, int max) {
		return (int)Math.round(Math.max(min, Math.random()*max));
	}
	
	private static void initializeGraph() {
		graph.reset();
		graph = new Graph<Location>();
		
		//start = graph.addVertex(new Location("V1"));
		//goal = 	graph.addVertex(new Location("V4"));
		

		int i;
		int k;
				
		for (i=0; i < 40; i++)
			graph.addVertex(new Location("V" + i));
		
		//for (int k=0; k < 32; k++)
		//	graph.addEdge( k, k+1, random(1,20));
				
		for (i=0; i < 5; i++)
			for (k=0; k < 8; k++) {
				int n = i*8 + k;
				if (i != 4)
					graph.addEdge( n, n+8, random(5,30), Color.cyan);
				if (k != 7)
					graph.addEdge( n, n+1, random(5,30), Color.orange);
			}
		
	}
	
	public static int getAlgorithm() {
		return algorithmCBox.getSelectedIndex();
	}
	
	public static int getMinimize() {
		return minimizeCBox.getSelectedIndex();
	}
	
	private static void highlightPath() {
		List<Node> path = null;
		if (getAlgorithm() == 0) {
			path = Astar.getPath(graph.getVertex(startNodeID), graph.getVertex(goalNodeID));
		
			System.out.println();
			for (Node n : path)
				System.out.print( " -> " + n.vertex.getValue().getName());
			System.out.println();
			
			graphPanel.highlightPath(path);
		}
		
		if (path == null)
			JOptionPane.showMessageDialog(null, "Error.");
	}
}
