package key;

import gui.GUI;

import java.awt.event.KeyEvent;
import javax.swing.JButton;

import listener.Tecla;
import synth.AudioSynth;

public class KeyManagement {

	private static AudioSynth synth = AudioSynth.getAudioSynth();
	private static Tecla tecla[] = new Tecla[37];

	public static void create(GUI g, JButton[] button) {

		System.out.println("Execute");
		createKeys();
		inicitiateKeys(g);
		addButtons(button);
	}

	private static void createKeys() {
		tecla[0] = new Tecla(KeyEvent.VK_Z, synth);
		tecla[1] = new Tecla(KeyEvent.VK_S, synth);
		tecla[2] = new Tecla(KeyEvent.VK_X, synth);
		tecla[3] = new Tecla(KeyEvent.VK_D, synth);
		tecla[4] = new Tecla(KeyEvent.VK_C, synth);
		tecla[5] = new Tecla(KeyEvent.VK_V, synth);
		tecla[6] = new Tecla(KeyEvent.VK_G, synth);
		tecla[7] = new Tecla(KeyEvent.VK_B, synth);
		tecla[8] = new Tecla(KeyEvent.VK_H, synth);
		tecla[9] = new Tecla(KeyEvent.VK_N, synth);
		tecla[10] = new Tecla(KeyEvent.VK_J, synth);
		tecla[11] = new Tecla(KeyEvent.VK_M, synth);
		tecla[12] = new Tecla(KeyEvent.VK_Q, synth);
		tecla[13] = new Tecla(KeyEvent.VK_2, synth);
		tecla[14] = new Tecla(KeyEvent.VK_W, synth);
		tecla[15] = new Tecla(KeyEvent.VK_3, synth);
		tecla[16] = new Tecla(KeyEvent.VK_E, synth);
		tecla[17] = new Tecla(KeyEvent.VK_R, synth);
		tecla[18] = new Tecla(KeyEvent.VK_5, synth);
		tecla[19] = new Tecla(KeyEvent.VK_T, synth);
		tecla[20] = new Tecla(KeyEvent.VK_6, synth);
		tecla[21] = new Tecla(KeyEvent.VK_Y, synth);
		tecla[22] = new Tecla(KeyEvent.VK_7, synth);
		tecla[23] = new Tecla(KeyEvent.VK_U, synth);
		tecla[24] = new Tecla(KeyEvent.VK_I, synth);
		tecla[25] = new Tecla(KeyEvent.VK_9, synth);
		tecla[26] = new Tecla(KeyEvent.VK_O, synth);
		tecla[27] = new Tecla(KeyEvent.VK_0, synth);
		tecla[28] = new Tecla(KeyEvent.VK_P, synth);
		tecla[29] = new Tecla(KeyEvent.VK_OPEN_BRACKET, synth);
		tecla[30] = new Tecla(KeyEvent.VK_EQUALS, synth);
		tecla[31] = new Tecla(KeyEvent.VK_CLOSE_BRACKET, synth);
		tecla[32] = new Tecla(-1, synth);
		tecla[33] = new Tecla(-1, synth);
		tecla[34] = new Tecla(-1, synth);
		tecla[35] = new Tecla(-1, synth);
		tecla[36] = new Tecla(-1, synth);
	}

	private static void inicitiateKeys(GUI g) {
		for (int i = 0; i < tecla.length; i++)
			g.addKeyListener(tecla[i]);
	}

	private static void addButtons(JButton[] button) {
		for (int i = 0; i < tecla.length; i++) {
			tecla[i].addJButton(button[i]);
		}
	}
	
	public static void playNoteFromMIDI(int note) {
		tecla[note].play();
	}
	
	public static void pauseNoteFromMIDI(int note) {
		tecla[note].pause();
	}

}