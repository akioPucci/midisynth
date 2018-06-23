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
	float sampleRate = 16000.0F; // pode ser 8000,11025,16000,22050,44100
	int sampleSizeInBits = 16; // pode ser 8,16
	int channels = 1; // pode ser 1,2
	boolean signed = true; // pode ser true,false
	boolean bigEndian = true; // pode ser true,false

	// vetor com 32 tipos de sons (pensando em 32 teclas de teclado)
	// 16000 samples por segundo, aguenta 1 segundo stereo e 2 mono
	// nao sei pq * 4, eu sei q stereo duplica a quantidade de bytes
	// mas anyway, copiei isso ai e funcionou
	byte audioData[][] = new byte[37][16000];
	byte noteBuffer[][] = new byte[37][16000];
	byte outBuffer[] = new byte[16000];
	int numOfKeys = 0;
	int keyEnable[] = new int[32];
	int waveSampleCount;

	Semaphore makewave_sem;
	Semaphore sem;

	public AudioSynth() {// constructor
		// E ELE RETORNARA O VETOR COM TUDO BONITINHO

		// Get the required audio format
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

		// metodo que adquire o sintetizador
		new SynGen().getSyntheticData(audioData);

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

	// This method plays or files the synthetic
	// audio data that has been generated and saved
	// in an array in memory.
	public void playNote(int note) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} // end catch
	}// end playOrFileData

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
						
						
						j++; 
						time = (j / sampleRate);
						if(time == 1) {
							j = 0;
							time = 0;
						}
						
						//calculo das ondas
						sinValue[0] = (Math.sin(2 * Math.PI * 440.00f * time));
						sinValue[1] = (Math.sin(2 * Math.PI * 602.01f  * time));
							
						
						outShortBuffer.put(waveSampleCount, (short)((8000* sinValue[0]) * keyEnable[0]
								+ (8000* sinValue[1]) * keyEnable[1]));
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
					System.out.println("akakak");
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

			byteLength = synDataBuffer[0].length;

			byteBuffer = ByteBuffer.wrap(synDataBuffer[0]); // C4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 261.63f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[1]); // C#4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 277.18f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[2]); // D4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 293.66f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[3]); // D#4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 311.13f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[4]); // E4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 329.63f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[5]); // F4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 349.23f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[6]); // F#4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 369.99f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[7]); // G4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 392.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[8]); // G#4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 415.30f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[9]); // A4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 440.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[10]); // A#4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 466.16f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[11]); // B4
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 493.88f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[12]); // C5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 523.25f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[13]); // C#5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 554.40f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[14]); // D5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 587.30f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[15]); // D#5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 622.30f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[16]); // E5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 659.30f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[17]); // F5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 698.50f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[18]); // F#5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 740.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[19]); // G5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 784.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[20]); // G#5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 830.60f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[21]); // A5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 880.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[22]); // A#5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 932.30f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[23]); // B5
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 987.80f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[24]); // C6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1046.50f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[25]); // C#6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1108.70f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[26]); // D6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1174.70f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[27]); // D#6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1244.50f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[28]); // E6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1318.50f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[29]); // F6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1396.90f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[30]); // F#6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1480.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[31]); // G6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1568.00f);
			/*
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[32]); // G#6 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 1661.20f);
			 * 
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[33]); // A6 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 1760.00f);
			 * 
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[34]); // A#6 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 1864.70f);
			 * 
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[35]); // B6 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 1975.50f);
			 * 
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[36]); // C7 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 2093.00f);
			 * 
			 * byteBuffer = ByteBuffer.wrap(synDataBuffer[37]); // C#7 shortBuffer =
			 * byteBuffer.asShortBuffer(); tones(shortBuffer, 2217.50f);
			 */


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
