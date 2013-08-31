import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Polygon {
	private Vector3D vector3DA;
	private Vector3D vector3DB;
	private Vector3D vector3DC;
	private Vector3D vectorLight;
	private int Red;
	private int Green;
	private int Blue;
	private Color color;
	private Color reflectivity;
	private Transform transform;
	private List<EdgeOfPolygon> polygonEdges;
	boolean ifFacingScreen = false;
	private Vector3D normalVector;
	private Bounds bounds;

	/**
	 * @param vector3d1
	 * @param vector3d2
	 * @param vector3d3
	 * @param red
	 * @param green
	 * @param blue
	 * @param color
	 */
	public Polygon(Vector3D lightSource, Vector3D vector3d1,
			Vector3D vector3d2, Vector3D vector3d3, int red, int green, int blue) {
		super();
		vector3DA = vector3d1;
		vector3DB = vector3d2;
		vector3DC = vector3d3;
		vectorLight = lightSource;
		Red = red;
		Green = green;
		Blue = blue;
		this.color = new Color(red, green, blue);
		this.reflectivity = new Color(red, green, blue);
		this.polygonEdges = new ArrayList<EdgeOfPolygon>();
		polygonEdges.add(new EdgeOfPolygon(vector3d1, vector3d2));
		polygonEdges.add(new EdgeOfPolygon(vector3d2, vector3d3));
		polygonEdges.add(new EdgeOfPolygon(vector3d3, vector3d1));
		calculateNormal();

	}

	public void shading(LightSource ambient, LightSource intensity) {
		float cost = convertUnitVector(normalVector).dotProduct(
				convertUnitVector(vectorLight));

		int newRed = (int) ((ambient.getRed() + intensity.getRed() * cost) * this.Red);
		int newGreen = (int) ((ambient.getGreen() + intensity.getGreen() * cost) * this.Green);
		int newBlue = (int) ((ambient.getBlue() + intensity.getBlue() * cost) * this.Blue);

		if (newRed < 0) {
			newRed = 0;
		} else if (newRed > 255) {
			newRed = 255;
		}
		
		if (newGreen < 0) {
			newGreen = 0;
		} else if (newGreen > 255) {
			newGreen = 255;
		}
		
		if (newBlue < 0) {
			newBlue = 0;
		} else if (newBlue > 255) {
			newBlue = 255;
		}
		
		this.color = new Color(newRed,newGreen,newBlue);

	}

	private Vector3D convertUnitVector(Vector3D v) {
		float length = (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2)
				+ Math.pow(v.z, 2));
		return new Vector3D(v.x / length, v.y / length, v.z / length);
	}

	public void calculateNormal() {
		normalVector = ((vector3DB.minus(vector3DA)).crossProduct(vector3DC
				.minus(vector3DB))).unitVector();
		if (normalVector.z > 0)
			ifFacingScreen = false;
		else
			ifFacingScreen = true;
	}
	
	private Bounds bounds() {
		float minX = Math.min(Math.min(vector3DA.x,vector3DB.x),vector3DC.x);
		float minY = Math.min(Math.min(vector3DA.y, vector3DB.y),vector3DC.y);
		float maxX = Math.max(Math.max(vector3DA.x, vector3DB.x),vector3DC.x);
		float maxY = Math.max(Math.max(vector3DA.y, vector3DB.y),vector3DC.y);
		bounds = new Bounds(minX, minY, (maxX - minX), (maxY - minY));
		return bounds;
	}
	public int getMinY(){
		return Math.round(bounds.getY());
	}
	public EdgeList[] edgeList() {
		bounds();
		EdgeList[] e = new EdgeList[(int) (bounds.getHeight() + 1)];
		Vector3D[] vectorArray = new Vector3D[3];
		vectorArray[0] = this.vector3DA;
		vectorArray[1] = this.vector3DB;
		vectorArray[2] = this.vector3DC;
		for(int i = 0; i<3; i++){
			Vector3D vectorA = vectorArray[i];
			Vector3D vectorB = vectorArray[(i+1)%3];
			if(vectorA.y > vectorB.y){
				vectorB = vectorA;
				vectorA = vectorArray[(i+1)%3];
			}
			float mx = (vectorB.x - vectorA.x)/(vectorB.y - vectorA.y);
			float mz = (vectorB.z - vectorA.z)/(vectorB.z - vectorA.z);
			float x = vectorA.x;
			float z = vectorA.z;

			int diffY = Math.round(vectorA.y)- Math.round(bounds.getY());
			int maxj = Math.round(vectorB.y) - Math.round(bounds.getY());

			while(diffY < maxj){
				if(e[diffY] == null){
					e[diffY] = new EdgeList(x, z);
				} else{
					e[diffY].add(x, z);

				}
				diffY++;
				x += mx;
				z += mz;
			}

		}
		return e;

	}

