package synth;

import java.util.HashMap;

public class Note {
	int noteNumber;
	HashMap<String, Double> currentChannelSample;
	double currentSample;
	double currentTime;
	boolean fin;
	
	public Note(int noteNumber) {
		this.noteNumber = noteNumber;
		this.currentChannelSample = new HashMap<String, Double>();
		this.currentSample = 0;
		
		this.currentTime = 0;
		this.fin = false;
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
	
	

}
