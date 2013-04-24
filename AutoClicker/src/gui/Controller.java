package gui;

import java.util.List;

import javax.swing.SwingWorker;

import autoclicker.AutoClicker;
import autoclicker.HotkeyListener;
import autoclicker.HotkeyNotifier;

public class Controller implements HotkeyListener {

	private AutoClicker autoClicker;

	//the current state of the system
	private volatile State state = State.stopped;

	//anticheat stuff
	private boolean anticheatEnabled;

	//hotkey stuff
	private boolean ignoreHotkeyDelay;
	private int startDelay;


	//hotkey notifier
	private HotkeyNotifier hotkeyNotifier;

	private InfoPanel infoPanel;
	private DelayPanel delayPanel;

	public Controller() {
		autoClicker = new AutoClicker(this);
		hotkeyNotifier = new HotkeyNotifier();
		hotkeyNotifier.registerHotkeyListener(this);
	}

	public void registerInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public void registerDelayPanel(DelayPanel delayPanel) {
		this.delayPanel = delayPanel;
	}

	@Override
	public void hotkeyPressed() {
		if (state == State.stopped) {
			if (!testAndSetDelayBetweenClicks()) return;
			if (ignoreHotkeyDelay) {
				//ignoring delay, so just start instantly
				beginClicking();
			} else {
				//otherwise it's just like pushing the 'start' button
				startPressed();
			}
		} else {
			stopPressed();
		}

	}

	/**
	 * Starts the clicking process.
	 * If clicking is already happening, this does nothing.
	 * If the timer is clicking down, this does nothing.
	 * Otherwise it counts down the timer and once it hits 0 it begins clicking.
	 * This can be interrupted by the stop button.
	 */
	public void startPressed() {
		//only do something if we're currently stopped
		if (state == State.stopped) {
			if (!testAndSetDelayBetweenClicks()) return;

			if (startDelay == 0) beginClicking();
			else beginCountdown();
		}
	}

	/**
	 * Stops the current activity. 
	 * If the program is autoclicking, it stops immediately. 
	 * If the program is currently counting down to an autoclick, it stops immediately.
	 * If the program is currently idle, nothing happens.
	 */
	public void stopPressed() {
		state = State.stopped;
		infoPanel.setInfo("Clicking Stopped");
		infoPanel.setClicks(0);
		autoClicker.stopClicking();
	}

	/**
	 * Sets the start delay.
	 * @param startDelay
	 */
	public void setDelayTime(int startDelay) {
		this.startDelay = startDelay;
	}

	/**
	 * Sets whether when pushing the hotkey combination, the countdown delay is ignored.
	 * @param ignoreDelay
	 */
	public void setHotkeyIgnoreDelay(boolean ignoreDelay) {
		ignoreHotkeyDelay = ignoreDelay;
	}

	/**
	 * Sets if the anticheat measures are enabled.
	 * @param enabled true if enabled, false otherwise
	 */
	public void setAnticheatEnabled(boolean enabled) {
		anticheatEnabled = enabled;
	}

	/**
	 * Sets the anticheat values.
	 * @param spread the spread
	 * @param variation the variation
	 */
	public void setAnticheatValues(int spread, int variation) {
		autoClicker.setAnticheatSpread(spread);
		autoClicker.setAnticheatVariation(variation);
	}

	public void setDelayBetweenClicks(long delayMilli) {
		autoClicker.setClickDelay(delayMilli);
	}

	/**
	 * Checks if the value in the field is valid, and if so sets it and returns true.
	 * Returns false if it's invalid.
	 * 
	 * If an invalid value is present, sets the state to State.stopped and displays an error message.
	 * 
	 * @return true if valid, false otherwise
	 */
	private boolean testAndSetDelayBetweenClicks() {
		int delayBetween = delayPanel.getDelayBetweenClicks();

		if (delayBetween == -1) {
			//was invalid so display to user and return
			infoPanel.setInfo("Invalid delay between clicks");
			state = State.stopped;
			return false;
		} else {
			//was valid so set the var
			setDelayBetweenClicks(delayBetween);

			return true;
		}
	}

	public void setClicks(int clicks) {
		//TODO fix this. 
		//This method is being called before the infoPanel has been registered with this panel.
		//need to fix the order things are instantiated in.
		if (infoPanel != null) infoPanel.setClicks(clicks);
	}

	public void setTime(long millis) {
		//TODO fix this. 
		//This method is being called before the infoPanel has been registered with this panel.
		//need to fix the order things are instantiated in.
		String time = "";
		
		long seconds = millis / 1000;
		long oneths = (millis - seconds * 1000) / 100;
		long tenths = (millis - seconds * 1000 - oneths * 100) / 10;
		long hundreths = (millis - seconds * 1000 - oneths * 100 - tenths * 10);
		
		time = seconds + "." + oneths + tenths + hundreths;
		
		if (infoPanel != null) infoPanel.setTime(time);
	}

	/**
	 * Begins autoclicking immediately. This should be called after any countdowns, etc.
	 */
	private void beginClicking() {
		state = State.clicking;
		infoPanel.setInfo("Clicking Started");

		autoClicker.beginClicking();
	}

	/**
	 * Begins a countdown.
	 */
	private void beginCountdown() {
		state = State.countingDown;

		CountdownWorker a = new CountdownWorker();
		a.execute();
	}

	/**
	 * Represents the current state of the system.
	 *
	 * @author Troy Shaw
	 */
	private enum State {clicking, countingDown, stopped}

	/**
	 * Class needed because of Swing's threading policy. Handles the countdown process.
	 *
	 * @author Troy Shaw
	 */
	private class CountdownWorker extends SwingWorker<Void, String> {
		@Override
		protected Void doInBackground() {
			long start = System.currentTimeMillis();
			//create a copy since the user can change whenever they want
			int seconds = startDelay;	

			while (System.currentTimeMillis() - start < seconds * 1000) {
				if (state == gui.Controller.State.stopped) {
					publish("Clicking Stopped");
					return null;
				}

				try {
					//arbitrary resolution, but gives reasonable control for user
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//ignore
				}

				long val = (seconds * 1000  - (System.currentTimeMillis() - start)) / 1000 + 1;

				publish("Counting down: " + val);
			}

			// The type we pass to publish() is determined
			// by the second template parameter.
			publish();

			return null;
		}

		@Override
		protected void done() {		    	
			beginClicking();
		}

		@Override
		// Can safely update the GUI from this method.
		protected void process(List<String> chunks) {
			String mostRecentValue = chunks.get(chunks.size()-1);

			infoPanel.setInfo(mostRecentValue);
		}
	};
}
