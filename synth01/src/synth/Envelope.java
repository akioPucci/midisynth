package synth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Synth Envelope
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vin�cius
 */
public class Envelope {
	double envTime;
	double sampleCounter;
	double sampleRate;
	
	double attackTime;
	double decayTime;
	double sustainAmp;
	double releaseTime;
	
	public Envelope(double sampleRate,
			double attackTime, double decayTime, double sustainAmp, double releaseTime) {
		this.envTime = 0;
		this.sampleCounter = 0;
		this.sampleRate = sampleRate;
		
		this.attackTime = attackTime;
		this.decayTime = attackTime + decayTime;
		this.sustainAmp = sustainAmp;
		this.releaseTime = releaseTime;
	}
	
	public double getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(double attackTime) {
		this.attackTime = attackTime;
	}

	public double getDecayTime() {
		return decayTime;
	}

	public void setDecayTime(double decayTime) {
		this.decayTime = decayTime;
	}

	public double getSustainAmp() {
		return sustainAmp;
	}

	public void setSustainAmp(double sustainAmp) {
		this.sustainAmp = sustainAmp;
	}

	public double getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(double releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * counts time in seconds, increase sample Counter
	 */
	public void countTime() {
		envTime = (sampleCounter / sampleRate);
		sampleCounter++;
		
	}
	
	public double getTime() {
		return envTime;
	}
	
	/**
	 * apply the envelope to all notes playing
	 * @param notesPlaying
	 * 				table of notes playing
	 */
	public void apply(ConcurrentHashMap<Integer, Note> notesPlaying) {
		for(Map.Entry<Integer, Note> entry : notesPlaying.entrySet()) {
		    Note note = entry.getValue();
		    
		    if(note.getInitTime() == -1) {
		    	note.setInitTime(getTime());
		    }
		    note.setTime(getTime() - note.getInitTime());
		    
		    switch(note.getEnvState()) {
		    case 0: //attack
		    	if(note.getTime() < getAttackTime()) {
		    		note.setSample(note.getSample()*(note.getTime()/getAttackTime()));
		    	}else {
		    		note.setEnvState(1);
		    	}
		    	break;
		    	
		    case 1: //decay
		    	if(note.getTime() < getDecayTime()) {
		    		note.setSample(note.getSample()
		    				*(getSustainAmp() 
		    						+ ((1-getSustainAmp())*(1-note.getTime()/getDecayTime()))));
		    	}else {
		    		note.setEnvState(2);
		    	}		    	
		    	break;
		    	
		    case 2: //sustain
		    	note.setSample(note.getSample()* getSustainAmp());
		    	break;
		    case 3: //release
		    	if(note.getReleaseStartTime() == -1)
		    		note.setReleaseStartTime(note.getTime());
		    	if(note.getTime() < note.getReleaseStartTime() + getReleaseTime()) {
			    	note.setSample((note.getSample()* getSustainAmp())
			    			*(1 - (note.getTime() 
			    					/ (note.getReleaseStartTime() + getReleaseTime()))));

		    	}else {
		    		note.setFin();
		    	}
		    	break;
		    
			}
		    
		}
		
		countTime();
	}

	
}
