package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import autoclicker.Model;
import gui.panels.AnticheatPanel;
import gui.panels.DelayPanel;
import gui.panels.DurationPanel;
import gui.panels.HotkeyPanel;
import gui.panels.InfoPanel;
import gui.panels.RunPanel;

public class MainFrame extends JFrame {

	private DelayPanel delayPanel;
	private HotkeyPanel hotkeyPanel;
	private AnticheatPanel anticheatPanel;
	private DurationPanel durationPanel;
	private RunPanel runPanel;
	private InfoPanel infoPanel;

	private Controller controller;
	private Model model;

	public MainFrame() {
		super("Autoclicker");
		GuiUtil.setNativeLAndF();

		model = new Model();
		
		controller = new Controller(model);
		controller.registerMainFrame(this);
		
		initPanels();
		setupLayout();
		initFrame();
	}
	
	private void initPanels() {
		hotkeyPanel = new HotkeyPanel();
		anticheatPanel = new AnticheatPanel(model);
		delayPanel = new DelayPanel(model);
		durationPanel = new DurationPanel(model);
		runPanel = new RunPanel(controller);
		infoPanel = new InfoPanel();
		
		controller.registerInfoPanel(infoPanel);
		controller.registerDelayPanel(delayPanel);
		controller.registerDurationPanel(durationPanel);
		controller.registerRunPanel(runPanel);
		controller.registerAnticheatPanel(anticheatPanel);
		controller.registerHotkeyPanel(hotkeyPanel);
	}
	
	private void setupLayout() {
		setBasicViewMode();
	}
	
	public void setBasicViewMode() {
		List<JPanel> panels = new ArrayList<JPanel>();
		panels.add(hotkeyPanel);
		panels.add(delayPanel);
		panels.add(runPanel);
		panels.add(infoPanel);
	
		setViewMode(panels);
	}
	
	public void setAdvancedViewMode() {
		List<JPanel> panels = new ArrayList<JPanel>();
		panels.add(hotkeyPanel);
		panels.add(anticheatPanel);
		panels.add(delayPanel);
		panels.add(durationPanel);
		panels.add(runPanel);
		panels.add(infoPanel);
		
		setViewMode(panels);
	}
	
	private void setViewMode(List<JPanel> panels) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (JPanel p : panels) {
			panel.add(p); 
		}
		
		getContentPane().removeAll();
		getContentPane().add(panel);
		pack();
		repaint();
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
