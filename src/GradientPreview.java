import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GradientPreview extends JPanel{

	Color c1, c2;
    int n = 5;
	
    public GradientPreview() {
       Dimension size = getPreferredSize();
       size.width = 200;
       size.height = 100;
       setPreferredSize(size);
       setBorder(BorderFactory.createTitledBorder("Input Image"));
    }

    public void setNum (int n) {
    	this.n = n;
    }
    public void setColor (Color c) {
    	if (c1 == null) {
    		c1 = c;
    	} else {
    		c2 = c;
    	}
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (c1 == null || c2 == null) return;
        Color[] gradient = ColorUtil.getGradient(c1, c2, n);
        for (int i = 0; i < gradient.length; i++) {
        	g.setColor(gradient[i]);
        	g.fillRect(20 + i * Draw.size, 30, Draw.size, Draw.size); // see javadoc for more info on the parameters  
        }
    }

}