package synth;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;

@SuppressWarnings("serial")

/**
 * Synthesizer Components Logic
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */

public class AudioSynth extends JFrame {

	private static AudioSynth audioSynth;
	
	// criar sourceDataLine (saida de audio)
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;

	// parametros de audio
	private float sampleRate; // pode ser 8000,11025,16000,22050,44100
	private int sampleSizeInBits; // pode ser 8,16
	private int channels; // pode ser 1,2
	private boolean signed; // pode ser true,false
	private boolean bigEndian; // pode ser true,false
	
	//componentes
	//TODO implementar hash de notas
	private ConcurrentHashMap<Integer, Note> notesPlaying;
	private Oscillator[] osc;
	private Envelope env;
	private Mixer mixer;
	
	//Informa��es Teclado
	private int keysEnabled;

	//buffer entrada
	private byte inBuffer[];
	
	
	
	//buffers saida
	private byte outBuffer[];
	
	private int blockSize;

	//semaforos
	private Semaphore input_sem;
	private Semaphore keyPressed_sem;
	private Semaphore output_sem;
	
	
	/**
	 * static method, creates the object
	 */
	public static AudioSynth getAudioSynth()  {
		
		if (audioSynth == null)
			audioSynth = new AudioSynth();
		
		return audioSynth;
	}

	/**
	 * constructor, initialize all synthesizer parameters
	 */
	private AudioSynth() {
		
		// parametros de audio
		sampleRate = 44100.0F; // pode ser 8000,11025,16000,22050,44100
		sampleSizeInBits = 16; // pode ser 8,16
		channels = 1; // pode ser 1,2
		signed = true; // pode ser true,false
		bigEndian = true; // pode ser true,false
		
		
		
		//Informa��es Teclado
		notesPlaying = new ConcurrentHashMap<Integer, Note>();
		keysEnabled = 0;
		
		osc = new Oscillator[3];
		osc[0] = new Oscillator("osc1", "sine",   2, sampleRate);
		osc[1] = new Oscillator("osc2", "square",   2, sampleRate);
		osc[2] = new Oscillator("osc3", "triangle",   2, sampleRate);
		
		env = new Envelope(sampleRate, 0.005, 0.001, 1, 1);
		
		//3 sound channels
		mixer = new Mixer(3);
		
		//block communications
		blockSize = 2;

		//buffer entrada
		inBuffer = new byte[blockSize];
		
		//buffers saida
	    outBuffer = new byte[blockSize];

		//semaforos
		input_sem = new Semaphore(0);
		output_sem = new Semaphore(0);
		keyPressed_sem = new Semaphore(Integer.MAX_VALUE);
		
		// Configurando formato de audio
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

		// Configurando saida de audio
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		
		// Iniciando Threads de Input e OutPut
		try {
			InputThread it = new InputThread();
			new Thread(it).start();

			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open();
			OutputThread ot = new OutputThread();
			new Thread(ot).start();
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}

	}

	/**
	 * @return
	 * 			number of keys in action
	 */
	private int getKeysEnabled() {
		return keysEnabled;
	}
	
	/**
	 * sets number of keys in action
	 * @param n
	 * 			number of keys
	 */
	private void setKeysEnabled(int n) {
		this.keysEnabled = n;
	}
	
	/**
	 * pop keys from the pressed table and put them in release state
	 * @param note
	 * 				note number (from the piano row)
	 */
	private void popKey(int note) {
		Note n = notesPlaying.remove(note);
		n.setEnvState(3);
		
		int shiftMap = 50;
		note = note + shiftMap;
		while(notesPlaying.containsKey(note))
			note = note + shiftMap;
		
		notesPlaying.put(note, n);

	}
	
	/**
	 * pushes key to the pressed table
	 * @param note
	 * 				note number (from piano row)
	 */
	private void pushKey(int note) {
		notesPlaying.put(note, new Note(note));
		setKeysEnabled(getKeysEnabled()+1);;
	}
	
	/**
	 * play a note
	 * @param note
	 * 				note number (from piano row)
	 */
	public void noteOn(int note) {
		try {
			keyPressed_sem.acquire();
			pushKey(note);
			
			sourceDataLine.flush();
			
			if (sourceDataLine.isRunning() == false) {
				sourceDataLine.start();
			}
			
			if(getKeysEnabled() == 1) {
				input_sem.release();
			}
			
		}catch(InterruptedException ie) {ie.printStackTrace();}
	}

	/**
	 * stop a note
	 * @param note
	 * 				note number (from piano row)
	 */
	public void noteOff(int note) {
		try {
			keyPressed_sem.acquire();
			
			popKey(note);
		}catch(InterruptedException ie) {ie.printStackTrace();}

	}
	
