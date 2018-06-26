package tecla;

import gui.Tela_MIDI;

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
 * @author Vinícius
 */

public class Tecla implements KeyListener {

	private int code;
	private int note;
	private AudioSynth synth;
	private MidiSynth midi;
	private boolean playing;
	private JButton button;
	private boolean recording;
	private List<Long> start;
	private List<Long> end;
	private boolean waitingClick;
	private boolean geniusClicked;
	private Semaphore semaphore;

	public Tecla(int code, AudioSynth synth, MidiSynth midi) {
		this.code = code;
		this.note = KeyManagement.getNote(code);
		this.synth = synth;
		this.midi = midi;
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
	
	/**
	 * tecla pressionada
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == code && !playing) {
			System.out.println(e.getKeyCode());
			if(e.getKeyCode() < 40)
				changeInstrument();
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
		//midi.noteOn(note);
		synth.noteOn(note);
		playing = true;
		button.setVisible(false);
		Tela_MIDI.setNotVisible(note);
		//System.out.println("Deixando falso: " + button.isVisible());
		recordOn();
	}

	/**
	 * pause note played
	 */
	public void pause() {
		playing = false;
		
		//midi.noteOff(note);
		synth.noteOff(note);
		button.setVisible(true);
		recordOff();
		if (waitingClick) {
			semaphore.release();
			geniusClicked = true;
			waitingClick = false;
		}
	}
	
	public void changeInstrument() {
		midi.changeInstrument(note);
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
	 * 					flag if recording is actvated
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
		System.out.println("semaforo sendo setado");
		if (semaphore == null)
			System.out.println("is null");
		this.semaphore = semaphore;
	}
	
	

}
