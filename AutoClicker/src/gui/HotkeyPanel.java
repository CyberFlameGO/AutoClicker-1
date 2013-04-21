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

/**
 * Panel contains the current keys used for the stop/ start hotkey combination.
 * <p>
 * The dropdown box also lets the user change the values at any time.
 *
 * @author Troy Shaw
 */
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
		//use html as a workaround to display a newline
		ignoreDelayCheckBox = new JCheckBox("<html>Ignore<br>start delay</html>", false);

		modifierComboBox.setSelectedItem(Hotkey.MODIFIER);
		numberComboBox.setSelectedItem(Hotkey.NUMBER);

		ignoreDelayCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);

		add(hotkeyLabel);
		add(modifierComboBox);
		add(numberComboBox);
		add(ignoreDelayCheckBox);
	}

	private void initListeners() {
		//create action listener. Just have it update all 3 at once since it's more efficient
		ActionListener a = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setHotkeyIgnoreDelay(ignoreDelayCheckBox.isSelected());
				Hotkey.MODIFIER = (Modifier) modifierComboBox.getSelectedItem();
				Hotkey.NUMBER = (Numkey) numberComboBox.getSelectedItem();
			}
		};
		
		//add the listeners
		ignoreDelayCheckBox.addActionListener(a);
		modifierComboBox.addActionListener(a);
		numberComboBox.addActionListener(a);
	}
}
