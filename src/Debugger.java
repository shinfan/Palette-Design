import java.util.HashMap;


public class Debugger {
	Board b;
	HashMap<Vertex, Position> pos;
	public Trend t;
	public Debugger(Board b, HashMap<Vertex, Position> pos, Trend t) {
		this.b = b;
		this.pos = pos;
		this.t = t;
	}
	
	public Vertex getVertex(int id) {
		for (int i = 0; i < b.BOARD_SIZE; i++) {
			for (int j = 0; j < b.BOARD_SIZE; j++) {
				Position p = new Position(i * Draw.size, j * Draw.size);
				if (b.get(p) != null && b.get(p).id == id) {
					return b.get(p);
				}
			}
		}
		
		System.out.println("Vertex cannot be found!");
		//System.exit(-1);
		return null;
	}
	
	public void printDiff(int id1, int id2) {
		Vertex v1 = getVertex(id1);
		Vertex v2 = getVertex(id2);
		System.out.println("Color diff between vertex" + id1 + " and " + id2 + " is " + ColorUtil.colorDiff(v1.color, v2.color));
	}
	
	public void printVertex(int id) {
		Vertex v = getVertex(id);
		if (v == null) return;
		System.out.println("Vertex " + id + ":");
		System.out.println("is leaf: " + Branch.isLeafNode(b, pos.get(v)));
		System.out.println("trend: " + t.get(v).index);
		System.out.println("ancestor: " + b.get(pos.get(v).move(t.get(v).reverse())).id);
	}
}
