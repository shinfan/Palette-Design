import java.util.HashMap;


public class Trend {
	HashMap<Vertex, Direction> trend;
	
	public Trend() {
		trend = new HashMap<Vertex, Direction>();
	}
	
	public void reset() {
		trend.clear();
	}
	
	public boolean has(Vertex v) {
		return trend.containsKey(v);
	}
	
	public Direction get(Vertex v) {
		return trend.get(v);
	}
	
	public void add(Vertex v, Direction d) {
		trend.put(v, d);
	}
	
	public boolean isNiceChain (Vertex v, Vertex parent, Vertex grandParent) {
		double range = 2 * ColorUtil.hueDiff(parent.color, grandParent.color);
		return ColorUtil.isSimilarHue(v.color, parent.color, 0.1);
	}
	
	public boolean isGoodTrend (Board b, Vertex v, Vertex parent, int x, int y) {
		if (!trend.containsKey(parent)) return true;
		Direction d = trend.get(parent);
		
		// index of grandParent in board[][]
		Position gpPos = new Position(x, y);
		
		// find index based on the trend
		gpPos.move(d);
		
		if (b.filled(gpPos)) {
			Vertex grandParent = b.get(gpPos);
			return isNiceChain(v, parent, grandParent);
		} else {
			return true;
		}
	}

}
