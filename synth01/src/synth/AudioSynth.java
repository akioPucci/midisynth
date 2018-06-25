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
	double[] noteFrequency = new double[38];

	
	private Oscillator osc1;
	private Oscillator osc2;
	private Oscillator osc3;
	private double[] channel1;
	private double[] channel2;
	private double[] channel3;
	private int numOfChannels;
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
	
	
	
	public static AudioSynth getAudioSynth()  {
		
		if (audioSynth == null)
			audioSynth = new AudioSynth();
		
		return audioSynth;
	}

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
		
		//definicao de frequencias
		noteFrequency[0] = 261.63f;
		noteFrequency[1] = 277.18f;
		noteFrequency[2] = 293.66f;
		noteFrequency[3] = 311.13f;
		noteFrequency[4] = 329.63f;
		noteFrequency[5] = 349.23f;
		noteFrequency[6] = 369.99f;
		noteFrequency[7] = 392.00f;
		noteFrequency[8] = 415.30f;
		noteFrequency[9] = 440.00f;
		noteFrequency[10] = 466.16f;
		noteFrequency[11] = 493.88f;
		noteFrequency[12] = 523.25f;
		noteFrequency[13] = 554.40f;
		noteFrequency[14] = 587.30f;
		noteFrequency[15] = 622.30f;
		noteFrequency[16] = 659.30f;
		noteFrequency[17] = 698.50f;
		noteFrequency[18] = 740.00f;
		noteFrequency[19] = 784.00f;
		noteFrequency[20] = 830.60f;
		noteFrequency[21] = 880.00f;
		noteFrequency[22] = 932.30f;
		noteFrequency[23] = 987.80f;
		noteFrequency[24] = 1046.50f;
		noteFrequency[25] = 1108.70f;
		noteFrequency[26] = 1174.70f;
		noteFrequency[27] = 1244.50f;
		noteFrequency[28] = 1318.50f;
		noteFrequency[29] = 1396.90f;
		noteFrequency[30] = 1480.00f;
		noteFrequency[31] = 1568.00f;
		noteFrequency[32] = 1661.20f;
		noteFrequency[33] = 1760.00f;
		noteFrequency[34] = 1864.70f;
		noteFrequency[35] = 1975.50f;
		noteFrequency[36] = 2093.00f;
		noteFrequency[37] = 2217.50f;
		
		//componentes
		osc1 = new Oscillator("sine",   1, sampleRate);
		osc2 = new Oscillator("square", 1, sampleRate);
		osc3 = new Oscillator("sine",   4, sampleRate);
		channel1 = new double[numOfKeys];
		channel2 = new double[numOfKeys];
		channel3 = new double[numOfKeys];
		numOfChannels = 3;
		mixer = new Mixer(numOfChannels);
		
		//block communications
		blockSize = 4000;

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

	//interface with keyboard
	public void noteOn(int note) {
		keysEnabled++;
		keyEnable[note] = true;
		inputBlockCounter = 0;
		if (sourceDataLine.isRunning() == false) {
			sourceDataLine.start();
		}
		sourceDataLine.flush();
		
		if(keysEnabled == 1)
			input_sem.release();
	}

	public void noteOff(int note) {
		try {
			sourceDataLine.flush();
			if (keysEnabled == 1) {
				sourceDataLine.stop();
				input_sem.acquire();				
			}
			keysEnabled--;
			keyEnable[note] = false;
			inputBlockCounter = 0;

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	
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
						//TODO CLICKS
						channel1 = osc1.oscillate(keyEnable);
						channel2 = osc2.oscillate(keyEnable);
						channel3 = osc3.oscillate(keyEnable);
						
						
						//TODO filter sample
						
						//mix channels
						
						//TODO filter channel
						
						//mix to output
						mixedSampleBuffer = mixer.mixOutputSample(channel1, channel2, channel3);
						
						outShortBuffer.put(inputBlockCounter, (short) (2000*mixedSampleBuffer));
						input_sem.release();
					}

					
					//PROCESSANDO DADO EM AUDIO
					byteArrayInputStream = new ByteArrayInputStream(inBuffer);
					audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
							inBuffer.length / audioFormat.getFrameSize());
					try {
						audioInputStream.read(outBuffer);

					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					
					
					//COMUNICACAO COM A SAIDA DE AUDIO
					output_sem.release();
					input_sem.acquire();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}

			}

		}
	}
	
class isSoundOn extends Thread {
		
		@Override
		public void run() {
			
			while(true) {
				
			}
		}
			
	}



	class OutputThread extends Thread {
		public void run() {
			try {
				while (true) {
					output_sem.acquire();

					sourceDataLine.write(outBuffer, 0, outBuffer.length);
					System.out.println("aaa");
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