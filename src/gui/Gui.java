package gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.DepthFirst;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Gui {
	
	public final static int scrw = 1220;
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
		graphPanel.setBounds(10, 78, scrw-60, scrh-64);
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
		//algorithmCBox.addItem("  Depth First");
		frame.getContentPane().add(algorithmCBox);
		
		JLabel lblMinimize = new JLabel("Minimize:");
		lblMinimize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMinimize.setBounds(285, 9, 89, 35);
		frame.getContentPane().add(lblMinimize);
		
		minimizeCBox = new JComboBox();
		minimizeCBox.setBounds(384, 14, 146, 30);
		minimizeCBox.setFocusable(false);
		minimizeCBox.addItem("  Distance");
		minimizeCBox.addItem("  Transport Changes");
		minimizeCBox.addItem("  Use of Subway");
		minimizeCBox.addItem("  Use of Bus");
		minimizeCBox.addItem("  Use of Taxi");
		minimizeCBox.addItem("  Use of Train");
		frame.getContentPane().add(minimizeCBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 66, 1044, 9);
		frame.getContentPane().add(separator);
		
		JLabel label = new JLabel(" ?");
		label.setToolTipText("Left click a node to set it as the start node. \r\nRight click a node to set it as the goal node.");
		label.setBackground(new Color(255, 0, 0));
		label.setFont(new Font("Tahoma", Font.BOLD, 30));
		label.setBounds(983, 7, 38, 35);
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
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveGraph();
			}
		});
		btnSave.setToolTipText("Remake Graph");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSave.setFocusable(false);
		btnSave.setBounds(801, 14, 81, 30);
		frame.getContentPane().add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadGraph();
			}
		});
		btnLoad.setToolTipText("Remake Graph");
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLoad.setFocusable(false);
		btnLoad.setBounds(892, 14, 81, 30);
		frame.getContentPane().add(btnLoad);
		graphPanel.requestFocus();
		
	}
	
	private static int random(int min, int max) {
		return (int)Math.round(Math.max(min, Math.random()*max));
	}
	
	private static void initializeGraph() {
		graph.reset();
		graph = new Graph<Location>();
		
		int i;
		int k;
				
		for (i=0; i < 84; i++)
			graph.addVertex(new Location("V" + i));
				
		for (i=0; i < 6; i++)
			for (k=0; k < 14; k++) {
				int n = i*14 + k;
				if (i != 5)
					graph.addEdge( n, n+14, random(5,30), random(0,3));
				if (k != 13)
					graph.addEdge( n, n+1, random(5,30), random(0,3));
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
		} else if (getAlgorithm() == 1) {
			/*
			DepthFirst.getPath(graph.getVertex(startNodeID), graph.getVertex(goalNodeID));
			
			
			path = DepthFirst.getPath(graph.getVertex(startNodeID), graph.getVertex(goalNodeID));
			
			System.out.println();
			for (Node n : path)
				System.out.print( " -> " + n.vertex.getValue().getName());
			System.out.println();
			*/
		}
		
		if (path == null)
			JOptionPane.showMessageDialog(null, "Error.");
	}
	
	private static void saveGraph() {
		
		
		File dir = new File("graphs");
		if (!dir.isDirectory()) dir.mkdir();
		
		String filename = "graph";
		int i=0;
		while(new File(dir.getAbsolutePath() + "\\" + filename + i + ".dat").exists()) {
			i++;
		}
		filename = filename + i + ".dat";
		
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream("graphs/" + filename));
			os.writeObject(graph);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
		finally { if (os != null)
			try {
				os.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} }
		
	}
	
	@SuppressWarnings("unchecked")
	private static void loadGraph() {
		
		File dir = new File("graphs");
		if (!dir.isDirectory()) {
			JOptionPane.showMessageDialog(frame, "You have no saved graphs.", "Load Graph", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose a file");
		chooser.setApproveButtonText("Load");
		chooser.setApproveButtonToolTipText("Load selected file");
		chooser.setCurrentDirectory(dir);
		chooser.setFileSelectionMode(0);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new ExtensionFileFilter("DAT (*.dat)", new String[] { "dat" }));
		
		int check = chooser.showOpenDialog(null);
		if (check == JFileChooser.APPROVE_OPTION) {
			String filepath = chooser.getSelectedFile().getAbsolutePath();
			String fileExtension = filepath.substring(filepath.lastIndexOf('.'));
			if (!fileExtension.equals(".dat")) {
				JOptionPane.showMessageDialog(frame, "Invalid file type.");
				return;
			}
			ObjectInputStream is = null;
			try {
				is = new ObjectInputStream(new FileInputStream(filepath));
				
				graph = null;
				graph = (Graph<Location>) is.readObject();
				graphPanel.initialize();
				graphPanel.repaint();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			finally {
				if (is != null)
					try {
						is.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}
		
	}
}
