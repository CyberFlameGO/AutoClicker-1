package autoclicker;

import gui.Hotkey.Modifier;

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

	public final static long DEFAULT_CLICK_DELAY = 1000;
	
	public final static int DEFAULT_SPREAD = 50;
	public final static int DEFAULT_VARIATION = 50;
	
	public final static int MAX_SPREAD = 100;
	public final static int MAX_VARIATION = 100;
	
	//delay when user clicks "Start!" or pushes hotkey without ignoreStartDelay enabled
	private int startDelay;

	//time between clicks
	private long clickDelay;

	//anticheat detection variables
	private int variation, spread;
	
	//if true pushing hotkey will not wait the delay time
	private boolean ignoreStartDelay;

	//cheat detection causes randomness in clicks
	private boolean anticheatDetection;

	//cheat detection option, default or advanced
	private AnticheatOption anticheatOption;



	//click duration... either forever, numClicks or timeDuration
	private ClickDuration clickDuration;

	//the robot that will perform clicking
	private Robot robot;
	
	private Thread currentThread;
	
	private boolean manualStop, isRunning;
	
	
	/**
	 * Instantiates a new <code>AutoClicker</code> with the default values.
	 */
	public AutoClicker() {
		initialiseVariables();
		
	}
	
	/**
	 * Initialises the variables.
	 */
	private void initialiseVariables() {
		clickDelay = DEFAULT_CLICK_DELAY;
		startDelay = 0;
		variation = 50;
		spread = 50;
		ignoreStartDelay = false;
		anticheatDetection = false;
		anticheatOption = AnticheatOption.Default;
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

	public boolean isRunning() {
		return isRunning;
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
		if (spread < 0 || spread > MAX_SPREAD) throw new IllegalArgumentException();
		this.spread = spread;
	}

	public void setAnticheatVariation(int variation) {
		if (variation < 0 || variation > MAX_VARIATION) throw new IllegalArgumentException();
		this.variation = variation;
	}


	//delay between click panel

	public void setClickDelay(long clickDelay) {
		if (clickDelay < 0) throw new IllegalArgumentException();
		this.clickDelay = clickDelay;
	}

	//click duration option panel

	public void setClickDuration(ClickDuration clickDuration) {
		if (clickDuration == null) throw new NullPointerException();
		this.clickDuration = clickDuration;
	}

	//run panel

	public void start() {
		run();
	}

	public void stop() {
		manualStop = true;
		System.out.println("Stopped");		
	}

	private void run() {
		manualStop = false;
		
		currentThread = new Thread() {
			@Override
			public void run() {
				while (!manualStop) {
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					try {
						Thread.sleep(clickDelay);
					} catch (InterruptedException e) {
						//ignore
					}
				}
			}
		};
		
		//currentThread.start();
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

}