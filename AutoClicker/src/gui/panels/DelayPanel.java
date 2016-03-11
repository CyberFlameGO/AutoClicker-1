package gui.panels;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import autoclicker.Model;
import gui.GuiUtil;
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
		GuiUtil.addChangeListener(milliTextbox, e -> attemptSetDelay(milliTextbox.getText()));
	}
	
	private void attemptSetDelay(String newDelay) {
		int val = 0;
		try {
			val = Integer.parseInt(newDelay);
			
			if (val < 0) {
				milliTextbox.setText(Integer.toString(Globals.DEFAULT_CLICK_DELAY));
				val = Globals.DEFAULT_CLICK_DELAY;
			}
		} catch (NumberFormatException e) {
			//text somehow wasn't a number, so return -1
			val = Globals.DEFAULT_CLICK_DELAY;
		}
		
		model.setClickDelay(val);
	}
}
