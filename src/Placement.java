
public class Placement {
	public Vertex vertex;
	public double score;
	public Position position;
	public Edge edge;
	public Vertex ancestor;
	public Direction direction;
	
	public Placement() {
		score = 9999;
	}
	
	public Placement(Vertex v, double s, Position p, Direction d) {
		vertex = v;
		score = s;
		position = p;
		direction = d;
	}
	public Placement(Vertex v, double s, Position p, Edge e, Direction d) {
		vertex = v;
		score = s;
		position = p;
		edge = e;
		direction = d;
		if (e.v1 == v) {
			ancestor = e.v2;
		} else {
			ancestor = e.v1;
		}
	}
}
