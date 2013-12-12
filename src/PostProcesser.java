import java.util.ArrayList;
import java.util.HashMap;


public class PostProcesser {
	Board board;
	Trend trend;
	HashMap<Vertex, Position> pos;
	public PostProcesser(Board b, Trend t, HashMap<Vertex, Position> pos) {
		this.board = b;
		this.trend = t;
		this.pos = pos;
	}

	public Placement findPosition (ArrayList<Position> candidates, Vertex v, Position oldPos) {
		Vertex ancestor = Branch.getAncestor(board, oldPos, trend);
		Placement pl = new Placement();

		
		if (ancestor != null && board.countNeighbours(pos.get(ancestor)) == 2) {
			pl = new Placement(v, ColorUtil.colorDiff(v.color, ancestor.color),
					pos.get(ancestor).move(trend.get(ancestor)), trend.get(ancestor));
			return pl;
		}
		
		for (int i = 0; i < candidates.size(); i++) {
			Vertex leaf = board.get(candidates.get(i));
			Direction d = trend.get(leaf);
			if (d == null) continue;
			Position p = candidates.get(i).move(trend.get(leaf));
			double diff = ColorUtil.colorDiff(leaf.color, v.color);
			if (diff < pl.score) {
				pl = new Placement(v, diff, p,trend.get(leaf));
			}
		}
		return pl;
	}
	
	
	public void update(Vertex v, Placement pl, Position oldPos) {
		System.out.println("move " + v.id + "from " + oldPos.x + " " + oldPos.y + 
				" to "+pl.position.x + " " + pl.position.y);
		board.set(v, pl.position);
		trend.add(v, pl.direction);
		pos.put(v, pl.position);
		board.remove(oldPos);
	}
	public void removeShortBranches() {
		ArrayList<Position> candidates = Branch.getLeafNode(board);
//		System.out.println("Leaf nodes");
//		for (int i = 0; i < candidates.size(); i++)
//			System.out.println(board.get(candidates.get(i)).id);
		boolean flag = false;
		while (!flag) {
			// loop until all the short branches are eliminated
			flag = true;
			for (int i = 0; i < candidates.size(); i++) {
				Position p = candidates.get(i);
				Vertex v = board.get(p);
				Direction d = trend.get(v);
				if (d == null) {
					// root
					continue;
				}
				if (Branch.isShortBranch(board, p, d, trend)) {
					// do something here
					flag = false;	
					candidates.remove(i);
					Placement pl = findPosition(candidates, v, p);
					update(v, pl, p);
					break;
				}
			}
		}
	}
}
