import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame {
	Draw treeImage;
	final ControlPanel cp;
	final ImagePanel img;
	
	public MainFrame(String title) {
		super(title);
		treeImage = null;
		
		// set layout manager
		setLayout(new BorderLayout());
		
		// create Swing component
		cp = new ControlPanel();
		img = new ImagePanel(cp.file);
		
		cp.addControlListener(new ControlListener () {
			public void controlEventOccured(ControlEvent e) {
				update(e.setting);
			}
		});	

		Container c = getContentPane();
		c.add(cp, BorderLayout.WEST);	
		c.add(img, BorderLayout.EAST);
	}
	
	public void update(Setting s) {
		reDraw(s);
		img.update(cp.file);
		img.revalidate();
		img.repaint();
	}
	
	private void reDraw(Setting s) {
		if (treeImage!=null) {
			this.remove(treeImage);
		}
		Load.generateEdges(s);
		MST t = new MST(Load.V, Load.E);
		t.generateTree(s);
		PostProcesser postp = new PostProcesser(t.b, t.t, t.pos);
		if (s.shortBranch == true) {
			postp.removeShortBranches();
		}
		treeImage = t.drawTree();
		this.add(treeImage);
		this.validate();
		this.repaint();
		
		// debug
		Debugger d = new Debugger(t.b, t.pos, t.t);
		d.printVertex(18);
		d.printVertex(17);


	}

}
