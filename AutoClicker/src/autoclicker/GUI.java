package autoclicker;

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
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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

import autoclicker.AutoClicker.Modifiers;


public class GUI extends JFrame{

	//panels
	private JPanel hotkeyPanel, anticheatPanel, delayPanel, clickDurationPanel, runPanel, statusPanel;

	//hotkeyPanel components
	private JLabel hotkeyLabel;
	private JComboBox modifierComboBox, numberComboBox;
	private JCheckBox ignoreDelayCheckBox;

	//anticheatPanel components
	private JCheckBox anticheatEnabled;
	private JRadioButton anticheatDefault, anticheatAdvanced;
	private JLabel spreadLabel, variationLabel;
	private JSlider spreadSlider, variationSlider;

	//delayPanel components
	private JLabel delaySecondsLabel, delayMilliLabel;
	private JTextField secondsTextbox, milliTextbox;
	private Integer secondsTextInt = 1, milliTextInt = 0;

	//clickDurationPanel components
	private JRadioButton foreverRButton, numClicksRButton, timeDurationRButton;
	private JTextField durationCountField, delaySecondsField, delayMilliSecondsField;
	private Integer numClicks = 0;

	//runPanel components
	private JButton startButton, stopButton;
	private JLabel startDelayLabel;
	private JSpinner delaySpinner;

	//statusPanel components
	private JLabel statusLabel, currentStatusLabel, timeLabel, currentTimeLabel, clickLabel, currentClickLabel;
	

	//AutoClicker object
	private AutoClicker autoClicker;

	public GUI(AutoClicker autoClicker) {
		super("Autoclicker");

		setNativeLAndF();

		this.autoClicker = autoClicker;

		//creates components and adds them to panels
		initializeHotkeyPanel();
		initializeAnticheatPanel();
		initializeDelayPanel();
		initializeClickDurationPanel();
		initializeRunPanel();
		initializeStatusPanel();

		//add listeners/ relations/ etc	(done after initialization to ensure no nulls)
		setupRelationsHotkeyPanel();
		setupRelationsAnticheatPanel();
		setupRelationsDelayPanel();
		setupRelationsClickDurationPanel();
		setupRelationsRunPanel();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(hotkeyPanel);
		panel.add(anticheatPanel);
		panel.add(delayPanel);
		panel.add(clickDurationPanel);
		panel.add(runPanel);
		panel.add(statusPanel);

		getContentPane().add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		center();
		setVisible(true);
	}


