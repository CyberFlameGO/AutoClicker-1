package autoclicker;

import gui.Controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

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
	
	private Controller controller;
	
	//time between clicks in milliseconds
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
	
	private SwingWorker clickerWorker;
	private SwingWorker timerWorker;
	
	private boolean isClicking;
	
	
	/**
	 * Instantiates a new <code>AutoClicker</code> with the default values.
	 */
	public AutoClicker(Controller controller) {
		this.controller = controller;
		
		initialiseVariables();
	}
	
	/**
	 * Initialises the variables.
	 */
	private void initialiseVariables() {
		clickDelay = DEFAULT_CLICK_DELAY;
		variation = DEFAULT_VARIATION;
		spread = DEFAULT_SPREAD;
		ignoreStartDelay = false;
		anticheatDetection = false;
		anticheatOption = AnticheatOption.Default;
		clickDuration = ClickDuration.Forever;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			String title = "Error";
			String message = "<html>Error message 1<br><br>Cannot instantiate java.awt.Robot." +
					"<br>Please give Java sufficient permissions." + 
					"<br><br>Program will now exit.</html>";
			JOptionPane.showMessageDialog(null,  message, title, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		clickerWorker = new ClickerWorker();
		clickerWorker.execute();
		
		timerWorker = new TimerWorker();
		timerWorker.execute();
	}

	//public methods to be called from GUI
	public boolean isRunning() {
		return isClicking;
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

	/**
	 * Starts clicking immediately until stopClicking() is called.
	 */
	public void beginClicking() {
		run();
	}

	public void stopClicking() {
		isClicking = false;		
	}

	private void run() {
		isClicking = true;
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

	/**
	 * Class needed because of Swing's threading policy. Handles the clicking.
	 * <br>
	 * This class is only instantiated once and loops continously.
	 * <br>
	 * It checks if we're clicking/ not clicking and either clicks or loops respectively.
	 *
	 * @author Troy Shaw
	 */
	private class ClickerWorker extends SwingWorker<Void, Integer> {
		@Override
		protected Void doInBackground() {
			int clicks = 0;
			
			while (true) {
				if (!isClicking) {
					//we are stopped, so just wait a little bit and continue
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						//ignore
					}
					
					clicks = 0;
				} else {
					//we are clicking, so click then wait the proper amount
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					clicks++;
					
					
					for (int i = 0; i < 10; i++) {
						try {
							Thread.sleep(clickDelay / 10);
						} catch (InterruptedException e) {
							//ignore
						}
						
						if (!isClicking) {
							//they cancelled during the wait
							clicks = 0;
							break;
						}
					}
					
					if (isClicking) {
						//make up for possible rounding errors due to int arithmetic
						long time = clickDelay - (clickDelay / 10 * 10);
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							//ignore
						}
					}
				
				}
				
				//send the amount of clicks we've done
				publish(clicks);
			}
		}

		@Override
		// Can safely update the GUI from this method.
		protected void process(List<Integer> chunks) {
			//we need to increment our click value, and update the amount of time we've clicked for
			int clicks = chunks.get(chunks.size() - 1);
			
			controller.setClicks(clicks);
		}
	};
	
	/**
	 * Temporary fix until I think of a better way to constantly update time panel.
	 *
	 * @author Troy Shaw
	 */
	private class TimerWorker extends SwingWorker<Void, Long> {
		private static final long SLEEP_TIME = 11;
		
		@Override
		protected Void doInBackground() {
			long lastState = System.currentTimeMillis();
			long difference;
			
			while (true) {
				if (!isClicking) {
					//we are stopped, so just wait a little bit and continue
					lastState = System.currentTimeMillis();
					difference = 0;
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						//ignore
					}
				} else {
					//we are clicking, so update the time
					difference = System.currentTimeMillis() - lastState;
					
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e) {
						//ignore
					}
				}
				
				//send the amount of clicks we've done
				publish(difference);
			}
		}

		@Override
		// Can safely update the GUI from this method.
		protected void process(List<Long> chunks) {
			//we need to increment our click value, and update the amount of time we've clicked for
			long millis = chunks.get(chunks.size() - 1);
			
			controller.setTime(millis);
		}
	};
}