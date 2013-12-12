import java.util.ArrayList;


public class Branch {
	static boolean isLeafNode(Board b, Position p) {
		return b.countNeighbours(p) <= 1;
	}
	
	static Vertex getAncestor(Board b, Position p, Trend t) {
		Direction reverse = t.get(b.get(p)).reverse();
		return b.get(p.move(reverse));
	}
	public static ArrayList<Position> getLeafNode(Board b) {
		ArrayList<Position> res = new ArrayList<Position>();
		for (int i = 0; i < b.BOARD_SIZE; i++) {
			for (int j = 0; j < b.BOARD_SIZE; j++) {
				Position p = new Position(i * Draw.size, j * Draw.size);
				if (b.filled(p) && isLeafNode(b, p)) {
					res.add(p);
				}
			}
		}
		return res;
	}
	static boolean isShortBranch(Board b, Position p, Direction d, Trend t) {
		// reverse the direction
		// traceback the branch
		Direction reverse = d.reverse();
		Vertex ancestor = b.get(p.move(reverse));
		if (ancestor == null) {
			return true;
		} else if (b.filled(p.move(reverse).move(reverse))) {
			return false;
		} else {
			return true;
		}	
	}
}
