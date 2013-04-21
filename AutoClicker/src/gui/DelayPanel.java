package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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

		NumberFormat nm = NumberFormat.getIntegerInstance();
		nm.setGroupingUsed(false);
		milliTextbox = new JFormattedTextField(nm);
		milliTextbox.setDocument(new UpperCaseDocument());
		milliTextbox.setColumns(10);
		milliTextbox.setText("1000");

		

		add(delayMilliLabel);
		add(milliTextbox);
	}

	private void initListeners() {

		milliTextbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
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

	private class UpperCaseDocument extends PlainDocument {

		@Override
		public void insertString(int offs, String str, AttributeSet a) 
				throws BadLocationException {

			if (str == null) {
				return;
			}
			
			char[] chars = str.toCharArray();
			StringBuffer bf = new StringBuffer();
			
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (Character.isDigit(c)) bf.append(c);
			}
			System.out.println(str.length());
			super.insertString(offs, bf.toString(), a);
		}
		
		
	}
}
