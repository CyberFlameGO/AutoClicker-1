package gui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Toolbar extends JMenuBar{
	
	public Toolbar(Controller controller) {		
		JMenuItem basicModeItem = new JRadioButtonMenuItem("Basic Mode", true);
		JMenuItem advancedModeItem = new JRadioButtonMenuItem("Advanced Mode", false);		
		
		ButtonGroup group = new ButtonGroup();
		group.add(basicModeItem);
		group.add(advancedModeItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		
		basicModeItem.addActionListener(e -> controller.setBasicViewMode());
		advancedModeItem.addActionListener(e -> controller.setAdvancedViewMode());
		exitItem.addActionListener(e -> System.exit(0));
		
		JMenu fileMenu = new JMenu("File");
		
		//fileMenu.add(basicModeItem);
		//fileMenu.add(advancedModeItem);
		//fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		add(fileMenu);
	}
}
