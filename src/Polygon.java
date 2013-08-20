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
	private Transform transform;
	private List<EdgeOfPolygon> polygonEdges;
	/**
	 * @param vector3d1
	 * @param vector3d2
	 * @param vector3d3
	 * @param red
	 * @param green
	 * @param blue
	 * @param color
	 */
	public Polygon(Vector3D lightSource, Vector3D vector3d1, Vector3D vector3d2, Vector3D vector3d3,
			int red, int green, int blue) {
		super();
		vector3DA = vector3d1;
		vector3DB = vector3d2;
		vector3DC = vector3d3;
		vectorLight = lightSource;
		Red = red;
		Green = green;
		Blue = blue;
		this.color = new Color(red,green,blue);
		this.polygonEdges = new ArrayList<EdgeOfPolygon>();
		polygonEdges.add(new EdgeOfPolygon(vector3d1,vector3d2));
		polygonEdges.add(new EdgeOfPolygon(vector3d2,vector3d3));
		polygonEdges.add(new EdgeOfPolygon(vector3d3,vector3d1));
		
		
		
	}
	
	public double[][] getEdgeList(){
		//Inialise min and max and thier extreme values
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		//loop to get the minY and maxY
		for(Vector3D vector : getVectors()){
			minY = Math.min(minY, (int)Math.round(vector.y));
			maxY = Math.max(maxY, (int)Math.round(vector.y));
			
		}
		
		//set all elements in the array to POSITIVE_INFINITY
		double[][] edgeList = new double[maxY-minY+1][4];
		for(int i = 0; i < edgeList.length; i++){
			edgeList[i][0] = Double.POSITIVE_INFINITY;
			edgeList[i][1] = Double.POSITIVE_INFINITY;
			edgeList[i][2] = Double.POSITIVE_INFINITY;
			edgeList[i][3] = Double.POSITIVE_INFINITY;
		}
		
		for(EdgeOfPolygon edge : this.polygonEdges){
			Vector3D vector1 = edge.getVector1();
			Vector3D vector2 = edge.getVector2();
			
			if(vector2.y < vector1.y){
				Vector3D tempVector = vector2;
				vector2 = vector1;
				vector1 = tempVector;
			}
			
			double xGradient = ((vector2.x - vector1.x)/(vector2.y - vector1.y));
			double zGradient = ((vector2.z - vector1.z)/(vector2.y - vector1.y));
			double currentX = vector1.x;
			double currentZ = vector2.z;
			
			int start = (int) (Math.round(vector1.y)-minY);
			int end = (int) (Math.round(vector2.y)-minY);
			
			for(int i = start; i < end; i++){
				if(currentX < edgeList[i][0]){
					edgeList[i][0] = currentX;
					edgeList[i][1] = currentZ;
				}
				
				if(currentX > edgeList[i][2]){
					edgeList[i][2] = currentX;
					edgeList[i][1]  = currentZ;
				}
				
				currentX += xGradient;
				currentZ += zGradient;
			}
			
			int edgeListPos = (int)(Math.round(vector2.y)- minY);
			if(vector2.x < edgeList[edgeListPos][0]){
				edgeList[edgeListPos][0] = vector2.x;
				edgeList[edgeListPos][1] = vector2.z;
			}
			if(vector2.x > edgeList[edgeListPos][2]){
				edgeList[edgeListPos][2] = vector2.x;
				edgeList[edgeListPos][3] = vector2.z;
			}
			
		}
		return edgeList;
	}
	
	public Vector3D getNormal(){
		float ax = vector3DB.x - vector3DA.x;
		float ay = vector3DB.y - vector3DA.y;
		float az = vector3DB.z - vector3DA.z;
		float bx = vector3DC.x - vector3DB.x;
		float by = vector3DC.y - vector3DB.y;
		float bz = vector3DC.z - vector3DB.z;
		float x = (ay*bz - az*by);
		float y = (az*bx - ax*bz);
		float z = (ax*by - ay*bx);
		return new Vector3D(x, y, z);
	}
	
	public Vector3D convertUnitVector(Vector3D v){
		float vectorLength = (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2)+Math.pow(v.z, 2));
		return new Vector3D(v.x/vectorLength,v.y/vectorLength,v.z/vectorLength);
	}

	public void rotateY(float radians){
		transform = Transform.newYRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);
		
	}
	
	public void rotateX(float radians){
		transform = Transform.newXRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);
		
	}
	
	public void rotateZ(float radians){
		transform = Transform.newZRotation(radians);
		vector3DA = transform.multiply(vector3DA);
		vector3DB = transform.multiply(vector3DB);
		vector3DC = transform.multiply(vector3DC);
		
	}
	
	public void translate(float x,float y, float z){
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
	
	public ArrayList<Vector3D> getVectors(){
		ArrayList<Vector3D> toReturn = new ArrayList<Vector3D>();
		toReturn.add(vector3DA);
		toReturn.add(vector3DB);
		toReturn.add(vector3DC);
		return toReturn;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		java.awt.Polygon p = new java.awt.Polygon();
		p.addPoint((int)vector3DA.x,(int)vector3DA.y);
		p.addPoint((int)vector3DB.x,(int)vector3DB.y);
		p.addPoint((int)vector3DC.x,(int)vector3DC.y);
		g.fillPolygon(p);
		
	}
	/**
	 * @return the vector3DA
	 */
	public Vector3D getVector3DA() {
		return vector3DA;
	}


	/**
	 * @param vector3da the vector3DA to set
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
	 * @param vector3db the vector3DB to set
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
	 * @param vector3dc the vector3DC to set
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
	 * @param vectorLight the vectorLight to set
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
	 * @param red the red to set
	 */
	public void setRed(int red) {
		Red = red;
		this.color = new Color(Red,Green,Blue);
	}
	/**
	 * @return the green
	 */
	public int getGreen() {
		return Green;
	}
	/**
	 * @param green the green to set
	 */
	public void setGreen(int green) {
		Green = green;
		this.color = new Color(Red,Green,Blue);
	}
	/**
	 * @return the blue
	 */
	public int getBlue() {
		return Blue;
	}
	/**
	 * @param blue the blue to set
	 */
	public void setBlue(int blue) {
		Blue = blue;
		this.color = new Color(Red,Green,Blue);
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
}
