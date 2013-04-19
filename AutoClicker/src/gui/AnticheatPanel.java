package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autoclicker.AutoClicker;

public class AnticheatPanel extends JPanel {
	
	private JCheckBox anticheatEnabled;
	private JRadioButton anticheatDefault, anticheatAdvanced;
	private JLabel spreadLabel, variationLabel;
	private JSlider spreadSlider, variationSlider;
	
	private Controller controller;
	
	public AnticheatPanel(Controller controller) {
		this.controller = controller;
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "Anti-Cheat detection", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		anticheatEnabled = new JCheckBox("Enabled", false);
		anticheatDefault = new JRadioButton("Default");
		anticheatAdvanced = new JRadioButton("Advanced configuration");
		spreadLabel = new JLabel("Spread");
		variationLabel = new JLabel("Variation");
		spreadSlider = new JSlider(0, AutoClicker.MAX_SPREAD, AutoClicker.DEFAULT_SPREAD); 
		variationSlider = new JSlider(0, AutoClicker.MAX_VARIATION, AutoClicker.DEFAULT_VARIATION); 

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

		add(anticheatEnabled);
		add(buttonPanel);
		add(sliderPanel);
	}
	
	private void initListeners() {

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


				controller.setAnticheatEnabled(optionEnabled);
			}
		});

		anticheatDefault.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = anticheatDefault.isSelected();
				
				if (selected) {
					variationSlider.setValue(variationSlider.getMaximum() / 2);
					spreadSlider.setValue(spreadSlider.getMaximum() / 2);
				}
				
				//send data to controller
				controller.setAnticheatValues(spreadSlider.getValue(), variationSlider.getValue());

				spreadLabel.setEnabled(!selected);
				variationLabel.setEnabled(!selected);
				spreadSlider.setEnabled(!selected);
				variationSlider.setEnabled(!selected);
			}
		});

		spreadSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				//autoClicker.setAnticheatSpread(spreadSlider.getValue());
			}		
		});

		variationSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				//autoClicker.setAnticheatSpread(variationSlider.getValue());
			}
		});
	}
}