	/**
	 * kill dead notes (ie. a note that has stopped playing)
	 * @param notesPlaying
	 * 						table of notes playing
	 */
	public void killDeadNotes(ConcurrentHashMap<Integer, Note> notesPlaying) {
		for(Map.Entry<Integer, Note> entry : notesPlaying.entrySet()) {
			int key = entry.getKey();
		    Note note = entry.getValue();
		    
		    if(note.isFin()) {
		    	notesPlaying.remove(key);
		    	setKeysEnabled(getKeysEnabled()-1);
		    }
		    
		}
		if (getKeysEnabled() == 0) {
			System.out.println("nenhuma nota tocando");
			sourceDataLine.stop();
			input_sem.drainPermits();				
		}
		
	}
	
	/**
	 * sets the main volume
	 * @param value
	 * 				(0 to 1)
	 */
	public void setVolumeMaster(double value) {
		mixer.setVolumeMaster(value);
	}
	
	/**
	 * sets the type of Oscillator (sine, square, triangle, saw or drawn)
	 * @param oscNum
	 * 				number of the oscillator
	 * @param type
	 * 				type of oscillator
	 */
	public void setOscType(int oscNum, String type) {
		osc[oscNum].setType(type);
	}
	
	/**
	 * sets the oscillator amplitude
	 * @param oscNum
	 * 				number of the oscillator
	 * @param value
	 * 				amplitude from oscillator
	 */
	public void setOscAmp(int oscNum, double value) {
		osc[oscNum].setAmp(value);
	}
	
	/**
	 * sets the oscillator octave base
	 * @param oscNum
	 * 				number of the oscillator
	 * @param octave
	 * 				number of the octave
	 */
	public void setOscOctave(int oscNum, int octave) {
		osc[oscNum].setOctave(octave);
	}
	
	
	/**
	 * sets the sample to be oscillated
	 * @param oscNum
	 * 				number of the oscillator
	 * @param sample
	 * 				array of samples from the drawn wave
	 */
	public void setOscDrawnSample(int oscNum, double[] sample) {
		osc[oscNum].setDrawnWaveSample(sample);
	}
	
	/**
	 * sets attack time
	 * @param attackTime
	 * 				time in milisseconds
	 */
	public void setAttackEnvTime(double attackTime) {
		env.setAttackTime(attackTime);
	}
	
	/**
	 * sets decay time
	 * @param decayTime
	 * 				time in milisseconds
	 */
	public void setDecayEnvTime(double decayTime) {
		env.setDecayTime(decayTime);
	}
	
	/**
	 * sets sustain amplitude
	 * @param sustainAmp
	 * 				amplitude (0 to 1)
	 */
	public void setSustainEnvAmp(double sustainAmp) {
		env.setSustainAmp(sustainAmp);
	}
	
	/**
	 * sets release time
	 * @param releaseTime
	 * 				time in milisseconds
	 */
	public void setReleaseEnvTime(double releaseTime) {
		env.setReleaseTime(releaseTime);
	}

	/**
	 * runs all the input processes, communicates with the output thread
	 * @author Carolina Arenas Okawa
	 * @author Eric
	 * @author Fernando Akio
	 * @author Vin�cius
	 */
	class InputThread extends Thread {
		
		//final sample to send to output
		private double mixedSampleBuffer;
		
		//buffers to send to output
		private ByteBuffer outByteBuffer = ByteBuffer.wrap(inBuffer);
		private ShortBuffer outShortBuffer = outByteBuffer.asShortBuffer();
		
		//audio input stream
		private InputStream byteArrayInputStream;
		private AudioInputStream audioInputStream;
		
		
		@Override
		public void run() {
			
			while (true) {
				try {
					input_sem.acquire();
					
					/*REGIAO CRITICA*/
					keyPressed_sem.drainPermits();
					
					//Oscillator
					osc[0].oscillate(notesPlaying);
					osc[1].oscillate(notesPlaying);
					osc[2].oscillate(notesPlaying);
					
					//mix channels
					mixer.mixSynthChannels(notesPlaying);
					
					//envelope
					env.apply(notesPlaying);
					
					//mix to output
					mixedSampleBuffer = mixer.mixOutputSample(notesPlaying);
					outShortBuffer.put(0,(short) (mixedSampleBuffer));
					

					
					
					keyPressed_sem.release(Integer.MAX_VALUE);
				
					
					//processing audio
					byteArrayInputStream = new ByteArrayInputStream(inBuffer);
					audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
							inBuffer.length / audioFormat.getFrameSize());
					try {
						audioInputStream.read(outBuffer, 0, outBuffer.length);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					
					
					//I/O communication
					output_sem.release();
					
					//get rid of dead notes
					killDeadNotes(notesPlaying);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}

			}

		}
	}
	


	/**
	 * communicates with the speaker, communicates with the input thread
	 * @author Carolina Arenas Okawa
	 * @author Eric
	 * @author Fernando Akio
	 * @author Vin�cius
	 */
	class OutputThread extends Thread {
		public void run() {
			try {
				while (true) {
					output_sem.acquire();

					sourceDataLine.write(outBuffer, 0, outBuffer.length);
					
					output_sem.drainPermits();
					input_sem.release();
					//TODO ouvir constantemente 
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			} 
		}
	}
	
	

}