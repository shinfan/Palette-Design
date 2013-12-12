import java.awt.*;
import javax.swing.*;
import java.util.*;


public class Draw extends JPanel {
	private Set<Vertex> V;
	private HashMap<Vertex, Position> pos;
	private Trend t;

	public static int size = 25;
	public Draw(Set<Vertex> V, HashMap<Vertex, Position> pos, Trend t) {
		this.V = V;
		this.pos = pos;
		this.t = t;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Vertex v : V) {
			if (pos.containsKey(v)) {	
				g.setColor(v.color);
				Position p = pos.get(v);
				g.fillRect(p.x, p.y, size, size);
				g.setColor(Color.black);
//				g.drawString(String.valueOf(v.id), p.x + 5 , p.y + 10);
			}
		}
	}
}
