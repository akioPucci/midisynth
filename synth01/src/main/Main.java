package main;

import java.awt.RenderingHints.Key;
import java.util.Scanner;

import genius.Genius;
import gui.TelaInicial;
import input.InputKeyboard;
import key.KeyManagement;

/**
 * Main Class starts the program
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinicius
 */
public class Main {

	/**
	 * main method, call the start method
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String args[]) {
		new Main().start();
	}

	/**
	 * start method, call verifyIfNimbusIsInstalled, starts the GUI and create
	 * the keyboard
	 */
	private void start() {
		InputKeyboard.start();
		new TelaInicial();
	}

}
