package gui;

import gui.Hotkey.Modifier;
import gui.Hotkey.NumKey;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.jna.platform.KeyboardUtils;

import autoclicker.AutoClicker;


public class MainFrame extends JFrame{

	//panels
	private DelayPanel delayPanel;
	private HotkeyPanel hotkeyPanel;
	private AnticheatPanel anticheatPanel;
	private DurationPanel durationPanel;
	private RunPanel runPanel;
	private InfoPanel infoPanel;

	//AutoClicker object
	private AutoClicker autoClicker;
	private boolean countingDown;
	
	//hotkey
	private Modifier modifier = Modifier.values()[0];
	private NumKey numKey = NumKey.values()[0];

	public MainFrame(AutoClicker autoClicker) {
		super("Autoclicker");
		setNativeLAndF();

		this.autoClicker = autoClicker;
		
		initPanels();
		setupLayout();
		initFrame();
	}
	
	private void initPanels() {
		hotkeyPanel = new HotkeyPanel();
		anticheatPanel = new AnticheatPanel();
		delayPanel = new DelayPanel();
		durationPanel = new DurationPanel();
		runPanel = new RunPanel();
		infoPanel = new InfoPanel();
	}
	
	private void setupLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(hotkeyPanel);
		panel.add(anticheatPanel);
		panel.add(delayPanel);
		panel.add(durationPanel);
		panel.add(runPanel);
		panel.add(infoPanel);
		
		getContentPane().add(panel);
	}
	
	
	private void initFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		center();
		setVisible(true);
	}
	
	/**
	 * Sets native look and feel.
	 */
	private void setNativeLAndF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//do nothing. It will default to normal
		}
	}

	/**
	 * Centers the frame.
	 */
	private void center() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		setLocation(x, y);
	}

	@SuppressWarnings("unused")
	private void submitTextfieldValues() {

	}

	private boolean validTextField(JTextField field) {
		int value;
		
		try {
			value = Integer.parseInt(field.getText());
			if (value < 0) return false;
		} catch (NumberFormatException e) {
			return false;
		}

		field.setText(Integer.toString(value));
		return true;
	}
}
