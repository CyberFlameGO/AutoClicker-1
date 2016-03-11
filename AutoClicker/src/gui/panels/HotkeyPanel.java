package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import gui.Hotkey;
import gui.Hotkey.Modifier;
import gui.Hotkey.Numkey;

/**
 * Panel contains the current keys used for the stop/ start hotkey combination.
 * <p>
 * The dropdown box also lets the user change the values at any time.
 * <P>
 * There is also a checkbox to let the user ignore the delay timeout when using hotkeys.
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
		ActionListener a = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Hotkey.MODIFIER = (Modifier) modifierComboBox.getSelectedItem();
				Hotkey.NUMBER = (Numkey) numberComboBox.getSelectedItem();
			}
		};
		
		modifierComboBox.addActionListener(a);
		numberComboBox.addActionListener(a);
	}
}
