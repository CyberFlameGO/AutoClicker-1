package main;

import java.awt.event.KeyEvent;

import autoclicker.HotkeyNotifier;
import gui.MainFrame;

public class Main {
	public static void main(String[] args) {
		//new MainFrame();
		HotkeyNotifier k = new HotkeyNotifier();
		k.setKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_1);
		System.out.println("finished");
	}
}