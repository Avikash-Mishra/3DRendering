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
	}
	
	public void rotateY(){
		
	}
	public void draw(Graphics g){
		g.setColor(color);
		
		
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
