package gui;

import gui.Hotkey.Modifier;
import gui.Hotkey.NumKey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.sun.jna.platform.KeyboardUtils;

public class HotkeyPanel extends JPanel {
	
	private JLabel hotkeyLabel;
	private JComboBox modifierComboBox, numberComboBox;
	private JCheckBox ignoreDelayCheckBox;
	
	public HotkeyPanel() {
		initThread();
		initComponents();
		initListeners();
	}
	
	private void initThread() {
		Thread t = new Thread() {
			boolean pressed;
			
			@Override
			public void run() {
				while (true) {
					
//					if (KeyboardUtils.isPressed(modifier.keycode) && KeyboardUtils.isPressed(numKey.keycode) && !pressed) {
//						pressed = true;
						//if we are running, signal we want to stop
//						if (autoClicker.isRunning()) {
//							autoClicker.stop();
//						} else if (countingDown) {
//							//if we are counting down, stop countdown
//							stopCountdown();
//						} else {
//							//else we want to start new clicking
//							if (ignoreDelayCheckBox.isSelected()) {
//								//if ignore is deselected, we start
//								startAutoclick();
//							} else {
//								
//							}
//						}
//					} else {
//						pressed = false;
//					}
					
					try {
						//sleep for a bit
						Thread.sleep(10);
					} catch (InterruptedException e) {
						//ignore
					}
				}
			}
		};
		
		t.start();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "Start/ Stop Hotkey", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		hotkeyLabel = new JLabel("Hotkey");
		modifierComboBox = new JComboBox(Hotkey.Modifier.values());
		numberComboBox = new JComboBox(Hotkey.NumKey.values());
		ignoreDelayCheckBox = new JCheckBox("<html>Ignore<br>start delay</html>", false);

		modifierComboBox.setSelectedIndex(0);
		ignoreDelayCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);

		add(hotkeyLabel);
		add(modifierComboBox);
		add(numberComboBox);
		add(ignoreDelayCheckBox);
	}
	
	private void initListeners() {
		//needs to set hotkey when changed and set ignore startdelay
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				
				if (o == modifierComboBox) {
					//modifier = ((Modifier) modifierComboBox.getSelectedItem());
				} else if (o == numberComboBox) {
					//numKey = ((NumKey) numberComboBox.getSelectedItem());
				} else if (o == ignoreDelayCheckBox) {
					//autoClicker.setHotkeyIgnoreDelay(ignoreDelayCheckBox.isSelected());
				}
			}
		};
		
		modifierComboBox.addActionListener(al);
		numberComboBox.addActionListener(al);
		ignoreDelayCheckBox.addActionListener(al);
	}
}
