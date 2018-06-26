package key;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;

import midi.MidiSynth;
import record.Record;
import synth.AudioSynth;
import tecla.Tecla;

/**
 * Manage the keys
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */
public class KeyManagement {

	public static Semaphore semaphore;

	private static Record r;

	private static AudioSynth synth = AudioSynth.getAudioSynth();
	private static MidiSynth midi = MidiSynth.getMidiSynth();
	private static Tecla tecla[] = new Tecla[39];

	/**
	 * Create all needed to key management
	 * 
	 * @param g
	 *            the GUI with the keys
	 * @param button
	 *            array of the key JButtons
	 */
	public static void create(JFrame j, JButton[] button) {

		createKeys();
		inicitiateKeyListeners(j);
		addButtons(button);

	}

	/**
	 * starts recording
	 * 
	 * @param wave
	 */
	public static void startRecording(int wave) {
		r = new Record(wave);

		for (int i = 0; i < tecla.length; i++) {
			tecla[i].setRecording(true);
		}
	}

	/**
	 * stops recording, save to a file
	 * 
	 * @param filename
	 *            name of the file
	 */
	public static void stopRecording(String filename) {

		for (int i = 0; i < tecla.length; i++) {
			if (tecla[i].getRecordTimes().size() > 0)
				r.addAllNotes(tecla[i].getRecordTimes());
			tecla[i].setRecording(false);
		}
		r.save(filename);
	}

