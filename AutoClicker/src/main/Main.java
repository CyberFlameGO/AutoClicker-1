package main;

import autoclicker.AutoClicker;
import gui.MainFrame;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		AutoClicker autoClicker = new AutoClicker();
		new MainFrame(autoClicker);
	}
}