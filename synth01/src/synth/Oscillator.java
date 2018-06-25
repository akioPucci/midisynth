package synth;

public class Oscillator {
	
	private String type;
	private double frequencyMult;
	double[] noteFrequency;
	
	private double time;
	private int sampleCounter;
	private float sampleRate;
	
	public Oscillator(String type, int octave, float sampleRate) {
		this.type = type;
		
		
		time = 0;
		this.sampleCounter = 0;
		this.sampleRate = sampleRate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getFrequencyMult() {
		return frequencyMult;
	}

	public void setFrequencyMult(double frequencyMult) {
		this.frequencyMult = frequencyMult;
	}

	public double[] getNoteFrequency() {
		return noteFrequency;
	}

	public void setNoteFrequency(double[] noteFrequency) {
		this.noteFrequency = noteFrequency;
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
	
	public void countTime() {
		time = (sampleCounter / sampleRate);
		sampleCounter++;
	}

	double [] oscillate(boolean[] keyEnable) {
		double[] synthData = new double[38];
		
		
		switch(type) {
		case "sine":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = (Math.sin(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "square":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = (squareWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "triangle":
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = (triangleWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
			
		case "saw"://Saw
			for(int i = 0; i < 38; i++) {
				if(keyEnable[i]) {
					synthData[i] = (sawWave(2 * Math.PI * (noteFrequency[i]*frequencyMult) * time));
				}
				
			}
			break;
		}
		
		countTime();
		
		return synthData;
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

}
