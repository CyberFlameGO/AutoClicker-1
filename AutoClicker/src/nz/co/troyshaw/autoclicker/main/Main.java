package nz.co.troyshaw.autoclicker.main;

import javax.swing.SwingUtilities;

import nz.co.troyshaw.autoclicker.gui.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainFrame::new);
	}
}
