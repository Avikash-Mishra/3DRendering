/**Represents a edge of a polygon*/
public class EdgeOfPolygon {
	private Vector3D Vector1;
	private Vector3D Vector2;
	/**
	 * @param vector1
	 * @param vector2
	 */
	public EdgeOfPolygon(Vector3D vector1, Vector3D vector2) {
		super();
		this.Vector1 = vector1;
		this.Vector2 = vector2;
	}
	/**
	 * @return the vector1
	 */
	public Vector3D getVector1() {
		return Vector1;
	}
	/**
	 * @param vector1 the vector1 to set
	 */
	public void setVector1(Vector3D vector1) {
		Vector1 = vector1;
	}
	/**
	 * @return the vector2
	 */
	public Vector3D getVector2() {
		return Vector2;
	}
	/**
	 * @param vector2 the vector2 to set
	 */
	public void setVector2(Vector3D vector2) {
		Vector2 = vector2;
	}
	
	
}
