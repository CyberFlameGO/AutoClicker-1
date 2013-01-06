package autoclicker;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		AutoClicker autoClicker = new AutoClicker();
		new GUI(autoClicker);
	}
}