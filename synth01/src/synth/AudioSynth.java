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

public class AudioSynth extends JFrame {

	// criar sourceDataLine (saida de audio)
	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	SourceDataLine sourceDataLine;

	// parametros de audio
	float sampleRate = 44100.0F; // pode ser 8000,11025,16000,22050,44100
	int sampleSizeInBits = 16; // pode ser 8,16
	int channels = 1; // pode ser 1,2
	boolean signed = true; // pode ser true,false
	boolean bigEndian = true; // pode ser true,false

	// vetor com 32 tipos de sons (pensando em 32 teclas de teclado)
	// 16000 samples por segundo, aguenta 1 segundo stereo e 2 mono
	// nao sei pq * 4, eu sei q stereo duplica a quantidade de bytes
	// mas anyway, copiei isso ai e funcionou
	byte outBuffer[] = new byte[16000];
	int numOfKeys = 0;
	int keyEnable[] = new int[38];
	int waveSampleCount;

	Semaphore makewave_sem;
	Semaphore sem;
	
	double[] note_frequency;

	public AudioSynth() {// constructor
		// Defining notes frequencies
		note_frequency = new double[38];
		
		note_frequency[0] = 261.63f;
		note_frequency[1] = 277.18f;
		note_frequency[2] = 293.66f;
		note_frequency[3] = 311.13f;
		note_frequency[4] = 329.63f;
		note_frequency[5] = 349.23f;
		note_frequency[6] = 369.99f;
		note_frequency[7] = 392.00f;
		note_frequency[8] = 415.30f;
		note_frequency[9] = 440.00f;
		note_frequency[10] = 466.16f;
		note_frequency[11] = 493.88f;
		note_frequency[12] = 523.25f;
		note_frequency[13] = 554.40f;
		note_frequency[14] = 587.30f;
		note_frequency[15] = 622.30f;
		note_frequency[16] = 659.30f;
		note_frequency[17] = 698.50f;
		note_frequency[18] = 740.00f;
		note_frequency[19] = 784.00f;
		note_frequency[20] = 830.60f;
		note_frequency[21] = 880.00f;
		note_frequency[22] = 932.30f;
		note_frequency[23] = 987.80f;
		note_frequency[24] = 1046.50f;
		note_frequency[25] = 1108.70f;
		note_frequency[26] = 1174.70f;
		note_frequency[27] = 1244.50f;
		note_frequency[28] = 1318.50f;
		note_frequency[29] = 1396.90f;
		note_frequency[30] = 1480.00f;
		note_frequency[31] = 1568.00f;
		note_frequency[32] = 1661.20f;
		note_frequency[33] = 1760.00f;
		note_frequency[34] = 1864.70f;
		note_frequency[35] = 1975.50f;
		note_frequency[36] = 2093.00f;
		note_frequency[37] = 2217.50f;
		


		// Get the required audio format
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

		// Get info on the required data line
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

		// Get a SourceDataLine object
		try {
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open();
			makewave_sem = new Semaphore(0);
			SourceThread st = new SourceThread();
			new Thread(st).start();

			sem = new Semaphore(0);
			ListenThread lt = new ListenThread();
			new Thread(lt).start();
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}

	}// end constructor
		// -------------------------------------------//

	public void noteOn(int note) {
		numOfKeys++;
		keyEnable[note] = 1;
		System.out.println("keys on: " + numOfKeys);
		waveSampleCount = 0;
		if (sourceDataLine.isRunning() == false) {
			System.out.println("start listener");
			sourceDataLine.start();
		}
		sourceDataLine.flush();
		
		if(numOfKeys == 1)
			makewave_sem.release();
	}

