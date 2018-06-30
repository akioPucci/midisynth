package synth;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
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
 * @author Vinícius
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
	private AudioChannel[] outputChannel;
	private Mixer mixer;
	
	//Informações Teclado
	private int keysEnabled;
	private int numOfKeys;
	private boolean keyEnable[];

	//buffer entrada
	private byte inBuffer[];
	
	
	
	//buffers saida
	private byte outBuffer[];
	
	private int blockSize;
	private int inputBlockCounter;

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
		
		
		
		//Informações Teclado
		notesPlaying = new ConcurrentHashMap<Integer, Note>();
		keysEnabled = 0;
		numOfKeys = 38;
		keyEnable = new boolean[numOfKeys];
		
		osc = new Oscillator[3];
		osc[0] = new Oscillator("osc1", "sine",   2, sampleRate);
		osc[1] = new Oscillator("osc2", "square",   2, sampleRate);
		osc[2] = new Oscillator("osc3", "triangle",   2, sampleRate);
		
		
		outputChannel = new AudioChannel[3];
		outputChannel[0] = new AudioChannel();
		outputChannel[1] = new AudioChannel();
		outputChannel[2] = new AudioChannel();
		mixer = new Mixer(outputChannel.length);
		
		//block communications
		blockSize = 1000;

		//buffer entrada
		inBuffer = new byte[blockSize];
		
		//buffers saida
	    outBuffer = new byte[blockSize];
		inputBlockCounter = 0;

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
	
	private int getKeysEnabled() {
		return keysEnabled;
	}
	
	private void popKey(int note) {
		keysEnabled--;
		notesPlaying.remove(note);
		
		/*
		Note n = notesPlaying.remove(note);
		n.setFin();
		
		int shiftMap = 50;
		note = note + shiftMap;
		while(notesPlaying.containsKey(note))
			note = note + shiftMap;
		
		notesPlaying.put(note, n);
		*/
	}
	
	private void pushKey(int note) {
		notesPlaying.put(note, new Note(note));
		keysEnabled++;
	}
	
	private void activateKey(int note) {
		keyEnable[note] = true;
	}
	
	private void desactivateKey(int note) {
		keyEnable[note] = false;
	}

	/**
	 * play a note
	 * @param note
	 */
	public void noteOn(int note) {
		//TODO concurrent access FUCK
		try {
			System.out.println(keyPressed_sem.availablePermits());
			keyPressed_sem.acquire();
			
			pushKey(note);
			activateKey(note);
			if (sourceDataLine.isRunning() == false) {
				sourceDataLine.start();
			}
			sourceDataLine.flush();
			
			if(getKeysEnabled() == 1)
				input_sem.release();
			
		}catch(InterruptedException ie) {ie.printStackTrace();}
	}

	/**
	 * stop a note
	 * @param note
	 */
	public void noteOff(int note) {
		try {
			keyPressed_sem.acquire();

			if (getKeysEnabled() == 1) {
				sourceDataLine.stop();
				input_sem.drainPermits();				
			}
			popKey(note);
			desactivateKey(note);
			sourceDataLine.flush();

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
	
	/**
	 * sets the main volume
	 * @param value
	 */
	public void setVolumeMaster(double value) {
		mixer.setVolumeMaster(value);
	}
	
	/**
	 * sets the type of Oscillator (sine, square, triangle, saw or drawn)
	 * @param oscNum
	 * @param type
	 */
	public void setOscType(int oscNum, String type) {
		osc[oscNum].setType(type);
	}
	
	/**
	 * sets the oscillator amplitude
	 * @param oscNum
	 * @param value
	 */
	public void setOscAmp(int oscNum, double value) {
		osc[oscNum].setAmp(value);
	}
	
	/**
	 * sets the oscillator octave base
	 * @param oscNum
	 * @param octave
	 */
	public void setOscOctave(int oscNum, int octave) {
		osc[oscNum].setOctave(octave);
	}
	
	
	/**
	 * sets the sample to be oscillated
	 * @param oscNum
	 * @param sample
	 */
	public void setOscDrawnSample(int oscNum, double[] sample) {
		osc[oscNum].setDrawnWaveSample(sample);
	}
	

	/**
	 * runs all the input processes, communicates with the output thread
	 * @author Carolina Arenas Okawa
	 * @author Eric
	 * @author Fernando Akio
	 * @author Vinícius
	 */
	class InputThread extends Thread {
		
		//final sample to send to output
		private double mixedSampleBuffer;
		
		//buffers to send to output
		private ByteBuffer outByteBuffer = ByteBuffer.wrap(inBuffer);
		private ShortBuffer outShortBuffer = outByteBuffer.asShortBuffer();
		private int shortBufferSize = blockSize/2;
		
		//audio input stream
		private InputStream byteArrayInputStream;
		private AudioInputStream audioInputStream;
		
		
		@Override
		public void run() {
			
			while (true) {
				try {
					for (inputBlockCounter = 0; inputBlockCounter < shortBufferSize; inputBlockCounter++) {
						input_sem.acquire();
						keyPressed_sem.drainPermits();
						
						//Oscillator
						osc[0].oscillate(notesPlaying);
						osc[1].oscillate(notesPlaying);
						osc[2].oscillate(notesPlaying);
						
						//mix channels
						mixer.mixSynthChannels(notesPlaying);
						
						//mix to output
						mixedSampleBuffer = mixer.mixOutputSample(notesPlaying);
						outShortBuffer.put(inputBlockCounter, (short) (mixedSampleBuffer));
						
						keyPressed_sem.release(Integer.MAX_VALUE);
						input_sem.release();
					}

					
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
					input_sem.acquire();
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
	 * @author Vinícius
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