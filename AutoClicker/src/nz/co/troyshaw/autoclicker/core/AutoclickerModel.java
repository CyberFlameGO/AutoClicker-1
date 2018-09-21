package nz.co.troyshaw.autoclicker.core;

public class AutoclickerModel {

	private int msBetweenClicks;
	private boolean isClicking;

	public AutoclickerModel(int msBetweenClicks) {
		this.msBetweenClicks = msBetweenClicks;
	}

	public void setTimeBetweenClicks(int msBetweenClicks) {
		if (msBetweenClicks < 0) throw new IllegalArgumentException();
		this.msBetweenClicks = msBetweenClicks;
	}

	public int getTimeBetweenClicks() {
		return msBetweenClicks;
	}

	public void setIsClicking(boolean isClicking) {
		this.isClicking = isClicking;
	}

	public boolean isClicking() {
		return isClicking;
	}
}
