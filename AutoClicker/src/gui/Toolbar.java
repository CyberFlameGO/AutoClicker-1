package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Toolbar extends JMenuBar{
	
	public Toolbar(Controller controller) {		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(exitItem);
		add(fileMenu);
	}
}
