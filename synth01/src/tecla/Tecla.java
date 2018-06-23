package tecla;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	private boolean recording;
	private List<Long> start;
	private List<Long> end;

	public Tecla(int code, AudioSynth synth) {
		this.code = code;
		this.note = KeyManagement.getNote(code);
		this.synth = synth;
		start = new ArrayList<Long>();
		end = new ArrayList<Long>();
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

	private void recordOn() {
		if (isRecording()) {
			start.add(Calendar.getInstance().getTimeInMillis());
		}
	}
	
	private void recordOff() {
		if (isRecording())
			end.add(Calendar.getInstance().getTimeInMillis());
	}

	public void play() {
		synth.playNote(note);
		synth.noteOn(note);
		playing = true;
		button.setVisible(false);
		recordOn();
	}

	public void pause() {
		playing = false;
		synth.noteOff(note);
		button.setVisible(true);
		recordOff();
	}
	
	public void changeStatus() {
		if (playing)
			pause();
		else
			play();
	}

	public List<String> getRecordTimes() {
		List<String> recordTimes = new ArrayList<String>();
		for (int i = 0; i < start.size(); i++) {
			recordTimes.add(start.get(i) + "," + end.get(i) + "," + code);
		}
		return recordTimes;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
		
		if (recording == false) {
			start = new ArrayList<Long>();
			end = new ArrayList<Long>();
		}
	}

	public boolean isRecording() {
		return recording;
	}

}
