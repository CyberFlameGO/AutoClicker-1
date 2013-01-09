package gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class DelayPanel extends JPanel {

	private JLabel delayMilliLabel;
	private JTextField milliTextbox;
	private Integer secondsTextInt = 1, milliTextInt = 0;
	
	public DelayPanel() {
		initComponents();
		initListeners();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "Delay Between Clicks", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		delayMilliLabel = new JLabel("Milliseconds");
		milliTextbox = new JTextField(6);
		milliTextbox.setText("1000");

		add(delayMilliLabel);
		add(milliTextbox);
	}
	
	private void initListeners() {

		milliTextbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//parseTextField(milliTextbox, milliTextInt);
				}
			}
		});

		milliTextbox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//parseTextField(milliTextbox, milliTextInt);
			}
		});
	}
}
