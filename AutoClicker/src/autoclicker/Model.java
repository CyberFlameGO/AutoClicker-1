package autoclicker;

import misc.Globals;

public class Model {
	private int clickDelay;
	private boolean isClicking;
	
	public Model() {
		clickDelay = Globals.DEFAULT_CLICK_DELAY;
	}
	
	public void setClickDelay(int clickDelay) {
		if (clickDelay < 0) throw new IllegalArgumentException();
		this.clickDelay = clickDelay;
	}
	
	public int getClickDelay() {
		return clickDelay;
	}
	
	public void setIsClicking(boolean isClicking) {
		this.isClicking = isClicking;
	}
	
	public boolean isClicking() {
		return isClicking;			
	}
}
