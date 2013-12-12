import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public void update(File img) {
    	try {
			this.image = resize(ImageIO.read(img));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static BufferedImage getBufferedImage(Image img){ 
        if( img == null ) return null; 
        int w = img.getWidth(null); 
        int h = img.getHeight(null); 
        // draw original image to thumbnail image object and 
        // scale it to the new size on-the-fly 
        BufferedImage bufimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
        Graphics2D g2 = bufimg.createGraphics(); 
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2.drawImage(img, 0, 0, w, h, null); 
        g2.dispose(); 
        return bufimg; 
    }
    
    public BufferedImage resize(BufferedImage img) {
    	double height = (double)img.getHeight() * (160 / (double)img.getWidth());
        return getBufferedImage(img.getScaledInstance(160, (int)height, Image.SCALE_SMOOTH));
    }
    
    public ImagePanel(File img) {
       try {                
          this.image = resize(ImageIO.read(img));
          
       } catch (IOException ex) {
            // handle exception...
       }
       Dimension size = getPreferredSize();
       size.width = 200;
       setPreferredSize(size);
       setBorder(BorderFactory.createTitledBorder("Input Image"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 20, 20, null); // see javadoc for more info on the parameters            
    }

}