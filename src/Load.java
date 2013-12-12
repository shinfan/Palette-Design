import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Load {
	public static ArrayList<Vertex> V = new ArrayList<Vertex> ();
	public static TreeSet<Edge> E;
	public static int colorRange = 8;
	private static void loadText() {
		Scanner s = null;
		try {
			s = new Scanner(new File("input.txt"));
		} catch (Exception e) {
			System.out.println("File not found");
		}
		int id = 0;
		while (s.hasNextLine()) {
			Scanner line = new Scanner(s.nextLine());
			while (line.hasNext()) {
				int r = Integer.parseInt(line.next());
				int g = Integer.parseInt(line.next());
				int b = Integer.parseInt(line.next());
				Color c = new Color(r,g,b);
				V.add(new Vertex(id, c));
				id += 1;
			}
		}
	}
	private static void reset() {
		V = new ArrayList<Vertex> ();
		E = null;
	}
	
	static private class distanceComparator implements Comparator<Edge> {
	    @Override
	    public int compare(Edge a, Edge b) {
	        return a.weight < b.weight ? -1 : a.weight == b.weight ? 0 : 1;
	    }
	}
	
	public static void generateEdges (Setting s) {
		Set<Edge> edges = new HashSet<Edge>();
		for (int i = 0; i < V.size() - 1; i++) {
			for (int j = i + 1; j < V.size(); j++) {
				int id = i * V.size() + j;  
				double cd = ColorUtil.colorDiff(V.get(i).color, V.get(j).color) * s.disWeight;
				double hd = ColorUtil.hueDiff(V.get(i).color, V.get(j).color) * s.hueWeight;
				edges.add(new Edge(id, V.get(i), V.get(j), cd + hd));
			}
		}
		E = new TreeSet<Edge>(new distanceComparator());
		E.addAll(edges);
		
//		for (Edge e : E)
//			System.out.println(e.weight);
	}
	private static Color findDuplicate(Set<Color> C, Color c) {
		for (Color c1 : C) {
			//System.out.println(ColorUtil.colorDiff(c1, c));
			if (ColorUtil.colorDiff(c1, c) < colorRange) 
				return c1;
		}
		return null;
	}
	public static void loadImage(File image) {
		reset();
		BufferedImage img;
		Set<Color> C = new HashSet<Color>();
		HashMap<Color, Integer> count = new HashMap<Color, Integer>();
		try {
			img = ImageIO.read(image);
			
			// retrieve rgb from pixel
			// and merge similar colors
			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					
					int argb = img.getRGB(j, i);
					int r = (argb >> 16) & 0xff;
					int g = (argb >> 8) & 0xff;
					int b = argb & 0xff;
					Color c = new Color(r, g, b);
					
					Color dup = findDuplicate(C, c);
					if (dup == null) {
						C.add(c);
						count.put(c, 1);
					} else {
						count.put(dup, count.get(dup) + 1);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("File not found.");
			System.exit(1);
		}
		
		int id = 0;
		for (Color c : C) {
			if (count.get(c) > 10) {
				// remove noise color
				V.add(new Vertex(id, c));
				id += 1;
			}
		}
		
	}
	
	public static void addColor(Color c) {
		int id = V.size();
		V.add(new Vertex(id, c));
	}
	
	public static void addGradient(Color[] g) {
		int id = V.size();
		for (int i = 0; i < g.length; i++) {
			if (g[i] != null) {
				V.add(new Vertex(id, g[i]));
				id++;
			}
		}
	}
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame f = new MainFrame("Test");
				f.setSize(1000, 600);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		});
	}
	
}
