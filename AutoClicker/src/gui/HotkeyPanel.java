package gui;

import gui.Hotkey.Modifier;
import gui.Hotkey.Numkey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class HotkeyPanel extends JPanel {
	
	private JLabel hotkeyLabel;
	private JComboBox modifierComboBox, numberComboBox;
	private JCheckBox ignoreDelayCheckBox;
	
	private Controller controller;
	
	public HotkeyPanel(Controller controller) {
		this.controller = controller;
	
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "Start/ Stop Hotkey", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		hotkeyLabel = new JLabel("Hotkey");
		modifierComboBox = new JComboBox(Hotkey.Modifier.values());
		numberComboBox = new JComboBox(Hotkey.Numkey.values());
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
				//just set all three variables at once, is more efficient than if-else chain
				
				Modifier modifier = ((Modifier) modifierComboBox.getSelectedItem());
				Numkey numKey = ((Numkey) numberComboBox.getSelectedItem());
				
				controller.setHotkeyCombination(modifier, numKey);
				controller.setHotkeyIgnoreDelay(ignoreDelayCheckBox.isSelected());
			}
		};
		
		modifierComboBox.addActionListener(al);
		numberComboBox.addActionListener(al);
		ignoreDelayCheckBox.addActionListener(al);
	}
}
