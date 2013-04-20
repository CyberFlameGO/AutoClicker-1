package autoclicker;

import gui.Hotkey;

import com.sun.jna.platform.KeyboardUtils;

/**
 * Class is responsible for monitoring keys being pressed and send an event in the case keys are.
 *
 * @author Troy Shaw
 */
public class HotkeyNotifier {
	//sleep time before we poll again
	private static long SLEEP_TIME = 20;
	
	//thread the polling is done on
	private Thread mainThread;

	//the listener object that is sent the message when both keys are held
	private HotkeyListener hotkeyListener;
	
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
	 * Registers the given HotkeyListener with this class. The given object will be notified when a combination is pressed.
	 * @param hotkeyListener
	 */
	public void registerHotkeyListener(HotkeyListener hotkeyListener) {
		this.hotkeyListener = hotkeyListener;
	}

	/**
	 * Method checks the system for currently held keys. 
	 * If the state goes from not having both keys held to having both keys held an event is sent to the system.
	 */
	private void monitorKeys() {
		boolean modifierHeld = KeyboardUtils.isPressed(Hotkey.MODIFIER.keycode);
		boolean numericHeld = KeyboardUtils.isPressed(Hotkey.NUMBER.keycode);
		
		if (pressed) {
			if (!modifierHeld || !numericHeld) pressed = false;
		} else {
			if (modifierHeld && numericHeld) {
				pressed = true;
				if (hotkeyListener != null) hotkeyListener.hotkeyPressed();
			}
		}
	}
}
