package main;

import autoclicker.AutoClicker;
import gui.GUI;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		AutoClicker autoClicker = new AutoClicker();
		new GUI(autoClicker);
	}
}