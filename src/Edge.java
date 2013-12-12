public class Edge {
	public int id;
	public Vertex v1, v2;
	public double weight;
	public Edge(int id, Vertex v1, Vertex v2, double weight) {
		this.id = id;
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}
}

