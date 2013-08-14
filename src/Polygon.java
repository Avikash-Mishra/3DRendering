import java.awt.Color;


public class Polygon {
	private Vector3D Vector3D1;
	private Vector3D Vector3D2;
	private Vector3D Vector3D3;
	private Vector3D VectorLight;
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
		Vector3D1 = vector3d1;
		Vector3D2 = vector3d2;
		Vector3D3 = vector3d3;
		VectorLight = lightSource;
		Red = red;
		Green = green;
		Blue = blue;
		this.color = new Color(red,green,blue);
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
	/**
	 * @return the Vector3D1
	 */
	public Vector3D getVector3D1() {
		return Vector3D1;
	}
	/**
	 * @param Vector3D1 the Vector3D1 to set
	 */
	public void setVector3D1(Vector3D Vector3D1) {
		this.Vector3D1 = Vector3D1;
	}
	/**
	 * @return the Vector3D2
	 */
	public Vector3D getVector3D2() {
		return Vector3D2;
	}
	/**
	 * @param Vector3D2 the Vector3D2 to set
	 */
	public void setVector3D2(Vector3D Vector3D2) {
		this.Vector3D2 = Vector3D2;
	}
	/**
	 * @return the Vector3D3
	 */
	public Vector3D getVector3D3() {
		return Vector3D3;
	}
	/**
	 * @param Vector3D3 the Vector3D3 to set
	 */
	public void setVector3D3(Vector3D Vector3D3) {
		this.Vector3D3 = Vector3D3;
	}
	
	
}
