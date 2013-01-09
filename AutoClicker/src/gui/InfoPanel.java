package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {

	private JLabel statusLabel, currentStatusLabel, timeLabel, currentTimeLabel, clickLabel, currentClickLabel;
	
	
	public InfoPanel() {
		setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2));
		
		statusLabel = new JLabel("Status:");
		clickLabel = new JLabel("Clicks:");
		timeLabel = new JLabel("Time:");
		
		currentStatusLabel = new JLabel("Not running");
		currentClickLabel = new JLabel("0");
		currentTimeLabel = new JLabel("0");
		
		add(statusLabel);
		add(currentStatusLabel);
		add(clickLabel);
		add(currentClickLabel);
		add(timeLabel);
		add(currentTimeLabel);
	}
}
