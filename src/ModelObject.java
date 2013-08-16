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

	public void draw(Graphics g) {
		for (Polygon p : polygons) {
			p.draw(g);
		}
	}

	public void rotateY(float radian) {
		for (Polygon p : polygons) {
			p.rotateY(radian);
			
		}
		centre(800f,800f);
		
	}

	public void rotateZ(float radian) {
		for (Polygon p : polygons) {
			p.rotateZ(radian);
			
		}
		centre(800f,800f);
	}

	public void rotateX(float radian) {
		for (Polygon p : polygons) {
			p.rotateX(radian);
			
		}
		centre(800f,800f);
	}
	
	public void translate(float x,float y,float z) {
		for (Polygon p : polygons) {
			p.translate(x, y, z);
			
		}
		
	}
	
	public void centre(float width, float height){
		float screenCentreWidth = width/2;
		float screenCentreHeight = height/2;
		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
		
		float minY = Float.MAX_VALUE;
		float maxY = Float.MIN_VALUE;
		for(Polygon p : polygons){
			for(Vector3D v : p.getVectors()){
				minX = Math.min(minX,v.x);
				minY = Math.min(minY, v.y);
				maxX = Math.max(maxX, v.x);
				maxY = Math.max(maxY, v.y);
			}
		}
		float imageCentreX = (minX + maxX)/2;
		float imageCentreY = (minY + maxY)/2;
		
		float amountMoveX = screenCentreWidth - imageCentreX;
		float amountMoveY = screenCentreHeight - imageCentreY;
		this.translate(amountMoveX, amountMoveY, 0f);
		
		
			
	}

}
