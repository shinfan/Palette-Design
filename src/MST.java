import java.awt.Color;
import java.util.*;
import javax.swing.JFrame;


public class MST {

	static final private int LARGE_NUM = 9999999;

	private ArrayList<Vertex> V;
	private Set<Vertex> S;
	private TreeSet<Edge> E;
	HashMap<Vertex, Position> pos;
	public HashMap<Vertex, Integer> branchCount;

	public Board b;
	public Trend t;

	public Position initPos;

	public MST(ArrayList<Vertex> V, TreeSet<Edge> E) {
		this.V = V;
		this.E = E;
		pos = new HashMap<Vertex, Position>();
		branchCount = new HashMap<Vertex, Integer>();

		b = new Board();
		t = new Trend();

		initPos = new Position(300, 300);
	}

	public Direction getDirection (Vertex target, Vertex ancestor, Setting s) {

		// variable init
		Position p = pos.get(ancestor);
		double score[] = new double[4];
		for (int i = 0; i < 4; i++) {
			// basic score
			score[i] = 100;
		}

		// Chain Detection
		if (t.has(ancestor)) {
			if (t.isGoodTrend(b, target, ancestor, p.x, p.y)) {
				// if the direction form a nice chain
				score[t.get(ancestor).index] -= s.trendBonus;
			} else {
				score[t.get(ancestor).index] += s.trendBonus;
			}
		}

		// Space Detection
		if (s.spaceCheck) {
			int[] spaceLeft = SpaceDetect.getSpace(p, b);
			for (int i = 0; i < 4; i++) {
				if (spaceLeft[i] < SpaceDetect.lowerBound) {
					score[i] += SpaceDetect.spacePenalty;
				} else if (spaceLeft[i] > SpaceDetect.upperBound) {
					score[i] -= SpaceDetect.spaceBonus;
				}
			}
		}

		// Average Difference between all its neighbours
		for (int i = 0; i < 4; i++) {
			Direction d = new Direction(i);
			Position nextPos = p.move(d);
			if (b.isValidPos(nextPos)) {
				score[i] += avgDiff(target, nextPos.x, nextPos.y, s);
			} else {
				score[i] = -1.0;
			}
		}

		// Find best direction
		Direction res = new Direction(9999,-1);
		for (int i = 0; i < 4; i++) {
			if (score[i] < res.score && score[i] >= 0) {
				res.index = i;
				res.score = score[i];
			}
		}	
		return res;
	}

	private double avgDiff (Vertex v, int x, int y, Setting s) {
		Position p = new Position(x, y);

		Vertex[] ngbrs = b.getNeighbours(p);
		int res = 0;
		double count = 0;
		for (int i = 0; i < 4; i++) {
			if (ngbrs[i] != null) {
				double cd = ColorUtil.colorDiff(ngbrs[i].color, v.color);
				double hd = ColorUtil.hueDiff(ngbrs[i].color, v.color);
				double minSaturation = (ColorUtil.getSaturation(v.color) + 
						ColorUtil.getSaturation(ngbrs[i].color)) / 2;
				//System.out.println(minSaturation);
				//double diff = cd +  hd * s.hueWeight;
				double diff = (1 - minSaturation) * cd + minSaturation * hd * s.hueWeight;
				res += diff;
				count += 0.8;
			}
		}
		return res / count;
	}

	private Vertex getDarkestVertex() {
		Vertex ret = V.get(0);
		for (Vertex v : V) {
			if (ColorUtil.getBrightness(v) < ColorUtil.getBrightness(ret)) {
				ret = v;
			}
		}
		return ret;
	}

	private void add(Placement p) {

		if (branchCount.containsKey(p.ancestor)) {
			branchCount.put(p.ancestor, branchCount.get(p.ancestor) + 1);
		} else {
			branchCount.put(p.ancestor, 1);
		}
		pos.put(p.vertex, p.position);
		b.set(p.vertex, p.position);
		S.add(p.vertex);
	}

	private void addRoot () {
		Vertex root = getDarkestVertex();
		branchCount.put(root, 0);
		pos.put(root, initPos);
		b.set(root, initPos);
		S.add(root);
	}


	private boolean isValidEdge(Edge e) {
		return S.contains(e.v1) ^ S.contains(e.v2);
	}

	public Placement getNextPlacement(Setting s) {
		Placement res = null;
		for (Edge e : E) {
			if (isValidEdge(e)) {
				Placement tmp = null;
				Direction d;
				if (!S.contains(e.v1) && S.contains(e.v2)) {
					d = getDirection(e.v1, e.v2, s);
					if (d.index >= 0) {
						tmp = new Placement(e.v1, d.score, pos.get(e.v2).move(d), e, d);
					}
				} else {
					d = getDirection(e.v2, e.v1, s);
					if (d.index >= 0) {
						tmp = new Placement(e.v2, d.score, pos.get(e.v1).move(d), e, d);	
					}
				}
				if (tmp != null && (res == null || tmp.score < res.score)) {
					res = tmp;
				}
			}
		}

		t.add(res.vertex, res.direction);
		return res;
	}


	public void generateTree(Setting s) {
		S = new HashSet<Vertex>();

		// add root
		addRoot();

		// get step limit from user settings
		int step = Math.min(V.size(), s.step);

		while (S.size() < step) {	
			Placement nextPlacement = getNextPlacement(s);
			if (nextPlacement == null) { 
				System.out.println("Error: No valid edge found");
				System.exit(1); 
			}
			//nextPlacement.position.print();
			add(nextPlacement);
		}
	}

	public Draw drawTree() {
		Draw d = new Draw(S, pos, t);
		return d;
	}

	public void reset() {
		b.reset();
		t.reset();

		pos.clear();
		branchCount.clear();
	}
}