	public void noteOff(int note) {
		try {
			sourceDataLine.flush();
			if (numOfKeys == 1) {
				sourceDataLine.stop();
				makewave_sem.acquire();				
			}
			numOfKeys--;
			keyEnable[note] = 0;
			System.out.println("key off");
			waveSampleCount = 0;

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	class SourceThread extends Thread {
		
		ByteBuffer outByteBuffer;
		ShortBuffer outShortBuffer;
		int shortBufferSize;
		
		
		byte auxBuffer[] = new byte[16000];
		double sinValue[] = new double[36];
		double channel1SynthData [] = new double [38];
		double channel2SynthData [] = new double [38];
		double channel3SynthData [] = new double [38];
		double mixedChannelSynthData[] = new double[38];
		double mixedSample;
		@Override
		public void run() {
			
			
			
			outByteBuffer = ByteBuffer.wrap(auxBuffer); // C4
			outShortBuffer = outByteBuffer.asShortBuffer();
			int j = 0;
			
			while (true) {
				try {
					shortBufferSize = auxBuffer.length/2;
					double time = 0;
					for (waveSampleCount = 0; waveSampleCount < shortBufferSize; waveSampleCount++) {
						makewave_sem.acquire();
						
						
						//get sample data
						//TODO CLICKS
						j++; 
						time = (j / sampleRate);
						if(time == 1) {
							j = 0;
							time = 0;
						}
						
						channel1SynthData = getSynthData(2, 4, time);
						channel2SynthData = getSynthData(3, 2, time);

						
						//TODO filter sample
						
						//mix channels
						mixedChannelSynthData = mixSynthChannels(channel1SynthData, channel2SynthData, channel3SynthData);
						
						//TODO filter channel
						
						//mix to output
						mixedSample = mixOutputSample(mixedChannelSynthData);
						
						outShortBuffer.put(waveSampleCount, (short) (4000*mixedSample));
						makewave_sem.release();
					}

					
					//PROCESSANDO DADO EM AUDIO
					InputStream byteArrayInputStream = new ByteArrayInputStream(auxBuffer);

					audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat,
							auxBuffer.length / audioFormat.getFrameSize());

					try {
						audioInputStream.read(outBuffer, 0, outBuffer.length);

					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					
					
					//COMUNICACAO COM A SAIDA DE AUDIO
					sem.release();
					makewave_sem.acquire();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}

			}

		}
	}
	
	double squareWave(double rad) {
		double square_function = 0;
		
		if(rad % (2*Math.PI) < Math.PI) {
			square_function = 1;
		}
		
		return square_function;
	}
	
	double triangleWave(double rad) {
		double triangle_function = 0;
		
		if(rad % (2*Math.PI) < Math.PI/2) {
			triangle_function = (rad % (Math.PI/2)) / (Math.PI/2);
		}else if(rad % (2*Math.PI) < Math.PI) {
			triangle_function = 1 - (rad % (Math.PI/2))/ (Math.PI/2);
		}else if(rad % (2*Math.PI) < 3*Math.PI/2) {
			triangle_function = - (rad % (Math.PI/2)) / (Math.PI/2);
		}else if(rad % (2*Math.PI) < 2*Math.PI) {
			triangle_function = (-1 + (rad % (Math.PI/2)) / (Math.PI/2));
		}
		return triangle_function;
	}
	
	double sawWave(double rad) {
		double triangle_function = 0;
		
		if(rad % (2*Math.PI) < Math.PI) {
			triangle_function = (rad % (Math.PI)) / (Math.PI);
		}else if(rad % (2*Math.PI) < 2*Math.PI) {
			triangle_function = (-1 + (rad % (Math.PI)) / (Math.PI));
		}
		return triangle_function;
	}
	
	double [] getSynthData(int synthType,int octave, double time) {
		double[] synthData = new double[38];
		double frequencyMult = 1;
		
		switch(octave) {
		case 0:
			frequencyMult = 0.25;
			break;
		case 1:
			frequencyMult = 0.5;
			break;
		case 2:
			frequencyMult = 1;
			break;
		case 3:
			frequencyMult = 2;
			break;
		case 4:
			frequencyMult = 4;
			break;
		case 5:
			frequencyMult = 8;
		break;
		}
		
		switch(synthType) {
		case 0://SINE
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i] == 1) {
					synthData[i] = (Math.sin(2 * Math.PI * (note_frequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case 1://SQUARE
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i] == 1) {
					synthData[i] = (squareWave(2 * Math.PI * (note_frequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case 2://TRIANGLE
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i] == 1) {
					synthData[i] = (triangleWave(2 * Math.PI * (note_frequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case 3://Saw
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i] == 1) {
					synthData[i] = (sawWave(2 * Math.PI * (note_frequency[i]*frequencyMult) * time));
				}
				
			}
			break;
		}
		
		
		return synthData;
	}
	
	double[] mixSynthChannels(double[] channel1SynthData, double[] channel2SynthData, double[] channel3SynthData) {
		double[] mixedSynthChannel = new double[38];
		for(int i = 0; i < 38; i++) {
			if(keyEnable[i] == 1) {
				mixedSynthChannel[i] = channel1SynthData[i] + channel2SynthData[i] + channel3SynthData[i];
			}
			
		}
		
		return mixedSynthChannel;
	}
	
	double mixOutputSample( double[] noteToMix) {
		double mixedSample = 0;
		
		for(int i = 0; i < 38; i++ ) {
			mixedSample = mixedSample + noteToMix[i];
		}
		
		return mixedSample;
	}


	class ListenThread extends Thread {
		// This is a working buffer used to transfer
		// the data between the AudioInputStream and
		// the SourceDataLine. The size is rather
		// arbitrary.

		public ListenThread() {
		}

		public void run() {
			try {
				while (true) {
					sem.acquire();

					sourceDataLine.write(outBuffer, 0, outBuffer.length);
					sem.drainPermits();
					makewave_sem.release();
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			} // end catch

		}// end run
	}// end inner class ListenThread

	class SynGen {
		ByteBuffer byteBuffer;
		ShortBuffer shortBuffer;
		int byteLength;

		void getSyntheticData(byte[][] synDataBuffer) {
			// Prepare the ByteBuffer and the shortBuffer
			// for use




		}

		void tones(ShortBuffer shortBuffer, double freq) {
			channels = 1;// Java allows 1 or 2
			// Each channel requires two 8-bit bytes per
			// 16-bit sample.
			int bytesPerSamp = 2;
			sampleRate = 16000.0F;
			// Allowable 8000,11025,16000,22050,44100
			int sampLength = byteLength / bytesPerSamp;
			for (int cnt = 0; cnt < sampLength; cnt++) {
				double time = cnt / sampleRate;
				double sinValue = (Math.sin(2 * Math.PI * freq * time));
				shortBuffer.put((short) (8000 * sinValue));
			} // end for loop
		}// end method tones
	}
}// end outer class AudioSynth01.java
