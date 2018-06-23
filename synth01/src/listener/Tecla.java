package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import key.KeyManagement;
import synth.AudioSynth;

public class Tecla implements KeyListener {

	private int code;
	private int note;
	private AudioSynth synth;
	private boolean playing;
	private JButton button;

	public Tecla(int code, AudioSynth synth) {
		this.code = code;
		this.note = KeyManagement.getNote(code);
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
