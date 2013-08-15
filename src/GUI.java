import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	private JFrame WindowFrame;
	private JComponent Drawing;
	private JPanel TopPanel;
	private int FrameWidth = 800;
	private int FrameHeight = 800;
	private Graphics g;
	private File file;
	private boolean dataLoaded = false;
	private ArrayList<Polygon> polygons;

	public static void main(String[] arg) {
		new GUI();

	}

	public GUI() {
		setupFrame();
	}

	private void setupFrame() {
		JButton quitButton = new JButton("Quit");
		JButton loadButton = new JButton("Load");
		WindowFrame = new JFrame("3D Rendering");
		WindowFrame.setSize(FrameWidth, FrameHeight);
		WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowFrame.setResizable(true);
		Drawing = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		TopPanel = new JPanel();
		TopPanel.add(quitButton);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		TopPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadData();

			}
		});
		Drawing.setSize(FrameWidth, FrameHeight);
		WindowFrame.add(Drawing);
		WindowFrame.add(TopPanel, BorderLayout.NORTH);

		WindowFrame.setVisible(true);
	}

	private void loadData() {
		JFileChooser openFile = new JFileChooser();
		openFile.showOpenDialog(null);
		File file = null;
		try {
			file = new File(openFile.getSelectedFile().toString());
		} catch (Exception e) {return;}
		if (file != null) {
			this.file = file;
			readData(file);
			dataLoaded = true;
		}
	}

	private void readData(File file) {
		this.polygons = new ArrayList<Polygon>();
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(scan != null){
			String[] lightSource = scan.nextLine().split(" ");
			float lightx = Float.parseFloat(lightSource[0]);
			float lighty = Float.parseFloat(lightSource[1]);
			float lightz = Float.parseFloat(lightSource[2]);
			Vector3D lightVector = new Vector3D(lightx,lighty,lightz);
			while(scan.hasNextLine()){
				
				String[] line = scan.nextLine().split(" ");
				float x1 = Float.parseFloat(line[0]);
				float y1 = Float.parseFloat(line[1]);
				float z1 = Float.parseFloat(line[2]);
				Vector3D v1 = new Vector3D(x1,y1,z1);
								
				float x2 = Float.parseFloat(line[3]);
				float y2 = Float.parseFloat(line[4]);
				float z2 = Float.parseFloat(line[5]);
				Vector3D v2 = new Vector3D(x2,y2,z2);
				
				float x3 = Float.parseFloat(line[6]);
				float y3 = Float.parseFloat(line[7]);
				float z3 = Float.parseFloat(line[8]);
				Vector3D v3 = new Vector3D(x3,y3,z3);
				
				int red = Integer.parseInt(line[9]);
				int green = Integer.parseInt(line[10]);
				int blue = Integer.parseInt(line[11]);
				
				polygons.add(new Polygon(lightVector, v1, v2, v3, red, green, blue));
			}
			
			
		}
		
	}

	private void draw(Graphics g) {
		this.g = g;
	}
}
