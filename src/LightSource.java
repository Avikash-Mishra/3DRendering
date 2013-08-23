
public class LightSource {
	private float red;
	private float green;
	private float blue;
	
//TODO add light source class to code	
	
	public LightSource(float red, float green, float blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public void increase(){
		float r = red + 0.1f;
		float g = green + 0.1f;
		float b = blue + 0.1f;
		
		if(r <= 1){
			this.red = r;
		}
		
		if(g <= 1){
			this.green = g;
		}
		
		if(b <= 1){
			this.blue = b;
		}
	}
	
	public void decrease(){
		float r = red - 0.1f;
		float g = green - 0.1f;
		float b = blue - 0.1f;
		
		if(r >= 0){
			this.red = r;
		}
		
		if(g >= 0){
			this.green = g;
		}
		
		if(b >= 0){
			this.blue = b;
		}
	}
	
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
}
