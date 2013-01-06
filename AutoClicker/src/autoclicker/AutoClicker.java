package autoclicker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.sun.jna.platform.KeyboardUtils;

/**
 * Class represents the actual autoclicker.
 * Entire autoclicker functionality and behaviour is managed through this class
 * @author Troy Shaw
 *
 */
public class AutoClicker {

	//delay when user clicks "Start!" or pushes hotkey without ignoreStartDelay enabled
	private int startDelay;

	//time between clicks
	private int secondsClick = 1, milliClick;

	//anticheat detection variables
	private int variation, spread;
	
	//if true pushing hotkey will not wait the delay time
	private boolean ignoreStartDelay;

	//cheat detection causes randomness in clicks
	private boolean anticheatDetection;

	//cheat detection option, default or advanced
	private AnticheatOption anticheatOption;

	private Modifiers modifier;
	private int hotkeyInt;

	//click duration... either forever, numClicks or timeDuration
	private ClickDuration clickDuration;

	//the robot that will perform clicking
	private Robot robot;
	
	private Thread currentThread;
	
	private boolean manualStop;
	
	public AutoClicker() {
		initializeVariables();
		
	}
	
	private void initializeVariables() {
		startDelay = 0;
		secondsClick = 1;
		milliClick = 0;
		variation = 50;
		spread = 50;
		ignoreStartDelay = false;
		anticheatDetection = false;
		anticheatOption = AnticheatOption.Default;
		modifier = Modifiers.values()[0];
		clickDuration = ClickDuration.Forever;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			String title = "Error";
			String message = "<html>Error message 1<br>Cannot instantiate java.awt.Robot" +
					"<br>Please give Java sufficient permissions." + 
					"<br><br>Program will now exit.</html>";
			JOptionPane.showMessageDialog(null,  message, title, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}


	//public methods to be called from GUI

	public void setHotkey(Modifiers hotkey) {
		this.modifier = hotkey;
	}
	
	public void setHotkey(Integer num) {
		hotkeyInt = num;
	}

	public void setHotkeyIgnoreDelay(boolean ignoreStartDelay) {
		this.ignoreStartDelay = ignoreStartDelay;
	}
	
	//anticheat detection panel

	public void setAnticheatDetection(boolean anticheatDetection) {
		this.anticheatDetection = anticheatDetection;
	}

	public void setAnticheatDefault() {
		this.spread = 50;
		this.variation = 50;
	}
	
	public void setAnticheatSpread(int spread) {
		this.spread = spread;
	}

	public void setAnticheatVariation(int variation) {
		this.variation = variation;
	}


	//delay between click panel

	public void setClickDelay(int seconds, int milli) {
		secondsClick = seconds;
		milliClick = milli;
	}

	//click duration option panel

	public void setClickDuration(ClickDuration duration) {
		clickDuration = duration;
	}

	//run panel

	public void runViaButton() {
		run();
	}
	
	public void runViaHotkey() {
		run();
	}

	public void stop() {
		manualStop = true;
	}

	private void run() {
		manualStop = false;
		
		currentThread = new Thread() {
			@Override
			public void run() {
				while (!KeyboardUtils.isPressed(modifier.keycode) && !manualStop) {
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					try {
						Thread.sleep(secondsClick * 1000 + milliClick);
					} catch (InterruptedException e) {
						//ignore
					}
				}
			}
		};
		
		currentThread.start();
	}
	
	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	//some enums

	private enum AnticheatOption {
		Default,
		Advanced
	}

	private enum ClickDuration {
		Forever,
		NumberOfClicks,
		TimeDuration
	}

	public enum Modifiers {
		Ctrl(KeyEvent.VK_CONTROL), Shift(KeyEvent.VK_SHIFT), Alt(KeyEvent.VK_ALT);
		
		public final int keycode;
		
		Modifiers(int keycode) {
			this.keycode = keycode;
		}
	}
}
