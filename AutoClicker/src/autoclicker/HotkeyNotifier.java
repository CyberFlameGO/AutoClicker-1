package autoclicker;

import com.sun.jna.platform.KeyboardUtils;

/**
 * Class is responsible for monitoring keys being pressed and send an event in the case keys are.
 *
 * @author Troy Shaw
 */
public class HotkeyNotifier {

	//sleep time before we poll again
	private static long SLEEP_TIME = 20;
	
	private Thread mainThread;
	
	//the keycodes of the keys we are checking for
	private int controlKey, numericKey;

	//true if we have registered a dual-press and have already sent a message and there are still 2 keys being held
	private boolean pressed;

	public HotkeyNotifier() {
		mainThread = new Thread() {
			@Override
			public void run() {
				while (true) {
					monitorKeys();

					try {
						Thread.sleep(SLEEP_TIME);
					} catch(InterruptedException e) {
						//ignore
					}
				}
			}
		};

		mainThread.start();
	}

	/**
	 * Method checks the system for currently held keys. 
	 * If the state goes from not having both keys held to having both keys held an event is sent to the system.
	 */
	private void monitorKeys() {
		boolean controlHeld = KeyboardUtils.isPressed(controlKey);
		boolean numericHeld = KeyboardUtils.isPressed(numericKey);
		
		if (pressed) {
			if (!controlHeld || !numericHeld) pressed = false;
		} else {
			if (controlHeld && numericHeld) {
				pressed = true;
				//notify listener object here
				System.out.println("pressed");
			}
		}
	}

	/**
	 * Updates the numerical key that must be pressed.
	 * @param numericKey
	 */
	public void setNumericKey(int numericKey) {
		this.numericKey = numericKey;
	}

	/**
	 * Updates the control key that must be pressed.
	 * @param controlKey
	 */
	public void setControlKey(int controlKey) {
		this.controlKey = controlKey;
	}

	/**
	 * Sets both keys which must be pressed to generate an event.
	 * @param controlKey
	 * @param numericKey
	 */
	public void setKeys(int controlKey, int numericKey) {
		this.controlKey = controlKey;
		this.numericKey = numericKey;
	}
}
