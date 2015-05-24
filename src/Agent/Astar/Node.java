package Agent.Astar;






public class Node {
	Node parent; 
	int x;
	int y; 
	double g; 
	double  f;

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
