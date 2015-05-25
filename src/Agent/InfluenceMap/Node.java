package Agent.InfluenceMap;


public class Node {
	int x;
	int y; 
	int distance; 		//distance from source
	double value;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y; 
	}
	
	public boolean equals(Node node){
		return x == node.x && y == node.y;
	}


	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

}
