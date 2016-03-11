package gui.panels;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import gui.Controller;

public class RunPanel extends JPanel {

	private Controller controller;
	private JButton startButton, stopButton;

	public RunPanel(Controller controller) {
		this.controller = controller;

		initComponents();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "Run", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		startButton = new JButton("Start!");
		stopButton = new JButton("Stop!");

		startButton.addActionListener(e -> controller.startPressed());
		stopButton.addActionListener(e -> controller.stopPressed());
		
		stopButton.setEnabled(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		add(startButton);
		add(stopButton);
		add(panel);
	}

	public void enableComponents() {
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
	}

	public void disableComponents() {
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
	}
}
