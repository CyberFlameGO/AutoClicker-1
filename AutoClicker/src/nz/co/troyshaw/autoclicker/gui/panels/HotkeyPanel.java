package nz.co.troyshaw.autoclicker.gui.panels;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import nz.co.troyshaw.autoclicker.gui.Hotkey;
import nz.co.troyshaw.autoclicker.gui.Hotkey.Modifier;
import nz.co.troyshaw.autoclicker.gui.Hotkey.Numkey;

/**
 * Panel contains the current keys used for the stop/ start hotkey combination.
 * A dropdown box lets the user change the values.
 *
 * @author Troy Shaw
 */
public class HotkeyPanel extends JPanel {

	private JLabel hotkeyLabel;
	private JComboBox<Modifier> modifierComboBox;
	private JComboBox<Numkey> numberComboBox;

	public HotkeyPanel() {
		initComponents();
		initListeners();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "Start/ Stop Hotkey", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		hotkeyLabel = new JLabel("Hotkey");
		modifierComboBox = new JComboBox<Modifier>(Hotkey.Modifier.values());
		numberComboBox = new JComboBox<Numkey>(Hotkey.Numkey.values());
		
		modifierComboBox.setSelectedItem(Hotkey.MODIFIER);
		numberComboBox.setSelectedItem(Hotkey.NUMBER);

		add(hotkeyLabel);
		add(modifierComboBox);
		add(numberComboBox);
	}

	private void initListeners() {
		modifierComboBox.addActionListener(ae -> Hotkey.setModifier(modifierComboBox.getItemAt(modifierComboBox.getSelectedIndex())));
		numberComboBox.addActionListener(ae -> Hotkey.setNumber(numberComboBox.getItemAt(numberComboBox.getSelectedIndex())));
	}
}
