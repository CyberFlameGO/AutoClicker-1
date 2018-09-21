package nz.co.troyshaw.autoclicker.system;

/**
 * The interface represents an object which is notified whenever the hotkey
 * combination is pressed.
 *
 * The keys are represented as a modifier and a numerical key, both defined in
 * the <class>Hotkey</class> class.
 *
 * @author Troy Shaw
 */
public interface HotkeyListener {

	/**
	 * Called when the user pressed both modifier and numerical key simultaneously.
	 */
	public void hotkeyPressed();
}