	private void setNativeLAndF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//do nothing. It will default to normal
		}
	}


	//panel initialization methods

	private void initializeHotkeyPanel() {
		hotkeyPanel = new JPanel();

		hotkeyPanel.setBorder(new TitledBorder(null, "Start/ Stop Hotkey", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		hotkeyLabel = new JLabel("Hotkey");
		//hotkeyComboBox = new JComboBox(new String[] {"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12"});
		modifierComboBox = new JComboBox(AutoClicker.Modifiers.values());
		numberComboBox = new JComboBox(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
		ignoreDelayCheckBox = new JCheckBox("<html>Ignore<br>start delay</html>", false);

		modifierComboBox.setSelectedIndex(0);
		ignoreDelayCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);

		hotkeyPanel.add(hotkeyLabel);
		hotkeyPanel.add(modifierComboBox);
		hotkeyPanel.add(numberComboBox);
		hotkeyPanel.add(ignoreDelayCheckBox);
	}


	private void initializeAnticheatPanel() {
		anticheatPanel = new JPanel();
		anticheatPanel.setBorder(new TitledBorder(null, "Anti-Cheat detection", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		anticheatEnabled = new JCheckBox("Enabled", false);
		anticheatDefault = new JRadioButton("Default");
		anticheatAdvanced = new JRadioButton("Advanced configuration");
		spreadLabel = new JLabel("Spread");
		variationLabel = new JLabel("Variation");
		spreadSlider = new JSlider(0, 100, 50); 
		variationSlider = new JSlider(0, 100, 50);

		ButtonGroup group = new ButtonGroup();
		group.add(anticheatDefault);
		group.add(anticheatAdvanced);
		anticheatDefault.setSelected(true);

		anticheatDefault.setEnabled(false);
		anticheatAdvanced.setEnabled(false);
		spreadLabel.setEnabled(false);
		variationLabel.setEnabled(false);
		spreadSlider.setEnabled(false);
		variationSlider.setEnabled(false);


		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(anticheatDefault);
		buttonPanel.add(anticheatAdvanced);

		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

		JPanel spreadPanel = new JPanel();
		spreadPanel.add(spreadLabel);
		spreadPanel.add(spreadSlider);

		JPanel variationPanel = new JPanel();
		variationPanel.add(variationLabel);
		variationPanel.add(variationSlider);

		sliderPanel.add(spreadPanel);
		sliderPanel.add(variationPanel);

		anticheatPanel.add(anticheatEnabled);
		anticheatPanel.add(buttonPanel);
		anticheatPanel.add(sliderPanel);
	}

	private void initializeDelayPanel() {
		delayPanel = new JPanel();
		delayPanel.setBorder(new TitledBorder(null, "Delay Between Clicks", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		delaySecondsLabel = new JLabel("Seconds");
		delayMilliLabel = new JLabel("Milliseconds");
		secondsTextbox = new JTextField(6);
		milliTextbox = new JTextField(6);
		milliTextbox = new JFormattedTextField(NumberFormat.getInstance());

		secondsTextbox.setText("1");
		milliTextbox.setText("0");

		delayPanel.add(delaySecondsLabel);
		delayPanel.add(secondsTextbox);
		delayPanel.add(delayMilliLabel);
		delayPanel.add(milliTextbox);
	}

	private void initializeClickDurationPanel() {
		clickDurationPanel = new JPanel();
		clickDurationPanel.setBorder(new TitledBorder(null, "Click Duration", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		foreverRButton = new JRadioButton("Forever");
		numClicksRButton = new JRadioButton("Number of clicks");
		timeDurationRButton = new JRadioButton("Time duration");

		ButtonGroup group = new ButtonGroup();

		group.add(foreverRButton);
		group.add(numClicksRButton);
		group.add(timeDurationRButton);

		foreverRButton.setSelected(true);

		durationCountField = new JTextField(6);
		delayMilliSecondsField = new JTextField(6);

		JPanel durationPanel = new JPanel();
		durationPanel.setLayout(new GridLayout(2, 3, 10, 2));

		durationPanel.add(foreverRButton);
		durationPanel.add(numClicksRButton);
		//durationPanel.add(timeDurationRButton);

		durationPanel.add(new JPanel());	//dummy panel for the "forever" RButton
		durationPanel.add(durationCountField);
		//durationPanel.add(delayMilliSecondsField);

		clickDurationPanel.add(durationPanel);
	}

	private void initializeRunPanel() {
		runPanel = new JPanel();
		runPanel.setBorder(new TitledBorder(null, "Run", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		startButton = new JButton("Start!");
		stopButton = new JButton("Stop!");
		startDelayLabel = new JLabel("Start delay");
		delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(startDelayLabel);
		panel.add(delaySpinner);

		runPanel.add(startButton);
		runPanel.add(stopButton);
		runPanel.add(panel);
	}
	
	private void initializeStatusPanel() {
		statusPanel = new JPanel();
		statusPanel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		statusPanel.setLayout(new GridLayout(0, 2));
		
		statusLabel = new JLabel("Status:");
		clickLabel = new JLabel("Clicks:");
		timeLabel = new JLabel("Time:");
		
		currentStatusLabel = new JLabel("Not running");
		currentClickLabel = new JLabel("0");
		currentTimeLabel = new JLabel("0");
		
		statusPanel.add(statusLabel);
		statusPanel.add(currentStatusLabel);
		statusPanel.add(clickLabel);
		statusPanel.add(currentClickLabel);
		statusPanel.add(timeLabel);
		statusPanel.add(currentTimeLabel);
	}

	//end panel initialization

	//begin panel event listener adding/ etc

	private void setupRelationsHotkeyPanel() {
		//needs to set hotkey when changed and set ignore startdelay
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				
				if (o == modifierComboBox) {
					autoClicker.setHotkey((Modifiers) modifierComboBox.getSelectedItem());
				} else if (o == numberComboBox) {
					autoClicker.setHotkey((Modifiers) modifierComboBox.getSelectedItem());
				} else if (o == ignoreDelayCheckBox) {
					autoClicker.setHotkeyIgnoreDelay(ignoreDelayCheckBox.isSelected());
				}
			}
		};
		
		modifierComboBox.addActionListener(al);
		numberComboBox.addActionListener(al);
		ignoreDelayCheckBox.addActionListener(al);
	}

	private void setupRelationsAnticheatPanel() {

		anticheatEnabled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				boolean optionEnabled = anticheatEnabled.isSelected();
				boolean sliderEnabled = anticheatAdvanced.isSelected() && optionEnabled;

				anticheatDefault.setEnabled(optionEnabled);
				anticheatAdvanced.setEnabled(optionEnabled);

				spreadLabel.setEnabled(sliderEnabled);
				variationLabel.setEnabled(sliderEnabled);
				spreadSlider.setEnabled(sliderEnabled);
				variationSlider.setEnabled(sliderEnabled);


				autoClicker.setAnticheatDetection(optionEnabled);
			}
		});

		anticheatDefault.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = anticheatDefault.isSelected();

				if (selected) {
					variationSlider.setValue(variationSlider.getMaximum() / 2);
					spreadSlider.setValue(spreadSlider.getMaximum() / 2);
					autoClicker.setAnticheatDefault();
				} else {
					autoClicker.setAnticheatSpread(spreadSlider.getValue());
					autoClicker.setAnticheatVariation(variationSlider.getValue());
				}

				spreadLabel.setEnabled(!selected);
				variationLabel.setEnabled(!selected);
				spreadSlider.setEnabled(!selected);
				variationSlider.setEnabled(!selected);
			}
		});

		spreadSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				autoClicker.setAnticheatSpread(spreadSlider.getValue());
			}		
		});

		variationSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				autoClicker.setAnticheatSpread(variationSlider.getValue());
			}
		});
	}

	private void setupRelationsDelayPanel() { 
		secondsTextbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (validTextField(secondsTextbox)) {
						//secondsTextBox.setText()
					}
				}
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

		secondsTextbox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//parseTextField(secondsTextbox, secondsTextInt);
			}
		});

		milliTextbox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//parseTextField(milliTextbox, milliTextInt);
			}
		});
	}

	private void setupRelationsClickDurationPanel() { 

	}

	private void setupRelationsRunPanel() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				
				if (o == startButton) {
					autoClicker.runViaButton();
				} else if (o == stopButton) {
					autoClicker.stop();
				}
			}
		};
		
		startButton.addActionListener(al);
		stopButton.addActionListener(al);
	}


	//end panel event listener adding/ etc

	//utility methods

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
