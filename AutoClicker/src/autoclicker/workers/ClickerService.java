package autoclicker.workers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import autoclicker.Clicker;
import gui.Controller;

public class ClickerService {

	private boolean isClicking;
	
	private Controller controller;
	private Clicker clicker;
	
	private ScheduledExecutorService service;
	
	private ScheduledFuture<?> scheduledFutureClick;
	private int clicksPerformed;
	
	private ScheduledFuture<?> scheduledFutureTimer;
	private long startClickTime;
	
	public ClickerService(Clicker clicker, Controller controller) {
		this.clicker = clicker;
		this.controller = controller;
		
		service = Executors.newSingleThreadScheduledExecutor();
	}
	
	public void startClicking(int clickDelay) {
		if (isClicking) throw new IllegalStateException();
		
		startClickTime = System.currentTimeMillis();
		isClicking = true;
		scheduledFutureClick = service.scheduleAtFixedRate(this::performClick, clickDelay, clickDelay, TimeUnit.MILLISECONDS);
		scheduledFutureTimer = service.scheduleAtFixedRate(this::updateDisplayTime, 0, 11, TimeUnit.MILLISECONDS);
	}
	
	private void performClick() {
		if (!isClicking) return;
		
		clicker.click();
		clicksPerformed++;
		
		// This may actually need to be done on the Swing thread
		controller.setClicks(clicksPerformed);
	}
	
	public void stopClicking() {
		if (!isClicking) throw new IllegalStateException();
		
		isClicking = false;
		clicksPerformed = 0;
		scheduledFutureClick.cancel(false);
		scheduledFutureTimer.cancel(false);
	}
	
	private void updateDisplayTime() {
		long msElapsed = System.currentTimeMillis() - startClickTime;
		controller.setTime(msElapsed);
	}
}
