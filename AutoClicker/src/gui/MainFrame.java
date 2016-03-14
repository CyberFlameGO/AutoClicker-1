package gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import autoclicker.Model;
import gui.panels.DelayPanel;
import gui.panels.HotkeyPanel;
import gui.panels.InfoPanel;
import gui.panels.RunPanel;

public class MainFrame extends JFrame {

	private DelayPanel delayPanel;
	private HotkeyPanel hotkeyPanel;
	private RunPanel runPanel;
	private InfoPanel infoPanel;

	private Controller controller;
	private Model model;

	public MainFrame() {
		super("Autoclicker");
		GuiUtil.setNativeLAndF();

		model = new Model();
		controller = new Controller(model);
		
		initPanels();
		setupLayout();
		initFrame();
	}
	
	private void initPanels() {
		hotkeyPanel = new HotkeyPanel();
		delayPanel = new DelayPanel(model);
		runPanel = new RunPanel(controller);
		infoPanel = new InfoPanel();
		
		controller.registerInfoPanel(infoPanel);
		controller.registerDelayPanel(delayPanel);
		controller.registerRunPanel(runPanel);
		controller.registerHotkeyPanel(hotkeyPanel);
	}
	
	private void setupLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(hotkeyPanel);
		panel.add(delayPanel);
		panel.add(runPanel);
		panel.add(infoPanel);
		
		getContentPane().add(panel);
	}
	
	private void initFrame() {
		setJMenuBar(new Toolbar(controller));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		GuiUtil.center(this);
		setVisible(true);
	}
}
