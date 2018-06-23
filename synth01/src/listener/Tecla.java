package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import synth.AudioSynth;

public class Tecla implements KeyListener {

	private int code;
	private int note;
	private AudioSynth synth;
	private boolean playing;
	private JButton button;

	public Tecla(int code, AudioSynth synth) {
		this.code = code;
		switch (code) {
		case 90:
			this.note = 0;
			break;
		case 83:
			this.note = 1;
			break;
		case 88:
			this.note = 2;
			break;
		case 68:
			this.note = 3;
			break;
		case 67:
			this.note = 4;
			break;
		case 86:
			this.note = 5;
			break;
		case 71:
			this.note = 6;
			break;
		case 66:
			this.note = 7;
			break;
		case 72:
			this.note = 8;
			break;
		case 78:
			this.note = 9;
			break;
		case 74:
			this.note = 10;
			break;
		case 77:
			this.note = 11;
			break;
		case 81:
			this.note = 12;
			break;
		case 50:
			this.note = 13;
			break;
		case 87:
			this.note = 14;
			break;
		case 51:
			this.note = 15;
			break;
		case 69:
			this.note = 16;
			break;
		case 82:
			this.note = 17;
			break;
		case 53:
			this.note = 18;
			break;
		case 84:
			this.note = 19;
			break;
		case 54:
			this.note = 20;
			break;
		case 89:
			this.note = 21;
			break;
		case 55:
			this.note = 22;
			break;
		case 85:
			this.note = 23;
			break;
		case 73:
			this.note = 24;
			break;
		case 57:
			this.note = 25;
			break;
		case 79:
			this.note = 26;
			break;
		case 48:
			this.note = 27;
			break;
		case 80:
			this.note = 28;
			break;
		case 91:
			this.note = 29;
			break;
		case 61:
			this.note = 30;
			break;
		case 93:
			this.note = 31;
			break;
		}
		this.synth = synth;

	}

	public void addJButton(JButton button) {
		this.button = button;
		addListener();
	}

	private void addListener() {
		final ButtonModel b = button.getModel();
		b.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if (b.isPressed() && !playing) {
					play();
				} else if (!b.isPressed() && playing) {
					pause();
				}

			}
		});
	}

	public void play() {
		synth.playNote(note);
		synth.noteOn(note);
		playing = true;
		button.setVisible(false);
	}

	public void pause() {
		playing = false;
		synth.noteOff(note);
		button.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == code && !playing) {
			play();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == code && playing) {
			pause();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not needed
	}

}
