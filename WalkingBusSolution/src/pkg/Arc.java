package pkg;

public class Arc{
	public double distance;
	public double danger;
	public int node1;
	public int node2;
	
	public Arc(int node1, int node2, double distance, double danger){
		this.distance = distance;
		this.danger = danger;
		this.node1 = node1;
		this.node2 = node2;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj instanceof Arc &&
				((Arc)obj).node1 == this.node1 &&
				((Arc)obj).node2 == this.node2 &&
				((Arc)obj).danger == this.danger &&
				((Arc)obj).distance == this.distance;
	}
}
