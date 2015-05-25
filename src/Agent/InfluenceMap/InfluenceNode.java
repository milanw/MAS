package Agent.InfluenceMap;


public class InfluenceNode {
	int x;
	int y; 
	int distance; 		//distance from source
	double value;
	
	public InfluenceNode(int x, int y) {
		this.x = x;
		this.y = y; 
	}
	
	public boolean equals(InfluenceNode node){
		return x == node.x && y == node.y;
	}


	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

}
