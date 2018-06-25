package synth;

public class NoteSound {

	private double frequency;
	private double frequencyMult;
	private double currentTime;
	private int sampleCounter;
	private float sampleRate;
	
	
	public NoteSound(double frequency, int octave, float sampleRate) {
		this.frequency = frequency;
		
		switch(octave) {
		case 0:
			this.frequencyMult = 0.25;
			break;
		case 1:
			this.frequencyMult = 0.5;
			break;
		case 2:
			this.frequencyMult = 1;
			break;
		case 3:
			this.frequencyMult = 2;
			break;
		case 4:
			this.frequencyMult = 4;
			break;
		case 5:
			this.frequencyMult = 8;
		break;
		}
		
		this.currentTime = 0;
		this.sampleCounter = 0;
		
		this.sampleRate = sampleRate;
	}
	
	public double getCurrentTime() {
		return currentTime;
	}
	
	private void countTime() {
		currentTime = sampleCounter/sampleRate;
		sampleCounter++;
	}
	
	public double readNote() {
		countTime();
		return frequency;
	}
	
}
