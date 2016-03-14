package gui.panels;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.Optional;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import autoclicker.Model;
import misc.Globals;

public class DelayPanel extends JPanel {
	
	private JTextField milliTextbox;
	private Model model;
	
	public DelayPanel(Model model) {
		this.model = model;
		
		initComponents();
		initListeners();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "Delay Between Clicks", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel delayMilliLabel = new JLabel("Milliseconds");

		NumberFormat nm = NumberFormat.getIntegerInstance();
		nm.setGroupingUsed(false);
		milliTextbox = new JFormattedTextField(nm);
		milliTextbox.setColumns(10);
		milliTextbox.setText(Integer.toString(Globals.DEFAULT_CLICK_DELAY));

		add(delayMilliLabel);
		add(milliTextbox);
	}

	private void initListeners() {
		milliTextbox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				attemptSetDelay(milliTextbox.getText());
			}
		});
	}
		
	private void attemptSetDelay(String newDelay) {
		Optional<Integer> val = getNumber(newDelay);
		
		if (val.isPresent()) {
			model.setClickDelay(val.get());
		} else {
			model.setClickDelay(Globals.DEFAULT_CLICK_DELAY);
			milliTextbox.setText(Integer.toString(Globals.DEFAULT_CLICK_DELAY));
		}
	}
	
	/**
	 * Parses the delay-time from the given newDelay argument.
	 * 
	 * @return a valid value, or Optional.empty()
	 */
	private Optional<Integer> getNumber(String newDelay) {
		try {
			int value = Integer.parseInt(newDelay);
			
			if (value > 0) {
				return Optional.of(value);
			} else {
				return Optional.empty();
			}
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
