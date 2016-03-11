package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JOptionPane;

public class Clicker {

	private Robot robot;
	
	public Clicker() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			String title = "Error";
			String message = "<html>Error message 1<br><br>Cannot instantiate java.awt.Robot." +
					"<br>Please give Java sufficient permissions." + 
					"<br><br>Program will now exit.</html>";
			JOptionPane.showMessageDialog(null,  message, title, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	public void click() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
