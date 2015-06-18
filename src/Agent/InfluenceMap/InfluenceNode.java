package Agent.InfluenceMap;


public class InfluenceNode {
	public int x;
	public int y; 
	public int distance; 		//distance from source
	public double value;
	
	public InfluenceNode(int x, int y) {
		this.x = x;
		this.y = y; 
	}
	
	public InfluenceNode(int x, int y, double value) {
		this.x = x;
		this.y = y; 
		this.value = value;
	}
	
	public boolean equals(InfluenceNode node){
		return x == node.x && y == node.y;
	}


	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

}
