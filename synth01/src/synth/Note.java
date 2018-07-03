package synth;

import java.util.HashMap;

/**
 * Synthesizer note object, contains the core parameter to communicate with all the others objects
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */
public class Note {
	int noteNumber;
	HashMap<String, Double> currentChannelSample;
	double currentSample;
	double initTime;
	double currentTime;
	double releaseStartTime;
	
	//envelopes states:
	//0 - attack
	//1 - decay
	//2 - sustain
	//3 - release
	int envState;
	boolean fin;
	
	public Note(int noteNumber) {
		this.noteNumber = noteNumber;
		this.currentChannelSample = new HashMap<String, Double>();
		this.currentSample = 0;
		
		this.initTime = -1;
		this.envState = 0;
		this.currentTime = 0;
		this.releaseStartTime = -1;
		this.fin = false;
	}
	
	public double getInitTime() {
		return initTime;
	}

	public void setInitTime(double initTime) {
		this.initTime = initTime;
	}
	
	public double getReleaseStartTime() {
		return releaseStartTime;
	}

	public void setReleaseStartTime(double releaseTime) {
		this.releaseStartTime = releaseTime;
	}
	
	public boolean isFin() {
		return this.fin;
	}
	
	public void setFin(){
		this.fin = true;
	}
	
	public int getNote() {
		return this.noteNumber;
	}
	
	public void setNote(int note) {
		this.noteNumber = note;
	}
	
	public double getSample() {
		return this.currentSample;
	}
	
	public void setSample(double sample) {
		this.currentSample = sample;
	}
	
	public double getChannelSample(String key) {
		return this.currentChannelSample.get(key);
	}
	
	public HashMap<String, Double> getAllChannelSample(){
		return this.currentChannelSample;
	}
	
	public void setChannelSample(String key, double sample) {
		this.currentChannelSample.put(key, sample);
	}
	
	public void setTime(double time) {
		this.currentTime = time;
	}
	
	public double getTime() {
		return this.currentTime;
	}
	
	public int getEnvState() {
		return this.envState;
	}

	/**
	 * set which envelope state the note holds
	 * @param state
	 * 1 - attack
	 * 2 - decay
	 * 3 - sustain
	 * 4 - release
	 */
	public void setEnvState(int state) {
		switch(state) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
		this.envState = state;
	}
	
	

}
