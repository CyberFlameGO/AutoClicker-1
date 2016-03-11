package system;

/**
 * The interface represents an object which is notified whenever the hotkey combination is pressed.
 * <br>
 * The keys are repsented as a modifier and a numerical key, both defined in the  <class>Hotkey</class> class.
 * 
 *
 * @author Troy Shaw
 */
public interface HotkeyListener {

	/**
	 * Called when the user pressed both modifier and numerical key simultaneously.
	 */
	public void hotkeyPressed();
}
