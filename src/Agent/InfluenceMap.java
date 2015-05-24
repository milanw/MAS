package Agent;

public class InfluenceMap {
	private int width;
	private int height; 
	private double[][] map; 

	public InfluenceMap(int width, int height) {
		this.width = width;
		this.height = height; 
		this.map = new double[height][width];
	}
}
