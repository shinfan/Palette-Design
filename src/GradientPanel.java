import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GradientPanel extends JPanel
implements ChangeListener {

	protected JButton chooseColorBtn;
	protected JButton submitBtn;
	public JLabel sliderLabel;
	public JSlider numColors;
	public GradientPreview gp;

	protected ControlPanel cp;    
	protected GradientPanel self = this;

	public Color c1, c2;


	public void initLayout() {		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 0.5;
		gc.weighty = 1;
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		this.add(sliderLabel, gc);

		// 2 row
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(numColors, gc);


		// 3 row
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(gp, gc);

		// 4rd row
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(chooseColorBtn, gc);

		// 5th row
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 4;
		this.add(submitBtn, gc);

	}


	public void redraw() {
		gp.revalidate();
		gp.repaint();
		this.revalidate();
		this.repaint();
	}


	public void setColor(Color c) {
		if (c1 == null) {
			c1 = c;
		} else {
			c2 = c;
		}

		gp.setColor(c);
		gp.setNum(numColors.getValue());
		redraw();
	}

	public Color[] getGradient() {
		if (c1 == null || c2 ==null) return null;
		int n = numColors.getValue();
		Color[] res = ColorUtil.getGradient(c1, c2, n);
		return res;
	}

	public void initPreview () {
		gp = new GradientPreview();
	}

	public void initSlider () {
		sliderLabel = new JLabel("Number of Colors", JLabel.CENTER);
		numColors = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);
		numColors.setPaintTicks(true);
		numColors.setPaintLabels(true);
		numColors.setMajorTickSpacing(5);
		numColors.setMinorTickSpacing(2);
		Dimension sliderSize =numColors.getPreferredSize();
		sliderSize.width= 200;
		numColors.setPreferredSize(sliderSize);
		numColors.addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				gp.setNum(numColors.getValue());
				redraw();
			}
	
		});
	}
	public void initSubmitBtn () {
		submitBtn = new JButton("Generate Gradient");
		submitBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color[] g = getGradient();
				if (g == null) return;
				Load.addGradient(g);
				cp.updateImage(new ChangeEvent(e));
			}
		});
	}

	public void initChooseColorBtn () {
		chooseColorBtn = new JButton("Choose Color");
		chooseColorBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO-DO
				GradientChooser.createAndShowGUI(self);
			}						
		});
	}


	public GradientPanel(final ControlPanel cp) {
		super(new BorderLayout());
		this.cp = cp;
		//Set up color chooser for setting text color
		initSubmitBtn();
		initSlider();
		initChooseColorBtn();
		initPreview();
		c1 = null;
		c2 = null;
		initLayout();
	}
	static void createAndShowGUI(ControlPanel cp) {
		//Create and set up the window.
		JFrame frame = new JFrame("ColorChooserDemo");

		//Create and set up the content pane.
		JComponent newContentPane = new GradientPanel(cp);
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}
}