package gui;

import java.awt.event.KeyEvent;

/**
 * Class contains the two enums Modifier and NumKey. 
 * They contain their KeyEvent.VK_* for their press, and their print-friendly representation.
 *
 * @author Troy Shaw
 */
public final class Hotkey {

	/**
	 * The modifier our system is currently using. Should only be changed from the GUI.
	 */
	public static Modifier MODIFIER = Hotkey.Modifier.ctrl;
	
	/**
	 * The number key our system is currently using. Should only be changed from the GUI.
	 */
	public static Numkey NUMBER = Hotkey.Numkey.one;
	
	private Hotkey() {
		//stop instantiation
	}
	
	
	
	public enum Modifier {
		ctrl	(KeyEvent.VK_CONTROL, 	"Ctrl"), 
		shift	(KeyEvent.VK_SHIFT, 	"Shift"), 
		alt		(KeyEvent.VK_ALT, 		"Alt");
		
		public final int keycode;
		public final String toString;
		
		Modifier(int keycode, String toString) {
			this.keycode 	= keycode;
			this.toString 	= toString;
		}
		
		@Override 
		public String toString() {
			return toString;
		}
	}
	
	public enum Numkey {
		one		(KeyEvent.VK_1, "1"),
		two		(KeyEvent.VK_2, "2"),
		three	(KeyEvent.VK_3, "3"),
		four	(KeyEvent.VK_4, "4"),
		five	(KeyEvent.VK_5, "5"),
		six		(KeyEvent.VK_6, "6"),
		seven	(KeyEvent.VK_7, "7"),
		eight	(KeyEvent.VK_8, "8"),
		nine	(KeyEvent.VK_9, "9"),
		zero	(KeyEvent.VK_0, "0");
		
		public final int keycode;
		public final String toString;
		
		Numkey(int keycode, String toString) {
			this.keycode 	= keycode;
			this.toString 	= toString;
		}
		
		@Override 
		public String toString() {
			return toString;
		}
	}
}
