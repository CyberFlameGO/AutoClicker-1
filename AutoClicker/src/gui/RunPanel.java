package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RunPanel extends JPanel {
	
	private JButton startButton, stopButton;
	private JLabel startDelayLabel;
	private JSpinner delaySpinner;
	
	private Controller controller;
	
	public RunPanel(Controller controller) {
		this.controller = controller;
	
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "Run", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		startButton = new JButton("Start!");
		stopButton = new JButton("Stop!");
		startDelayLabel = new JLabel("Start delay");
		delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(startDelayLabel);
		panel.add(delaySpinner);

		add(startButton);
		add(stopButton);
		add(panel);
	}
	
	private void initListeners() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				
				if (o == startButton) {
					controller.startPressed();
				} else if (o == stopButton) {
					controller.stopPressed();
				}
			}
		};
		
		startButton.addActionListener(al);
		stopButton.addActionListener(al);
		
		delaySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setDelayTime((Integer) delaySpinner.getValue());
			}
		});
	}
}
