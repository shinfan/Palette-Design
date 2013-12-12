import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GradientChooser extends JPanel
                              implements ChangeListener {

    protected JColorChooser tcc;
    protected JButton submitBtn;
    protected GradientPanel cp;

    public GradientChooser(final GradientPanel cp) {
        super(new BorderLayout());
        this.cp = cp;
        //Set up color chooser for setting text color
        tcc = new JColorChooser();
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));

        JButton submitBtn = new JButton("Add Color");
        submitBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Handlxe open button action.
				Color newColor = tcc.getColor();
				cp.setColor(newColor);
			}
		});

        add(tcc, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.PAGE_END);

    }

    public void stateChanged(ChangeEvent e) {
        //Color newColor = tcc.getColor();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static void createAndShowGUI(GradientPanel cp) {
        //Create and set up the window.
        JFrame frame = new JFrame("ColorChooserDemo");

        //Create and set up the content pane.
        JComponent newContentPane = new GradientChooser(cp);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}