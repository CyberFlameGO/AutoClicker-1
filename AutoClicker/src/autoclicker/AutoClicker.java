package autoclicker;

import autoclicker.workers.ClickerService;
import gui.Controller;

/**
 * Class represents the actual autoclicker.
 * 
 * @author Troy Shaw
 */
public class AutoClicker {

	private Controller controller;
	private Model model;

	private Clicker clicker;
	private ClickerService clickerService;
	
	public AutoClicker(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		
		initialiseVariables();
	}
	
	private void initialiseVariables() {
		clicker = new Clicker();
		
		clickerService = new ClickerService(clicker, controller);
		
		//timerWorker = new TimerWorker(model, controller);
		//timerWorker.execute();
	}

	public boolean isRunning() {
		return model.isClicking();
	}

	public void beginClicking() {
		model.setIsClicking(true);
		clickerService.startClicking(model.getClickDelay());
	}

	public void stopClicking() {
		model.setIsClicking(false);
		clickerService.stopClicking();
	}
}