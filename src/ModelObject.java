import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ModelObject {
	private ArrayList<Polygon> polygons;
	private Vector3D lightSource;
	private float[][] zBuffer;
	private Color[][] colors;

	/**
	 * @param polygons
	 */
	public ModelObject(ArrayList<Polygon> polygons, Vector3D light) {
		super();
		this.polygons = polygons;
		this.lightSource = light;
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
				EdgeList[] edgelist = p.edgeList();

				int miny = p.getMinY();
				Color c = p.getColor();
				for (int i = 0; i < edgelist.length && edgelist[i] != null; i++) {
					int cury = miny + i;
					int x = Math.round(edgelist[i].getLeftX());
					float z = edgelist[i].getLeftZ();

					int gradientz = Math
							.round((edgelist[i].getRightZ() - edgelist[i]
									.getLeftZ())
									/ (edgelist[i].getRightX() - edgelist[i]
											.getLeftX()));
					while (x <= edgelist[i].getRightX()) {
						if (x < GUI.FrameWidth && cury < GUI.FrameHeight) {
							if (z < zBuffer[x][cury]) {
								zBuffer[x][cury] = z;
								colors[x][cury] = p.shading(Main.ambient, Main.intensity);
							}
						}
						x++;
						z += gradientz;
					}

				}
			}
		}
		
		Main.image = Main.convertToImage(colors);
		
//		for (int i = 0; i < colors.length; i++) {
//			for (int j = 0; j < colors[i].length; j++) {
//				if (colors[i][j] != null) {
//					g.setColor(colors[i][j]);
//					g.fillRect(i, j, 1, 1);
//				}
//			}
//		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Main.image, null, 0,0);
		
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

	public void rotateY(float radian) {
		for (Polygon p : polygons) {
			p.rotateY(radian);

		}
		centre(GUI.FrameWidth, GUI.FrameHeight);

	}

	public void rotateZ(float radian) {
		for (Polygon p : polygons) {
			p.rotateZ(radian);

		}
		centre(GUI.FrameWidth, GUI.FrameHeight);
	}

	public void rotateX(float radian) {
		for (Polygon p : polygons) {
			p.rotateX(radian);

		}
		centre(GUI.FrameWidth, GUI.FrameHeight);
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
