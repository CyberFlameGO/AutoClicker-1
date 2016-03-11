package system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.jna.platform.KeyboardUtils;

import gui.Hotkey;

/**
 * Class is responsible for monitoring keys being pressed and send an event in the case keys are.
 *
 * @author Troy Shaw
 */
public class HotkeyNotifier {
	private static long POLL_SLEEP_TIME = 20;

	private HotkeyListener hotkeyListener;
	
	//true if we have registered a dual-press and have already sent a message and there are still 2 keys being held
	private boolean bothKeysHeldPreviously;

	public HotkeyNotifier(HotkeyListener hotkeyListener) {
		this.hotkeyListener = hotkeyListener;
		
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(this::monitorKeys, 0, POLL_SLEEP_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * Method checks the system for currently held keys. 
	 * If the state goes from not having both keys held to having both keys held an event is sent to the system.
	 */
	private void monitorKeys() {
		boolean modifierHeld = KeyboardUtils.isPressed(Hotkey.MODIFIER.keycode);
		boolean numericHeld = KeyboardUtils.isPressed(Hotkey.NUMBER.keycode);
		
		if (bothKeysHeldPreviously) {
			if (!modifierHeld || !numericHeld) bothKeysHeldPreviously = false;
		} else {
			if (modifierHeld && numericHeld) {
				bothKeysHeldPreviously = true;
				hotkeyListener.hotkeyPressed();
			}
		}
	}
}
