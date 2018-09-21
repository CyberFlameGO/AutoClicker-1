package nz.co.troyshaw.autoclicker.gui;

import java.awt.event.KeyEvent;

/**
 * Class contains the two enums Modifier and NumKey. They contain their
 * KeyEvent.VK_* for their press, and their print-friendly representation.
 *
 * @author Troy Shaw
 */
public final class Hotkey {

	public static Modifier MODIFIER = Hotkey.Modifier.ctrl;
	public static Numkey NUMBER = Hotkey.Numkey.one;

	private Hotkey() {
		// stop instantiation
	}

	public static void setModifier(Hotkey.Modifier modifier) {
		Hotkey.MODIFIER = modifier;
	}

	public static void setNumber(Hotkey.Numkey number) {
		Hotkey.NUMBER = number;
	}

	public enum Modifier {
		ctrl(KeyEvent.VK_CONTROL, "Ctrl"),
		shift(KeyEvent.VK_SHIFT, "Shift"),
		alt(KeyEvent.VK_ALT, "Alt");

		private int keycode;
		private String toString;

		Modifier(int keycode, String toString) {
			this.keycode = keycode;
			this.toString = toString;
		}

		public int getKeycode() {
			return keycode;
		}
		
		@Override
		public String toString() {
			return toString;
		}
	}

	public enum Numkey {
		one(KeyEvent.VK_1, "1"), 
		two(KeyEvent.VK_2, "2"),
		three(KeyEvent.VK_3, "3"),
		four(KeyEvent.VK_4, "4"),
		five(KeyEvent.VK_5, "5"),
		six(KeyEvent.VK_6, "6"),
		seven(KeyEvent.VK_7, "7"),
		eight(KeyEvent.VK_8, "8"),
		nine(KeyEvent.VK_9, "9"),
		zero(KeyEvent.VK_0, "0");

		private int keycode;
		private String toString;

		Numkey(int keycode, String toString) {
			this.keycode = keycode;
			this.toString = toString;
		}

		public int getKeycode() {
			return keycode;
		}
		
		@Override
		public String toString() {
			return toString;
		}
	}
}
