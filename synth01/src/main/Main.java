package main;

import java.awt.RenderingHints.Key;
import java.util.Scanner;

import genius.Genius;
import gui.Tela_Inicial;
import input.InputKeyboard;
import key.KeyManagement;

/**
 * Main Class starts the program
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinícius
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
		
		new Tela_Inicial();
		//verifyIfNimbusIsInstalled();
/*
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI g = new GUI();
				g.setVisible(true);
				g.setFocusable(true);
				KeyManagement.create(g, g.createJButtonArray());
				InputKeyboard.start();
				
				
			}
		});
		
		Scanner s = new Scanner(System.in);
		System.out.println("Record?");
		s.nextLine();
		KeyManagement.startRecording(0);
		System.out.println("Pause?");
		String l = s.nextLine();
		KeyManagement.stopRecording(l);
		System.out.println("Genius?");
		Genius.startGenius(s.nextLine());*/
		
	}

}
