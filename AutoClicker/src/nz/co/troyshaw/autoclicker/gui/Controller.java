package nz.co.troyshaw.autoclicker.gui;

import java.awt.Component;

import nz.co.troyshaw.autoclicker.core.AutoClicker;
import nz.co.troyshaw.autoclicker.core.AutoclickerModel;
import nz.co.troyshaw.autoclicker.gui.panels.DelayPanel;
import nz.co.troyshaw.autoclicker.gui.panels.HotkeyPanel;
import nz.co.troyshaw.autoclicker.gui.panels.InfoPanel;
import nz.co.troyshaw.autoclicker.gui.panels.RunPanel;
import nz.co.troyshaw.autoclicker.system.HotkeyListener;
import nz.co.troyshaw.autoclicker.system.HotkeyNotifier;

public class Controller implements HotkeyListener {

	private AutoclickerModel model;
	private AutoClicker autoClicker;

	private State currentState = State.idle;

	private HotkeyPanel hotkeyPanel;
	private InfoPanel infoPanel;
	private DelayPanel delayPanel;
	private RunPanel runPanel;

	public Controller(AutoclickerModel model) {
		this.model = model;

		autoClicker = new AutoClicker(model, this);

		new HotkeyNotifier(this);
	}

	public void registerInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public void registerDelayPanel(DelayPanel delayPanel) {
		this.delayPanel = delayPanel;
	}

	public void registerRunPanel(RunPanel runPanel) {
		this.runPanel = runPanel;
	}

	public void registerHotkeyPanel(HotkeyPanel hotkeyPanel) {
		this.hotkeyPanel = hotkeyPanel;
	}

	@Override
	public void hotkeyPressed() {
		if (currentState == State.idle) {
			startPressed();
		} else {
			stopPressed();
		}
	}

	/**
	 * Starts the clicking process. 
	 * If clicking is already happening, this does nothing.
	 */
	public void startPressed() {
		if (currentState == State.idle) {
			if (!testAndSetDelayBetweenClicks()) {
				return;
			}

			beginClicking();
		}
	}

	/**
	 * Stops the current activity.
	 * 
	 * If the program is autoclicking, it stops immediately. If the program is
	 * currently idle, nothing happens.
	 */
	public void stopPressed() {
		currentState = State.idle;
		infoPanel.setInfo("Clicking Stopped");
		setComponentsEnabled(true);
		autoClicker.stopClicking();
	}

	/**
	 * Checks if the value in the field is valid, and if so sets it and returns
	 * true. Returns false if it's invalid.
	 * 
	 * If an invalid value is present, sets the state to State.stopped and displays
	 * an error message.
	 * 
	 * @return true if valid, false otherwise
	 */
	private boolean testAndSetDelayBetweenClicks() {
		int timeBetweenClicks = model.getTimeBetweenClicks();

		if (timeBetweenClicks == -1) {
			// was invalid so display to user and return
			infoPanel.setInfo("Invalid delay between clicks");
			currentState = State.idle;
			
			return false;
		} else {
			return true;
		}
	}

	public void setClicks(int clicks) {
		infoPanel.setClicks(clicks);
	}

	public void setTime(long millis) {
		long seconds = millis / 1000;
		long oneths = (millis - seconds * 1000) / 100;
		long tenths = (millis - seconds * 1000 - oneths * 100) / 10;
		long hundreths = (millis - seconds * 1000 - oneths * 100 - tenths * 10);

		String time = seconds + "." + oneths + tenths + hundreths;
		infoPanel.setTime(time);
	}

	/**
	 * Enables or disables various components so the user can interact again after
	 * finishing a clicking session.
	 */
	public void setComponentsEnabled(boolean enabled) {
		for (Component c : delayPanel.getComponents()) c.setEnabled(enabled);
		for (Component c : hotkeyPanel.getComponents()) c.setEnabled(enabled);

		runPanel.setEnabled(enabled);
	}

	/**
	 * Begins autoclicking immediately.
	 */
	private void beginClicking() {
		currentState = State.clicking;
		infoPanel.setInfo("Clicking Started");
		infoPanel.setClicks(0);
		infoPanel.setTime("0.000");
		setComponentsEnabled(false);

		autoClicker.beginClicking();
	}

	/**
	 * Represents the current state of the system.
	 *
	 * @author Troy Shaw
	 */
	private enum State {
		clicking,
		idle
	}
}
