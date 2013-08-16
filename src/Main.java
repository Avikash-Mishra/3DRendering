import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main {
	private GUI gui;
	private File file;
	private ArrayList<Polygon> polygons;
	boolean dataLoaded = false;
	private ModelObject model;
	public Main() {
		gui = new GUI(this);
	}
	public void loadData() {
		JFileChooser openFile = new JFileChooser();
		openFile.showOpenDialog(null);
		File file = null;
		try {
			file = new File(openFile.getSelectedFile().toString());
		} catch (Exception e) {
			return;
		}
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

		if (scan != null) {
			String[] lightSource = scan.nextLine().split(" ");
			float lightx = Float.parseFloat(lightSource[0]);
			float lighty = Float.parseFloat(lightSource[1]);
			float lightz = Float.parseFloat(lightSource[2]);
			Vector3D lightVector = new Vector3D(lightx, lighty, lightz);
			while (scan.hasNextLine()) {

				String[] line = scan.nextLine().split(" ");
				float x1 = Float.parseFloat(line[0]);
				float y1 = Float.parseFloat(line[1]);
				float z1 = Float.parseFloat(line[2]);
				Vector3D v1 = new Vector3D(x1, y1, z1);

				float x2 = Float.parseFloat(line[3]);
				float y2 = Float.parseFloat(line[4]);
				float z2 = Float.parseFloat(line[5]);
				Vector3D v2 = new Vector3D(x2, y2, z2);

				float x3 = Float.parseFloat(line[6]);
				float y3 = Float.parseFloat(line[7]);
				float z3 = Float.parseFloat(line[8]);
				Vector3D v3 = new Vector3D(x3, y3, z3);

				int red = Integer.parseInt(line[9]);
				int green = Integer.parseInt(line[10]);
				int blue = Integer.parseInt(line[11]);

				polygons.add(new Polygon(lightVector, v1, v2, v3, red, green,
						blue));
			}
			model = new ModelObject(polygons);
			model.centre(800, 800);

		}

	}
	
	private int prevMouseX;
	private int prevMouseY;
	private int mouseX;
	private int mouseY;

	public void mousePressed(MouseEvent e) {
		prevMouseX = e.getX();
		prevMouseY = e.getY();
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		prevMouseX = e.getX();
		prevMouseY = e.getY();
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		float diffX = prevMouseX - mouseX;
		float diffY = prevMouseY - mouseY;
		if(model != null){
			model.rotateX(diffY/10);
			model.rotateY(diffX/10);
		}
		prevMouseX = e.getX();
		prevMouseY = e.getY();
	}



	public ArrayList<Polygon> getPolygons() {
		return this.polygons;
	}

	public static void main(String[] args) {
		new Main();
	}
}
