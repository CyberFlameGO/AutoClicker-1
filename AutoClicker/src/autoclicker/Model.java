package autoclicker;

import misc.Globals;

public class Model {
	//time between clicks in milliseconds
	private int clickDelay;

	//anticheat detection variables
	private int variation, spread;

	//cheat detection causes randomness in clicks
	private boolean anticheatDetection;

	//cheat detection option, default or advanced
	private AnticheatOption anticheatOption;

	//click duration... either forever, numClicks or timeDuration
	private ClickDuration clickDuration;
	
	private boolean isClicking;
	
	public Model() {
		initialise();
	}
		
	private void initialise() {
		clickDelay = Globals.DEFAULT_CLICK_DELAY;
		variation = Globals.DEFAULT_VARIATION;
		spread = Globals.DEFAULT_SPREAD;
		anticheatDetection = false;
		anticheatOption = AnticheatOption.Default;
		clickDuration = ClickDuration.Forever;
	}
	
	public boolean isClicking() {
		return isClicking;			
	}
	
	public int getClickDelay() {
		return clickDelay;
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
		if (spread < 0 || spread > Globals.MAX_SPREAD) throw new IllegalArgumentException();
		this.spread = spread;
	}

	public void setAnticheatVariation(int variation) {
		if (variation < 0 || variation > Globals.MAX_VARIATION) throw new IllegalArgumentException();
		this.variation = variation;
	}
	
	//click duration option panel
	public void setClickDuration(ClickDuration clickDuration) {
		if (clickDuration == null) throw new NullPointerException();
		this.clickDuration = clickDuration;
	}
	
	//delay between click panel
	public void setClickDelay(int clickDelay) {
		if (clickDelay < 0) throw new IllegalArgumentException();
		this.clickDelay = clickDelay;
	}
	
	public void setClick(boolean isClicking) {
		this.isClicking = isClicking;
	}
	
	/**
	 * Sets if the anticheat measures are enabled.
	 * @param enabled true if enabled, false otherwise
	 */
	public void setAnticheatEnabled(boolean enabled) {
		//TODO make it interact with AutoClicker object
	}
	
	/**
	 * Sets the anticheat values.
	 * @param spread the spread
	 * @param variation the variation
	 */
	public void setAnticheatValues(int spread, int variation) {
		setAnticheatSpread(spread);
		setAnticheatVariation(variation);
	}
}
