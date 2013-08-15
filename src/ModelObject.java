import java.awt.Graphics;
import java.util.ArrayList;

public class ModelObject {
	private ArrayList<Polygon> polygons;

	/**
	 * @param polygons
	 */
	public ModelObject(ArrayList<Polygon> polygons) {
		super();
		this.polygons = polygons;
	}

	/**
	 * @return the polygons
	 */
	public ArrayList<Polygon> getPolygons() {
		return polygons;
	}

	/**
	 * @param polygons
	 *            the polygons to set
	 */
	public void setPolygons(ArrayList<Polygon> polygons) {
		this.polygons = polygons;
	}

	public void draw(Graphics g){
		for(Polygon p : polygons){
			p.draw(g);
		}
	}
	
	public void rotateY(float radian){
		
	}
	
}
