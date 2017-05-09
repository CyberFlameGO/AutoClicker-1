package nz.co.troyshaw.autoclicker.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Clicker {

	private Robot robot;
	
	public Clicker() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException("Could not instantiate Robot");
		}
	}
	
	public void click() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
