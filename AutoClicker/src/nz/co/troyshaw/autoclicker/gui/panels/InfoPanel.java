package nz.co.troyshaw.autoclicker.gui.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {

	private JLabel currentStatusLabel, currentTimeLabel, currentClickLabel;

	public InfoPanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2));

		JLabel statusLabel = new JLabel("Status:");
		JLabel clickLabel = new JLabel("Clicks:");
		JLabel timeLabel = new JLabel("Time:");

		currentStatusLabel = new JLabel("Not Running");
		currentClickLabel = new JLabel("0");
		currentTimeLabel = new JLabel("0.000");

		add(statusLabel);
		add(currentStatusLabel);
		add(clickLabel);
		add(currentClickLabel);
		add(timeLabel);
		add(currentTimeLabel);
	}

	public void setInfo(String info) {
		currentStatusLabel.setText(info);
	}

	public void setClicks(int clicks) {
		currentClickLabel.setText(Integer.toString(clicks));
	}

	public void setTime(String time) {
		currentTimeLabel.setText(time);
	}
}