	/**
	 * play a recorded file
	 * 
	 * @param filename
	 *            name of the file
	 */
	public static void playRecord(String filename) {
		filename += ".csv";
		filename = "gravacoes/" + filename;
		BufferedReader br = null;
		String line = "";

		List<Pair<Long, Integer>> changes = new ArrayList<Pair<Long, Integer>>();

		try {

			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			if (!line.startsWith("Wave number:")) {
				// TODO erro arquivo inv�lido
			}
			// System.out.println(line);
			while ((line = br.readLine()) != null) {

				String[] note = line.split(",");
				if (note.length != 3) {
					// TODO erro arquivo inv�lido
				}
				changes.add(new Pair<Long, Integer>(Long.parseLong(note[0]),
						Integer.parseInt(note[2])));
				changes.add(new Pair<Long, Integer>(Long.parseLong(note[1]),
						Integer.parseInt(note[2])));
			}

			Collections.sort(changes, new Comparator<Pair<Long, Integer>>() {

				@Override
				public int compare(Pair<Long, Integer> p0,
						Pair<Long, Integer> p1) {
					if (p0.getFirst() > p1.getFirst())
						return 1;
					else if (p0.getFirst() < p1.getFirst())
						return -1;
					return p0.getSecond() - p1.getSecond();
				}
			});

			long start = changes.get(0).getFirst();
			for (Pair<Long, Integer> pair : changes) {
				pair.setFirst(pair.getFirst() - start);
			}

			long playStart = Calendar.getInstance().getTimeInMillis();

			while (changes.size() > 0) {

				if ((Calendar.getInstance().getTimeInMillis() - playStart) >= changes
						.get(0).getFirst()) {
					if (changes.get(0).getSecond() < 0) {
						// TODO Erro arquivo inv�lido
					}
					tecla[getNote(changes.get(0).getSecond())].changeStatus();
					changes.remove(0);
				}

			}

		} catch (FileNotFoundException e) {
			//TODO erro arquivo n�o encontrado
			//e.printStackTrace();
		} catch (IOException e) {
			//TODO erro de entrada/saida
			//e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * makes a note plays with a determined time
	 * 
	 * @param code
	 *            code of the note
	 * @param ms
	 *            milisseconds
	 */
	public static void playForMilliseconds(int code, long ms) {
		if (getNote(code) < 0)
			return;
		int note = getNote(code);
		playNote(note);
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pauseNote(note);
		tecla[note].setWaitingClick(true);
	}

	/**
	 * waits for a note do be played
	 * 
	 * @param code
	 */
	public static void waitClick(int code) {

		semaphore = new Semaphore(0);
		if (getNote(code) < 0)
			return;
		tecla[getNote(code)].setSemaphore(semaphore);
		try {
			// System.out.println("Esperando");
			semaphore.acquire();
			// System.out.println("Pronto");

			TimeUnit.MILLISECONDS.sleep(500);

		} catch (Exception e) {
			e.printStackTrace();
		}
		tecla[getNote(code)].setGeniusClicked(false);

	}

	private static void createKeys() {
		tecla[0] = new Tecla(KeyEvent.VK_Z, synth, midi);
		tecla[1] = new Tecla(KeyEvent.VK_S, synth, midi);
		tecla[2] = new Tecla(KeyEvent.VK_X, synth, midi);
		tecla[3] = new Tecla(KeyEvent.VK_D, synth, midi);
		tecla[4] = new Tecla(KeyEvent.VK_C, synth, midi);
		tecla[5] = new Tecla(KeyEvent.VK_V, synth, midi);
		tecla[6] = new Tecla(KeyEvent.VK_G, synth, midi);
		tecla[7] = new Tecla(KeyEvent.VK_B, synth, midi);
		tecla[8] = new Tecla(KeyEvent.VK_H, synth, midi);
		tecla[9] = new Tecla(KeyEvent.VK_N, synth, midi);
		tecla[10] = new Tecla(KeyEvent.VK_J, synth, midi);
		tecla[11] = new Tecla(KeyEvent.VK_M, synth, midi);
		tecla[12] = new Tecla(KeyEvent.VK_Q, synth, midi);
		tecla[13] = new Tecla(KeyEvent.VK_2, synth, midi);
		tecla[14] = new Tecla(KeyEvent.VK_W, synth, midi);
		tecla[15] = new Tecla(KeyEvent.VK_3, synth, midi);
		tecla[16] = new Tecla(KeyEvent.VK_E, synth, midi);
		tecla[17] = new Tecla(KeyEvent.VK_R, synth, midi);
		tecla[18] = new Tecla(KeyEvent.VK_5, synth, midi);
		tecla[19] = new Tecla(KeyEvent.VK_T, synth, midi);
		tecla[20] = new Tecla(KeyEvent.VK_6, synth, midi);
		tecla[21] = new Tecla(KeyEvent.VK_Y, synth, midi);
		tecla[22] = new Tecla(KeyEvent.VK_7, synth, midi);
		tecla[23] = new Tecla(KeyEvent.VK_U, synth, midi);
		tecla[24] = new Tecla(KeyEvent.VK_I, synth, midi);
		tecla[25] = new Tecla(KeyEvent.VK_9, synth, midi);
		tecla[26] = new Tecla(KeyEvent.VK_O, synth, midi);
		tecla[27] = new Tecla(KeyEvent.VK_0, synth, midi);
		tecla[28] = new Tecla(KeyEvent.VK_P, synth, midi);
		tecla[29] = new Tecla(KeyEvent.VK_OPEN_BRACKET, synth, midi);
		tecla[30] = new Tecla(KeyEvent.VK_EQUALS, synth, midi);
		tecla[31] = new Tecla(KeyEvent.VK_CLOSE_BRACKET, synth, midi);
		// TODO verificar teclas com -1
		tecla[32] = new Tecla(-1, synth, midi);
		tecla[33] = new Tecla(-1, synth, midi);
		tecla[34] = new Tecla(-1, synth, midi);
		tecla[35] = new Tecla(-1, synth, midi);
		tecla[36] = new Tecla(-1, synth, midi);
		tecla[37] = new Tecla(KeyEvent.VK_LEFT, synth, midi);
		tecla[38] = new Tecla(KeyEvent.VK_RIGHT, synth, midi);
	
	}

	/**
	 * Add all key listeners
	 * 
	 * @param g
	 */
	private static void inicitiateKeyListeners(JFrame j) {
		for (int i = 0; i < tecla.length; i++)
			j.addKeyListener(tecla[i]);
	}

	/**
	 * Add the buttons to the respective object Tecla
	 * 
	 * @param button
	 *            array of JButtons
	 */
	private static void addButtons(JButton[] button) {
		for (int i = 0; i < tecla.length; i++) {
			if(i < 37)
				tecla[i].addJButton(button[i]);
		}
	}

	/**
	 * Play a note
	 * 
	 * @param note
	 *            to be played
	 */
	public static void playNote(int note) {
		if (note < tecla.length)
			tecla[note].play();
	}

	/**
	 * Pause a note
	 * 
	 * @param note
	 *            to be paused
	 */
	public static void pauseNote(int note) {
		if (note < tecla.length)
			tecla[note].pause();
	}

	public static int getNote(int code) {
		switch (code) {
		case 90:
			return 0;
		case 83:
			return 1;
		case 88:
			return 2;
		case 68:
			return 3;
		case 67:
			return 4;
		case 86:
			return 5;
		case 71:
			return 6;
		case 66:
			return 7;
		case 72:
			return 8;
		case 78:
			return 9;
		case 74:
			return 10;
		case 77:
			return 11;
		case 81:
			return 12;
		case 50:
			return 13;
		case 87:
			return 14;
		case 51:
			return 15;
		case 69:
			return 16;
		case 82:
			return 17;
		case 53:
			return 18;
		case 84:
			return 19;
		case 54:
			return 20;
		case 89:
			return 21;
		case 55:
			return 22;
		case 85:
			return 23;
		case 73:
			return 24;
		case 57:
			return 25;
		case 79:
			return 26;
		case 48:
			return 27;
		case 80:
			return 28;
		case 91:
			return 29;
		case 61:
			return 30;
		case 93:
			return 31;
		}
		return -1;
	}

}