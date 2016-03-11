package gui.panels;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import autoclicker.Model;

public class DurationPanel extends JPanel {

	private JRadioButton foreverRButton, numClicksRButton, timeDurationRButton;
	private JTextField durationCountField, delaySecondsField, delayMilliSecondsField;
	private Integer numClicks = 0;

	private Model model;

	public DurationPanel(Model model) {
		this.model = model;
		
		initComponents();
	}


	private void initComponents() {
		setBorder(new TitledBorder(null, "Click Duration", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		foreverRButton = new JRadioButton("Forever");
		numClicksRButton = new JRadioButton("Number of clicks");
		timeDurationRButton = new JRadioButton("Time duration");

		ButtonGroup group = new ButtonGroup();

		group.add(foreverRButton);
		group.add(numClicksRButton);
		group.add(timeDurationRButton);

		foreverRButton.setSelected(true);

		durationCountField = new JTextField(6);
		delayMilliSecondsField = new JTextField(6);

		JPanel durationPanel = new JPanel();
		durationPanel.setLayout(new GridLayout(2, 3, 10, 2));

		durationPanel.add(foreverRButton);
		durationPanel.add(numClicksRButton);
		//durationPanel.add(timeDurationRButton);

		durationPanel.add(new JPanel());	//dummy panel for the "forever" RButton
		durationPanel.add(durationCountField);
		//durationPanel.add(delayMilliSecondsField);

		add(durationPanel);
	}	
}