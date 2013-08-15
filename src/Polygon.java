import java.awt.Color;
import java.awt.Graphics;



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
	private float[][] matrix;

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
		centre();
		rotateY(-.5f);
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
	
	public void translate(double x,double y, double z){
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
	
	public void centre(){
		int width = 800;
		int height = 800;
		
		vector3DA.x += width/4;
		vector3DA.y += height/4;
		
		vector3DB.x += width/4;
		vector3DB.y += height/4;
		
		vector3DC.x += width/4;
		vector3DC.y += height/4;
		
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
