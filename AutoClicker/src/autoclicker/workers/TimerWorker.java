package autoclicker.workers;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import autoclicker.Model;
import gui.Controller;

/**
 * Temporary fix until I think of a better way to constantly update time panel.
 *
 * @author Troy Shaw
 */
public class TimerWorker extends SwingWorker<Void, Long> {
	private static final long SLEEP_TIME = 11;
	
	private Controller controller;
	private Model model;
	
	public TimerWorker(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
	}
	
	@Override
	protected Void doInBackground() {
		long lastState = System.currentTimeMillis();
		long difference;
		
		while (true) {
			if (!model.isClicking()) {
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
			//updateGUI(difference);
			//System.out.println(SwingUtilities.isEventDispatchThread());
		}
	}

	@Override
	// Can safely update the GUI from this method.
	protected void process(List<Long> chunks) {
		//we need to increment our click value, and update the amount of time we've clicked for
		long millis = chunks.get(chunks.size() - 1);
		
		controller.setTime(millis);
		
		//System.out.println(SwingUtilities.isEventDispatchThread());
	}
	
	public void updateGUI(final long millis) {
		   if (!SwingUtilities.isEventDispatchThread()) {
		     SwingUtilities.invokeLater(new Runnable() {
		       @Override
		       public void run() {
		    	   //controller.setTime(millis);
		    	   System.out.println("asd" + SwingUtilities.isEventDispatchThread());
		       }
		     });
		   }
		   //Now edit your gui objects
		   //controller.setTime(millis);
		}
};