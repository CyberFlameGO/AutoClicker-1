package nz.co.troyshaw.autoclicker.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class GuiUtil {

	public static void center(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		frame.setLocation(x, y);
	}
	
	public static void setNativeLAndF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//do nothing. It will default to normal
		}
	}

}
