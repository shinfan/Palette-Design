
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;


public class ControlPanel extends JPanel {

	// event listeners
	private EventListenerList listenerList = new EventListenerList();

	File file;
	// components
	public JSlider weight;
	public JSlider step;
	public JSlider trend;
	public JButton fileSelectBtn;
	public JButton genBtn;
	public JButton chooseColorBtn;
	public JButton gradientBtn;

	public JLabel sliderLabel;
	public JLabel stepLabel;
	public JLabel trendLabel;
	public JCheckBox spaceCheck = new JCheckBox("Enable Space Detect");
	public JCheckBox shortBranch = new JCheckBox("Enable Short Branch Detect");

	public ImagePanel imgPanel;
	public JFileChooser fc;
	private ControlPanel self;


	public void initLayout() {		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 0.5;
		gc.weighty = 1;
		gc.gridheight=1;
		gc.gridwidth=2;
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		this.add(fileSelectBtn, gc);

		// 2 row
		gc.gridwidth=1;
		gc.gridheight=1;

		gc.weighty = 0.1;
		gc.weightx = 0.5;
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(chooseColorBtn, gc);
		
		
		gc.gridx = 1;
		gc.gridy = 1;
		this.add(gradientBtn, gc);
		
		gc.gridwidth=2;
		// 3 row
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(sliderLabel, gc);

		// 4rd row
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(weight, gc);

		// 5th row
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 4;
		this.add(stepLabel, gc);

		// 6th row
		gc.gridx = 0;
		gc.gridy = 5;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(step, gc);

		// 7th row
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 6;
		this.add(trendLabel, gc);

		// 8th row
		gc.gridx = 0;
		gc.gridy = 7;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(trend, gc); 

		// 9th row
		gc.gridx = 0;
		gc.gridy = 8;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(spaceCheck, gc); 

		// 10th row
		gc.gridx = 0;
		gc.gridy = 9;
		gc.weightx = 10;
		gc.anchor = GridBagConstraints.SOUTH;
		this.add(shortBranch, gc); 
		
		// 11th row
		gc.weighty = 3;
		gc.gridx = 0;
		gc.gridy = 10;
		this.add(genBtn, gc);

	}

	public void initWeightSlider () {
		sliderLabel = new JLabel("Weight of Hue Difference", JLabel.CENTER);
		weight = new JSlider(JSlider.HORIZONTAL, 0, 10, 6);
		weight.setPaintTicks(true);
		weight.setPaintLabels(true);
		weight.setMajorTickSpacing(5);
		weight.setMinorTickSpacing(1);
		Dimension sliderSize =weight.getPreferredSize();
		sliderSize.width = 200;
		weight.setPreferredSize(sliderSize);

		weight.addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateImage(e);                                   
			}		
		});	
	}

	public void initStepSlider () {
		stepLabel = new JLabel("Number of Steps performed", JLabel.CENTER);
		step = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);
		step.setPaintTicks(true);
		step.setPaintLabels(true);
		step.setMajorTickSpacing(20);
		step.setMinorTickSpacing(10);
		Dimension sliderSize =step.getPreferredSize();
		sliderSize.width= 200;
		step.setPreferredSize(sliderSize);

		step.addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateImage(e);                                  
			}		
		});	
	}



	public void initTrendSlider () {
		trendLabel = new JLabel("Weight of Keeping Trend", JLabel.CENTER);
		trend = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);
		trend.setPaintTicks(true);
		trend.setPaintLabels(true);
		trend.setMajorTickSpacing(5);
		trend.setMinorTickSpacing(1);
		Dimension sliderSize =trend.getPreferredSize();
		sliderSize.width= 200;
		trend.setPreferredSize(sliderSize);

		trend.addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateImage(e);                       
			}		
		});	
	}
	public void initFileSelectBtn () {
		fileSelectBtn = new JButton("Choose image");
		fileSelectBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Handle open button action.
				if (e.getSource() == fileSelectBtn) {
					int returnVal = fc.showOpenDialog(self);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File image = fc.getSelectedFile();
						//This is where a real application would open the file.
						Load.loadImage(image);
						file = image;
					}
				}
			}
		});
	}

	public void initGenBtn () {
		genBtn = new JButton("Generate");
		genBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO-DO
				updateImage(new ChangeEvent(e));       
			}						
		});

	}
	
	public void initChooseColorBtn () {
		chooseColorBtn = new JButton("Add Color");
		chooseColorBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO-DO
				ColorChooser.createAndShowGUI(self);
			}						
		});
	}
	
	public void initGradientBtn () {
		gradientBtn = new JButton("Add Gradient");
		gradientBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO-DO
				GradientPanel.createAndShowGUI(self);
			}						
		});
	}
	
	public Setting getSetting() {
		int w = weight.getValue();
		int s = step.getValue();
		int t = trend.getValue();
		boolean sc = spaceCheck.isSelected();
		boolean sb = shortBranch.isSelected();

		return new Setting(1, w, s, t, sc, sb);
	}
	
	public void updateImage(ChangeEvent e) {
		Setting setting = getSetting();
		fireControlEvent(new ControlEvent(e, setting));   
	}

	public void initFC () {
		fc = new JFileChooser("/Users/Sue/Dropbox/2013 WINTER/ra/MST Palette/");
	}
	public ControlPanel() {
		Dimension size = getPreferredSize();
		size.width = 300;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Control Panel"));

		// init components
		self = this;
		initWeightSlider();
		initStepSlider();
		initTrendSlider();
		initGenBtn();
		initChooseColorBtn();
		initGradientBtn ();
		initFC();
		initFileSelectBtn();
		// set Layout
		initLayout();

		file = new File("init.png");
	}

	public void fireControlEvent(ControlEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i+=2) {
			if (listeners[i] == ControlListener.class) {
				((ControlListener) listeners[i+1]).controlEventOccured(event);
			}
		}
	}
	public void addControlListener(ControlListener listener) {
		listenerList.add(ControlListener.class, listener);
	}
}
