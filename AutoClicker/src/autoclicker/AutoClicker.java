package autoclicker;

import autoclicker.workers.ClickerService;
import autoclicker.workers.TimerWorker;
import gui.Controller;

/**
 * Class represents the actual autoclicker.
 * Entire autoclicker functionality and behaviour is managed through this class
 * @author Troy Shaw
 *
 */
public class AutoClicker {

	private Controller controller;
	private Model model;

	private Clicker clicker;
	
	private TimerWorker timerWorker;
	private ClickerService clickerService;
	
	/**
	 * Instantiates a new <code>AutoClicker</code> with the default values.
	 */
	public AutoClicker(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		
		initialiseVariables();
	}
	
	/**
	 * Initialises the variables.
	 */
	private void initialiseVariables() {
		clicker = new Clicker();
		
		clickerService = new ClickerService(clicker, controller);
		
		timerWorker = new TimerWorker(model, controller);
		timerWorker.execute();
	}

	public boolean isRunning() {
		return model.isClicking();
	}

	/**
	 * Starts clicking immediately until stopClicking() is called.
	 */
	public void beginClicking() {
		model.setIsClicking(true);
		clickerService.startClicking(model.getClickDelay());
	}

	public void stopClicking() {
		model.setIsClicking(false);
		clickerService.stopClicking();
	}
}