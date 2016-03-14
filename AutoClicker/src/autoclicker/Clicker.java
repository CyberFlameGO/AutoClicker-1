package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import gui.GuiUtil;

public class Clicker {

	private Robot robot;
	
	public Clicker() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			GuiUtil.displayErrorDialog("Cannot instantiate java.awt.Robot");
			System.exit(1);
		}
	}
	
	public void click() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
