package pkg;

public class Node {
	public int number;
	public boolean visited;
	public double distance;
	public int numNodes;
	
	public Node(int number, boolean visited){
		this.number = number;
		this.visited = visited;
		this.distance = Double.MAX_VALUE;
		this.numNodes = 0;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Node && ((Node)obj).number == this.number;
	}
}
