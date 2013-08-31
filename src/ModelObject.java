import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ModelObject {
	private ArrayList<Polygon> polygons;
	private Vector3D lightSource;
	private LightSource ambient;
	private LightSource intensity;
	private float[][] zBuffer;

	private Color[][] colors;

	/**
	 * @param polygons
	 */
	public ModelObject(ArrayList<Polygon> polygons, Vector3D light,
			LightSource amb, LightSource inten) {
		super();
		this.polygons = polygons;
		this.lightSource = light;
		this.ambient = amb;
		this.intensity = inten;
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
		resetColors();
		calculateNormal();
		for (Polygon p : polygons) {
			if (p.ifFacingScreen) {
				p.shading(ambient, intensity);
				EdgeList[] edgelist = p.edgeList();

				int miny = p.getMinY();
				Color c = p.getColor();
				for (int i = 0; i < edgelist.length && edgelist[i] != null; i++) {
					int cury = miny + i;
					int x = Math.round(edgelist[i].getLeftX());
					int z = Math.round(edgelist[i].getLeftZ());

					int gradientz = Math
							.round((edgelist[i].getRightZ() - edgelist[i]
									.getRightZ())
									/ (edgelist[i].getRightX() - edgelist[i]
											.getLeftX()));
					while (x <= edgelist[i].getRightX()) {
						if (x < GUI.FrameWidth && cury < GUI.FrameHeight) {
							if (z < zBuffer[x][cury]) {
								zBuffer[x][cury] = z;
								colors[x][cury] = c;
							}
						}
						x++;
						z += gradientz;
					}

				}
			}
		}

		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				if (colors[i][j] != null) {
					g.setColor(colors[i][j]);
					g.fillRect(i, j, 1, 1);
				}
			}
		}

		/*
		 * for (Polygon p : polygons) { p.draw(g); }
		 */
	}

	private void resetColors() {
		this.colors = new Color[GUI.FrameWidth][GUI.FrameHeight];
		zBuffer = new float[GUI.FrameWidth][GUI.FrameHeight];
		for (int i = 0; i < zBuffer.length; i++) {
			for (int j = 0; j < zBuffer[i].length; j++) {
				zBuffer[i][i] = Float.MAX_VALUE;
			}
		}
	}

	private void calculateNormal() {
		for (Polygon p : polygons) {
			p.calculateNormal();
		}
	}

	// public BufferedImage draw(){
	// Iterator<Polygon> poly = polygon.iterator();
	//
	// while (poly.hasNext()) {
	// Polygon p = poly.next();
	// //only draws the polygons that are facing the user
	// if (p.getDraw()) {
	// EdgeList[] edgelist = p.getEdgeList();
	// int minY = p.getMinY();
	// Color c = p.shading(lightSource, ambientLight);//compute the shading
	//
	// for (int i = 0; i < edgelist.length-1 && edgelist[i]!=null ; i++) {
	// int y = minY+i;//go down each row
	// //System.out.println(y);
	// //System.out.println(edgelist[i].getLeftX());
	// int x = Math.round(edgelist[i].getLeftX());
	// int z = Math.round(edgelist[i].getLeftZ());
	//
	// int mz = Math.round((edgelist[i].getRightZ() - edgelist[i].getLeftZ())
	// / (edgelist[i].getRightX() - edgelist[i].getLeftX()));
	//
	// while (x <= Math.round(edgelist[i].getRightX())) {
	// //System.out.println("left x: "+ x
	// +"right x :"+Math.round(edgelist[i].getRightX()));
	// if (z < zBuffer[x][y]) {
	// zBuffer[x][y] = z;
	// screen[x][y] = c;
	// }
	// x++;
	// z += mz;
	// }
	// }
	// }
	// }
	// return convertToImage(screen);
	// }

	public void rotateY(float radian) {
		for (Polygon p : polygons) {
			p.rotateY(radian);

		}
		centre(800f, 800f);

	}

	public void rotateZ(float radian) {
		for (Polygon p : polygons) {
			p.rotateZ(radian);

		}
		centre(800f, 800f);
	}

	public void rotateX(float radian) {
		for (Polygon p : polygons) {
			p.rotateX(radian);

		}
		centre(800f, 800f);
	}

	public void translate(float x, float y, float z) {
		for (Polygon p : polygons) {
			p.translate(x, y, z);

		}

	}

	public void centre(float width, float height) {
		float screenCentreWidth = width / 2;
		float screenCentreHeight = height / 2;
		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;

		float minY = Float.MAX_VALUE;
		float maxY = Float.MIN_VALUE;
		for (Polygon p : polygons) {
			for (Vector3D v : p.getVectors()) {
				minX = Math.min(minX, v.x);
				minY = Math.min(minY, v.y);
				maxX = Math.max(maxX, v.x);
				maxY = Math.max(maxY, v.y);
			}
		}
		float imageCentreX = (minX + maxX) / 2;
		float imageCentreY = (minY + maxY) / 2;
		float diffX = maxX - minX;
		float diffY = maxY - minY;
		if (diffX > width || diffY > height) {
			float scale = Math.min(height / diffY, width / diffX);
			for (Polygon p : polygons) {
				for (Vector3D v : p.getVectors()) {
					v.x *= scale;
					v.y *= scale;
					v.z *= scale;
				}
			}
			lightSource.x *= scale;
			lightSource.y *= scale;
			lightSource.z *= scale;
			centre(width, height);
			return;
		}
		float amountMoveX = screenCentreWidth - imageCentreX;
		float amountMoveY = screenCentreHeight - imageCentreY;
		this.translate(amountMoveX, amountMoveY, 0f);
		lightSource.x += amountMoveX;
		lightSource.y += amountMoveY;

	}

}
