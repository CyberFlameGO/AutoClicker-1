package gui;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
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

	public Controller() {
		autoClicker = new AutoClicker();
		hotkeyNotifier = new HotkeyNotifier();
		hotkeyNotifier.registerHotkeyListener(this);
	}

	public void registerInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	@Override
	public void hotkeyPressed() {
		if (state == State.stopped) {
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

//		SwingUtilities.invokeLater(new Thread() {
//			@Override
//			public void run() {
//				try {
//					for (int i = 0; i < startDelay * 10; i++) {
//						if (state == gui.Controller.State.stopped) {
//							infoPanel.setInfo("Clicking Stopped");
//							return;
//						}
//						Thread.sleep(100);
//
//						int val = (startDelay * 1000  - i * 100) / 999;
//
//						infoPanel.setInfo("Counting down: " + val);
//					}
//				} catch (InterruptedException e) {
//					//ignore
//				}
//				beginClicking();
//			}
//		});
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
