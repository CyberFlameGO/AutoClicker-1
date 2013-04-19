package gui;

import com.sun.jna.platform.KeyboardUtils;

import autoclicker.AutoClicker;
import gui.Hotkey.Modifier;
import gui.Hotkey.Numkey;

public class Controller {

	private AutoClicker autoClicker;

	//anticheat stuff
	private boolean anticheatEnabled;

	//hotkey stuff
	private Modifier modifier;
	private Numkey numkey;
	private boolean ignoreHotkeyDelay;

	public Controller() {
		autoClicker = new AutoClicker();
	}

	/**
	 * Initialises and starts the hotkey detection thread.<br>
	 * This should be called at the end of the creation of the main frame to ensure all objects have been 
	 * instantiated first.
	 */
	public void initHotkeyThread() {
		Thread t = new Thread() {
			boolean pressed;

			@Override
			public void run() {
				while (true) {

					if (KeyboardUtils.isPressed(modifier.keycode) && KeyboardUtils.isPressed(numkey.keycode) && !pressed) {
						pressed = true;
						//if we are running, signal we want to stop
						if (autoClicker.isRunning()) {
							autoClicker.stop();
						}
//						} else if (countingDown) {
//							//if we are counting down, stop countdown
//							//stopCountdown();
//						} else {
//							//else we want to start new clicking
//							if (ignoreDelayCheckBox.isSelected()) {
//								//if ignore is deselected, we start
//								startAutoclick();
//							} else {
//
//							}
//						}
					} else {
						pressed = false;
					}

					try {
						//sleep for a bit
						Thread.sleep(10);
					} catch (InterruptedException e) {
						//ignore
					}
				}
			}
		};

		t.start();
	}

	/**
	 * Signals that the hotkey combination was pressed.<p>
	 * The controller will decide the logic of this. 
	 */
	public void hotkeyPressed() {

	}

	public void startPressed() {

	}

	public void stopPressed() {

	}

	public void setHotkeyCombination(Modifier modifier, Numkey numkey) {
		this.modifier = modifier;
		this.numkey = numkey;
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
}
