package synth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
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
	private int oscSelector;
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
		keysEnabled = 0;
		numOfKeys = 38;
		keyEnable = new boolean[numOfKeys];
		
		//componentes
		oscSelector = 0;
		osc = new Oscillator[3];
		osc[0] = new Oscillator("sine",   2, sampleRate);
		osc[1] = new Oscillator("sine",   3, sampleRate);
		osc[2] = new Oscillator("sine",   4, sampleRate);

		
		outputChannel = new AudioChannel[3];
		outputChannel[0] = new AudioChannel(numOfKeys);
		outputChannel[1] = new AudioChannel(numOfKeys);
		outputChannel[2] = new AudioChannel(numOfKeys);
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
	
	private void popKey() {
		keysEnabled--;
	}
	
	private void pushKey() {
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
		pushKey();
		activateKey(note);
		if (sourceDataLine.isRunning() == false) {
			sourceDataLine.start();
		}
		sourceDataLine.flush();
		
		if(getKeysEnabled() == 1)
			input_sem.release();
	}

	/**
	 * stop a note
	 * @param note
	 */
	public void noteOff(int note) {
		try {
			if (getKeysEnabled() == 1) {
				sourceDataLine.stop();
				input_sem.acquire();				
			}
			popKey();
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
	
	public void changeOscillator(int key) {
		int keyboardDirection;
    	if(key == 37)
    		keyboardDirection = -1;
    	else
    		keyboardDirection = 1;
    	
    	if(oscSelector + keyboardDirection == -1)
    		oscSelector = 3;
    	else
    		oscSelector = (oscSelector + keyboardDirection) % 4;
    	
    	switch(oscSelector) {
    	case 0://sine
    		System.out.println("sine set");
    		setOscType(0, "sine");
    		setOscType(1, "sine");
    		setOscType(2, "sine");
    		break;
    	case 1://square
    		System.out.println("square set");
    		setOscType(0, "square");
    		setOscType(1, "square");
    		setOscType(2, "square");
    		break;
    	case 2://triangle
    		System.out.println("triangle set");
    		setOscType(0, "triangle");
    		setOscType(1, "triangle");
    		setOscType(2, "triangle");
    		break;
    	case 3://saw
    		System.out.println(" set");
    		setOscType(0, "saw");
    		setOscType(1, "saw");
    		setOscType(2, "saw");
    		break;
    	}
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
						
						//Oscillator
						outputChannel[0].setKeysOutput(osc[0].oscillate(keyEnable));
						outputChannel[1].setKeysOutput(osc[1].oscillate(keyEnable));
						outputChannel[2].setKeysOutput(osc[2].oscillate(keyEnable));
						
						//mix to output
						mixedSampleBuffer = mixer.mixOutputSample(
								outputChannel[0].getKeysOutput(),
								outputChannel[1].getKeysOutput(),
								outputChannel[2].getKeysOutput());
						outShortBuffer.put(inputBlockCounter, (short) (mixedSampleBuffer));
						
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