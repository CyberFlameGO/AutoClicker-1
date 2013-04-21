package gui;

import gui.Hotkey.Modifier;
import gui.Hotkey.Numkey;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class MainFrame extends JFrame{

	//panels
	private DelayPanel delayPanel;
	private HotkeyPanel hotkeyPanel;
	private AnticheatPanel anticheatPanel;
	private DurationPanel durationPanel;
	private RunPanel runPanel;
	private InfoPanel infoPanel;

	//AutoClicker object
	private boolean countingDown;
	
	//hotkey
	private Modifier modifier = Modifier.values()[0];
	private Numkey numKey = Numkey.values()[0];
	
	Controller controller;

	public MainFrame() {
		super("Autoclicker");
		setNativeLAndF();

		controller = new Controller();
		
		initPanels();
		setupLayout();
		initFrame();
	}
	
	private void initPanels() {
		hotkeyPanel = new HotkeyPanel(controller);
		anticheatPanel = new AnticheatPanel(controller);
		delayPanel = new DelayPanel();
		durationPanel = new DurationPanel();
		runPanel = new RunPanel(controller);
		infoPanel = new InfoPanel();
		
		controller.registerInfoPanel(infoPanel);
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
