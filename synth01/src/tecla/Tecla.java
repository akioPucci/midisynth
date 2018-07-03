package tecla;

import gui.Teclado;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import key.KeyManagement;
import midi.MidiSynth;
import synth.AudioSynth;

/**
 * Synthesizer Components Logic
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */

public class Tecla implements KeyListener {

	private int code;
	private int note;
	private AudioSynth synth;
	private MidiSynth midi;
	private int midiOrSynth;
	private boolean playing;
	private JButton button;
	private boolean recording;
	private List<Long> start;
	private List<Long> end;
	private boolean waitingClick;
	private boolean geniusClicked;
	private Semaphore semaphore;

	public Tecla(int code, AudioSynth synth, MidiSynth midi, int midiOrSynth) {
		this.code = code;
		this.note = KeyManagement.getNote(code);
		this.synth = synth;
		this.midi = midi;
		this.midiOrSynth = midiOrSynth;
		start = new ArrayList<Long>();
		end = new ArrayList<Long>();
		waitingClick = false;
		geniusClicked = false;
	}

	public void addJButton(JButton button) {
		this.button = button;
		addListener();
	}

	/**
	 * chama evento de tecla
	 */
	private void addListener() {
		ButtonModel b = button.getModel();
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
	
	/**
	 * tecla pressionada
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == code && !playing) {
			if(e.getKeyCode() < 40)
				changeInstrument(e.getKeyCode());
			else
				play();
		}
	}

	
	/**
	 * tecla solta
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == code && playing) {
			pause();
		}
	}

	/**
	 * idk
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// not needed
	}

	/**
	 * activates recording
	 */
	private void recordOn() {
		if (isRecording()) {
			start.add(Calendar.getInstance().getTimeInMillis());
		}
	}
	
	/**
	 * stops recording
	 */
	private void recordOff() {
		if (isRecording())
			end.add(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * play note from the synthesizer or midi
	 */
	public void play() {
		if(midiOrSynth == 0)
			midi.noteOn(note);
		else
			synth.noteOn(note);
		playing = true;

		int i = note % 12;
		
		if (i == 1 || i == 3 || i == 6 || i == 8 || i == 10) {
			button.setIcon(Teclado.tecla_preta_press);
		} else {
			button.setIcon(Teclado.tecla_branca_press);
		}

		
		recordOn();
	}

	/**
	 * pause note played
	 */
	public void pause() {
		playing = false;
		
		if(midiOrSynth == 0)
			midi.noteOff(note);
		else
			synth.noteOff(note);
		
		int i = note % 12;
		
		if (i == 1 || i == 3 || i == 6 || i == 8 || i == 10) {
			button.setIcon(Teclado.tecla_preta);
		} else {
			button.setIcon(Teclado.tecla_branca);
		}
		
		recordOff();
		if (waitingClick) {
			if (semaphore == null)
				semaphore = KeyManagement.getSemaphore();
			semaphore.release();
			geniusClicked = true;
			waitingClick = false;
		}
	}
	
	public void changeInstrument(int keyCode) {
			midi.changeInstrument(keyCode);
	}
	
	public void changeStatus() {
		if (playing)
			pause();
		else
			play();
	}

	/**
	 * gets information to record
	 * @return
	 * 			returns a list of start time, end time and note code
	 */
	public List<String> getRecordTimes() {
		List<String> recordTimes = new ArrayList<String>();
		for (int i = 0; i < start.size(); i++) {
			recordTimes.add(start.get(i) + "," + end.get(i) + "," + code);
		}
		return recordTimes;
	}

	/**
	 * set recording parameter
	 * @param recording
	 * 					flag if recording is activated
	 */
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

	public boolean isWaitingClick() {
		return waitingClick;
	}

	public void setWaitingClick(boolean waitingClick) {
		this.waitingClick = waitingClick;
	}

	public boolean isGeniusClicked() {
		return geniusClicked;
	}

	public void setGeniusClicked(boolean geniusClicked) {
		this.geniusClicked = geniusClicked;
	}

	public void setSemaphore(Semaphore semaphore) {
		if (semaphore == null)
			//System.out.println("is null");
		this.semaphore = semaphore;
	}
	
	

}
