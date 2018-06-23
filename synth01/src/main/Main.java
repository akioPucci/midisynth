package main;

import input.InputKeyboard;
import key.KeyManagement;
import gui.GUI;

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
		verifyIfNimbusIsInstalled();

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI g = new GUI();
				g.setVisible(true);
				g.setFocusable(true);
				KeyManagement.create(g, g.createJButtonArray());
				InputKeyboard.start();
			}
		});
	}

	/**
	 * Verifiy if the LookAndFeel Nimbus is installed
	 */
	private void verifyIfNimbusIsInstalled() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}
}