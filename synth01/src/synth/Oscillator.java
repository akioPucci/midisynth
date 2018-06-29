package synth;

import java.util.HashMap;
import java.util.Map;


/**
 * Oscillator, contains 5 types of oscillator,
 *  sine, square, triangle, saw, and drawn
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinícius
 */
public class Oscillator {
	
	private String name;
	private String type;
	private int octave;
	private double frequencyMult;
	double[] noteFrequency;
	double amp;
	
	private double time;
	private int sampleCounter;
	private float sampleRate;
	
	private double[] drawnWaveSample;
	
	public Oscillator(String name, String type, int octave, float sampleRate) {
		this.name = name;
		this.type = type;
		
		this.octave = octave;
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
		
		noteFrequency = new double[38];
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
		
		amp = 0.5;
		
		time = 0;
		this.sampleCounter = 0;
		this.sampleRate = sampleRate;
		
		//drawn wave
		drawnWaveSample = new double[1000];
	}
	
	public String getName() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
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
	}

	public double[] getNoteFrequency() {
		return noteFrequency;
	}

	public void setNoteFrequency(double[] noteFrequency) {
		this.noteFrequency = noteFrequency;
	}
	
	public double getAmp() {
		return amp;
	}
	
	public void setAmp(double value) {
		System.out.println("amplificacao: " + value);
		this.amp = value;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}
	
	/**
	 * counts the oscillator time to simulate his behavior
	 */
	public void countTime() {
		time = (sampleCounter / sampleRate);
		sampleCounter++;
		if(sampleCounter == 30000);
	}
	
	/**
	 * gets the sampled data
	 * @param sample
	 */
	public void setDrawnWaveSample(double[] sample) {
		this.drawnWaveSample = sample;
	}

	
	/**
	 * returns a array of samples(for each keyboard key)
	 * the oscillator differs, depending on the type of oscillator
	 * @param keyEnable
	 * @return
	 */
	double [] oscillate(boolean[] keyEnable) {
		double[] synthData = new double[38];
		
		
		switch(type) {
		case "sine":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = getAmp()*(Math.sin(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "square":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = getAmp()*(squareWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "triangle":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = getAmp()*(triangleWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "saw":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = getAmp()*(sawWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "drawn":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = getAmp()*(drawnWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
		}
		
		countTime();
		
		return synthData;
	}
	
	void oscillate(HashMap<Integer, Note> notesPlaying) {
		for(Map.Entry<Integer, Note> entry : notesPlaying.entrySet()) {
		    Note note = entry.getValue();
		    
		    switch(type) {
			case "sine":
				note.setChannelSample(getName(), getAmp()*(Math.sin(2 * Math.PI * (noteFrequency[note.getNote()]*frequencyMult) * time))); 
				break;
				
			case "square":
				note.setChannelSample(getName(), getAmp()*(squareWave(2 * Math.PI * (noteFrequency[note.getNote()]*frequencyMult) * time))); 
				break;
				
			case "triangle":
				note.setChannelSample(getName(), getAmp()*(triangleWave(2 * Math.PI * (noteFrequency[note.getNote()]*frequencyMult) * time))); 
				break;
				
			case "saw":
				note.setChannelSample(getName(), getAmp()*(sawWave(2 * Math.PI * (noteFrequency[note.getNote()]*frequencyMult) * time))); 
				break;
				
			case "drawn":
				note.setChannelSample(getName(), getAmp()*(drawnWave(2 * Math.PI * (noteFrequency[note.getNote()]*frequencyMult) * time))); 
			}

		}
		countTime();
		return;
	}
	
	/**
	 * square function
	 * @param rad
	 * @return
	 */
	double squareWave(double rad) {
		double square_function = 0;
		
		if(rad % (2*Math.PI) < Math.PI) {
			square_function = 1;
		}
		
		return square_function;
	}
	
	/**
	 * triangle function
	 * @param rad
	 * @return
	 */
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
	
	/**
	 * saw wave function
	 * @param rad
	 * @return
	 */
	double sawWave(double rad) {
		double saw_function = 0;
		
		if(rad % (2*Math.PI) < Math.PI) {
			saw_function = (rad % (Math.PI)) / (Math.PI);
		}else if(rad % (2*Math.PI) < 2*Math.PI) {
			saw_function = (-1 + (rad % (Math.PI)) / (Math.PI));
		}
		return saw_function;
	}
	
	/**
	 * mapping of the sampled data into a 2*PI interval, 
	 * simulates a approximated function of the sampled wave
	 * @param rad
	 * @return
	 */
	double drawnWave(double rad) {
		return drawnWaveSample[(int)((rad % 2*Math.PI) * (1000/(2*Math.PI)))];
	}
}