//	public float[][] getEdgeList() {
//		// Inialise min and max and thier extreme values
//		int minY = Integer.MAX_VALUE;
//		int maxY = Integer.MIN_VALUE;
//
//		// loop to get the minY and maxY
//		for (Vector3D vector : getVectors()) {
//			minY = Math.min(minY, (int) Math.round(vector.y));
//			maxY = Math.max(maxY, (int) Math.round(vector.y));
//
//		}
//
//		// set all elements in the array to POSITIVE_INFINITY
//		float[][] edgeList = new float[maxY - minY + 1][4];
//		for (int i = 0; i < edgeList.length; i++) {
//			edgeList[i][0] = Float.MAX_VALUE;
//			edgeList[i][1] = Float.MAX_VALUE;
//			edgeList[i][2] = Float.MAX_VALUE;
//			edgeList[i][3] = Float.MAX_VALUE;
//		}
//
//		for (EdgeOfPolygon edge : this.polygonEdges) {
//			Vector3D vector1 = edge.getVector1();
//			Vector3D vector2 = edge.getVector2();
//
//			if (vector2.y < vector1.y) {
//				Vector3D tempVector = vector2;
//				vector2 = vector1;
//				vector1 = tempVector;
//			}
//
//			float xGradient = ((vector2.x - vector1.x) / (vector2.y - vector1.y));
//			float zGradient = ((vector2.z - vector1.z) / (vector2.y - vector1.y));
//			float currentX = vector1.x;
//			float currentZ = vector2.z;
//
//			int start = (int) (Math.round(vector1.y) - minY);
//			int end = (int) (Math.round(vector2.y) - minY);
//
//			for (int i = start; i < end; i++) {
//				if (currentX < edgeList[i][0]) {
//					edgeList[i][0] = currentX;
//					edgeList[i][1] = currentZ;
//				}
//
//				if (currentX > edgeList[i][2]) {
//					edgeList[i][2] = currentX;
//					edgeList[i][1] = currentZ;
//				}
//
//				currentX += xGradient;
//				currentZ += zGradient;
//			}
//
//			int edgeListPos = (int) (Math.round(vector2.y) - minY);
//			if (vector2.x < edgeList[edgeListPos][0]) {
//				edgeList[edgeListPos][0] = vector2.x;
//				edgeList[edgeListPos][1] = vector2.z;
//			}
//			if (vector2.x > edgeList[edgeListPos][2]) {
//				edgeList[edgeListPos][2] = vector2.x;
//				edgeList[edgeListPos][3] = vector2.z;
//			}
//
//		}
//		return edgeList;
//	}

	public Vector3D getNormal() {
		float ax = vector3DB.x - vector3DA.x;
		float ay = vector3DB.y - vector3DA.y;
		float az = vector3DB.z - vector3DA.z;
		float bx = vector3DC.x - vector3DB.x;
		float by = vector3DC.y - vector3DB.y;
		float bz = vector3DC.z - vector3DB.z;
		float x = (ay * bz - az * by);
		float y = (az * bx - ax * bz);
		float z = (ax * by - ay * bx);
		return new Vector3D(x, y, z);
	}

	public void rotateY(float radians) {
		transform = Transform.newYRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);
		//rotateZ(radians);

	}

	public void rotateX(float radians) {
		transform = Transform.newXRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);
		//rotateZ(radians);

	}

	public void rotateZ(float radians) {
		transform = Transform.newZRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);

	}

	public void translate(float x, float y, float z) {
		vector3DA.x += x;
		vector3DA.y += y;
		vector3DA.z += z;

		vector3DB.x += x;
		vector3DB.y += y;
		vector3DB.z += z;

		vector3DC.x += x;
		vector3DC.y += y;
		vector3DC.z += z;
	}

	public ArrayList<Vector3D> getVectors() {
		ArrayList<Vector3D> toReturn = new ArrayList<Vector3D>();
		toReturn.add(vector3DA);
		toReturn.add(vector3DB);
		toReturn.add(vector3DC);
		return toReturn;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		java.awt.Polygon p = new java.awt.Polygon();
		p.addPoint((int) vector3DA.x, (int) vector3DA.y);
		p.addPoint((int) vector3DB.x, (int) vector3DB.y);
		p.addPoint((int) vector3DC.x, (int) vector3DC.y);
		g.fillPolygon(p);

	}

	/**
	 * @return the vector3DA
	 */
	public Vector3D getVector3DA() {
		return vector3DA;
	}

	/**
	 * @param vector3da
	 *            the vector3DA to set
	 */
	public void setVector3DA(Vector3D vector3da) {
		vector3DA = vector3da;
	}

	/**
	 * @return the vector3DB
	 */
	public Vector3D getVector3DB() {
		return vector3DB;
	}

	/**
	 * @param vector3db
	 *            the vector3DB to set
	 */
	public void setVector3DB(Vector3D vector3db) {
		vector3DB = vector3db;
	}

	/**
	 * @return the vector3DC
	 */
	public Vector3D getVector3DC() {
		return vector3DC;
	}

	/**
	 * @param vector3dc
	 *            the vector3DC to set
	 */
	public void setVector3DC(Vector3D vector3dc) {
		vector3DC = vector3dc;
	}

	/**
	 * @return the vectorLight
	 */
	public Vector3D getVectorLight() {
		return vectorLight;
	}

	/**
	 * @param vectorLight
	 *            the vectorLight to set
	 */
	public void setVectorLight(Vector3D vectorLight) {
		this.vectorLight = vectorLight;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return Red;
	}

	/**
	 * @param red
	 *            the red to set
	 */
	public void setRed(int red) {
		Red = red;
		this.color = new Color(Red, Green, Blue);
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return Green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen(int green) {
		Green = green;
		this.color = new Color(Red, Green, Blue);
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return Blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(int blue) {
		Blue = blue;
		this.color = new Color(Red, Green, Blue);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
