package nz.co.troyshaw.autoclicker.core;

import nz.co.troyshaw.autoclicker.core.workers.ClickerService;
import nz.co.troyshaw.autoclicker.gui.Controller;

/**
 * Class represents the actual autoclicker.
 * 
 * @author Troy Shaw
 */
public class AutoClicker {

	private Controller controller;
	private AutoclickerModel model;

	private Clicker clicker;
	private ClickerService clickerService;
	
	public AutoClicker(AutoclickerModel model, Controller controller) {
		this.model = model;
		this.controller = controller;
		
		initialiseVariables();
	}
	
	private void initialiseVariables() {
		clicker = new Clicker();
		
		clickerService = new ClickerService(clicker, controller);
	}

	public boolean isRunning() {
		return model.isClicking();
	}

	public void beginClicking() {
		model.setIsClicking(true);
		clickerService.startClicking(model.getTimeBetweenClicks());
	}

	public void stopClicking() {
		model.setIsClicking(false);
		clickerService.stopClicking();
	}
}
