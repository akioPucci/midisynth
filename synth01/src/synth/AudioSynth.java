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
	byte audioData[][] = new byte[32][16001];
	byte noteBuffer[][] = new byte[32][16001];
	byte outBuffer[];
	int numOfKeys = 0;
	int keyEnable[] = new int[32];

	Semaphore makewave_sem;
	Semaphore sem;

	public AudioSynth() {// constructor
		// E ELE RETORNARA O VETOR COM TUDO BONITINHO

		// Get the required audio format
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels,
				signed, bigEndian);

		new SynGen().getSyntheticData(audioData);
		// Get info on the required data line
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
				audioFormat);

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
		}// end catch
	}// end playOrFileData

	public void noteOn(int note) {
		numOfKeys++;
		keyEnable[note] = 1;
		//System.out.println("key on");
		if (sourceDataLine.isRunning() == false) {
			//System.out.println("start listener");
			sourceDataLine.start();
		}
		sourceDataLine.flush();
		makewave_sem.release();
	}

	public void noteOff(int note) {
		try {
			makewave_sem.acquire();
			numOfKeys--;
			keyEnable[note] = 0;
			//System.out.println("key off");
			if (numOfKeys == 0) {
				sourceDataLine.flush();
				sourceDataLine.stop();

			}

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	class SourceThread extends Thread {

		@Override
		public void run() {
			byte auxBuffer[];
			int position = 0;
			int blockSize;
			while (true) {
				try {
					auxBuffer = new byte[1000];
					blockSize = auxBuffer.length;
					for (int i = 0; i < blockSize; i++) {
						makewave_sem.acquire();
						auxBuffer[i] = (byte) ((noteBuffer[0][i + blockSize
								* position]
								* keyEnable[0]
								+ noteBuffer[1][i + blockSize * position]
										* keyEnable[1]
								+ noteBuffer[2][i + blockSize * position]
										* keyEnable[2]
								+ noteBuffer[3][i + blockSize * position]
										* keyEnable[3]
								+ noteBuffer[4][i + blockSize * position]
										* keyEnable[4]
								+ noteBuffer[5][i + blockSize * position]
										* keyEnable[5]
								+ noteBuffer[6][i + blockSize * position]
										* keyEnable[6]
								+ noteBuffer[7][i + blockSize * position]
										* keyEnable[7]
								+ noteBuffer[8][i + blockSize * position]
										* keyEnable[8]
								+ noteBuffer[9][i + blockSize * position]
										* keyEnable[9]
								+ noteBuffer[10][i + blockSize * position]
										* keyEnable[10]
								+ noteBuffer[11][i + blockSize * position]
										* keyEnable[11]
								+ noteBuffer[12][i + blockSize * position]
										* keyEnable[12]
								+ noteBuffer[13][i + blockSize * position]
										* keyEnable[13]
								+ noteBuffer[14][i + blockSize * position]
										* keyEnable[14]
								+ noteBuffer[15][i + blockSize * position]
										* keyEnable[15]
								+ noteBuffer[16][i + blockSize * position]
										* keyEnable[16]
								+ noteBuffer[17][i + blockSize * position]
										* keyEnable[17]
								+ noteBuffer[18][i + blockSize * position]
										* keyEnable[18]
								+ noteBuffer[19][i + blockSize * position]
										* keyEnable[19]
								+ noteBuffer[20][i + blockSize * position]
										* keyEnable[20]
								+ noteBuffer[21][i + blockSize * position]
										* keyEnable[21]
								+ noteBuffer[22][i + blockSize * position]
								* keyEnable[22]
								+ noteBuffer[23][i + blockSize * position]
								* keyEnable[23]
								+ noteBuffer[24][i + blockSize * position]
								* keyEnable[24]
								+ noteBuffer[25][i + blockSize * position]
								* keyEnable[25]
								+ noteBuffer[26][i + blockSize * position]
								* keyEnable[26]
								+ noteBuffer[27][i + blockSize * position]
								* keyEnable[27]
								+ noteBuffer[28][i + blockSize * position]
								* keyEnable[28]
								+ noteBuffer[29][i + blockSize * position]
								* keyEnable[29]
								+ noteBuffer[30][i + blockSize * position]
								* keyEnable[30] + noteBuffer[31][i + blockSize
								* position]
								* keyEnable[31]) / numOfKeys);
						makewave_sem.release();
					}
					outBuffer = auxBuffer;
					//System.out.println(position++);
					sem.release();
					if (blockSize * position >= (int) sampleRate)
						position = 0;
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

					// System.out.println("playing");
					// sem.acquire();
					// System.out.println("stopped");
					// sourceDataLine.drain();
					sourceDataLine.write(outBuffer, 0, outBuffer.length);
					sem.drainPermits();

				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}// end catch

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
			byteBuffer = ByteBuffer.wrap(synDataBuffer[32]); // G#6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1661.20f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[33]); // A6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1760.00f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[34]); // A#6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1864.70f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[35]); // B6
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 1975.50f);

			byteBuffer = ByteBuffer.wrap(synDataBuffer[36]); // C7
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 2093.00f);
			
			byteBuffer = ByteBuffer.wrap(synDataBuffer[37]); // C#7
			shortBuffer = byteBuffer.asShortBuffer();
			tones(shortBuffer, 2217.50f); */
			
			for (int i = 0; i < 32; i++) {
				InputStream byteArrayInputStream = new ByteArrayInputStream(
						synDataBuffer[i]);

				audioInputStream = new AudioInputStream(byteArrayInputStream,
						audioFormat, synDataBuffer[0].length
								/ audioFormat.getFrameSize());

				try {
					audioInputStream.read(noteBuffer[i], 0,
							noteBuffer[i].length);

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

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
				shortBuffer.put((short) (16000 * sinValue));
			}// end for loop
		}// end method tones
	}
}// end outer class AudioSynth01.java
