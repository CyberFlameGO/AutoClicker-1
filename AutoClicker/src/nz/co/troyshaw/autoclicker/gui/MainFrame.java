package nz.co.troyshaw.autoclicker.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import nz.co.troyshaw.autoclicker.core.AutoclickerModel;
import nz.co.troyshaw.autoclicker.gui.panels.DelayPanel;
import nz.co.troyshaw.autoclicker.gui.panels.HotkeyPanel;
import nz.co.troyshaw.autoclicker.gui.panels.InfoPanel;
import nz.co.troyshaw.autoclicker.gui.panels.RunPanel;
import nz.co.troyshaw.autoclicker.misc.Globals;

public class MainFrame extends JFrame {

	private DelayPanel delayPanel;
	private HotkeyPanel hotkeyPanel;
	private RunPanel runPanel;
	private InfoPanel infoPanel;

	private Controller controller;
	private AutoclickerModel model;

	public MainFrame() {
		super("Autoclicker");
		
		GuiUtil.setNativeLAndF();

		model = new AutoclickerModel(Globals.DEFAULT_TIME_BETWEEN_CLICKS);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		GuiUtil.center(this);
		setVisible(true);
	}
}
