package synth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mixer, mix sample channels, each channel has his own control parameters 
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinícius
 */
public class Mixer {
	
	private double volumeMaster;
	private double[] volumeChannel;
	private int numOfChannels;
	private boolean[] channelOn;
	
	public Mixer(int numOfChannels) {
		this.volumeMaster = 0.5;
		
		this.numOfChannels = numOfChannels;
		this.volumeChannel = new double[numOfChannels];
		for(int i = 0; i < volumeChannel.length; i++) {
			volumeChannel[i] = 1;
		}
		
		this.channelOn = new boolean[numOfChannels];
		
	}
	
	/**
	 * getter method for volume master
	 * @return
	 */
	public double getVolumeMaster() {
		return volumeMaster;
	}


	/**
	 * setter method for volume master
	 * @param volumeMaster
	 */
	public void setVolumeMaster(double volumeMaster) {
		this.volumeMaster = volumeMaster;
	}


	/**
	 * getter method for volume of a specify channel
	 * @param channelNum
	 * @return
	 */
	public double getVolumeChannel(int channelNum) {
		return volumeChannel[channelNum];
	}


	/**
	 * setter method for volume of a specify channel
	 * @param channelNum
	 * @param volumeChannel
	 */
	public void setVolumeChannel(int channelNum, double volumeChannel) {
		this.volumeChannel[channelNum] = volumeChannel;
	}


	/**
	 * getter method, returns if a channel is on
	 * @param channelNum
	 * @return
	 */
	public boolean getChannelOn(int channelNum) {
		return channelOn[channelNum];
	}


	/**
	 * sets a channel ON or OFF
	 * @param channelNum
	 * @param channelOn
	 */
	public void setChannelOn(int channelNum, boolean channelOn) {
		this.channelOn[channelNum] = channelOn;
	}


	/**
	 * returns a mix of all the channels
	 * @param channel1SynthData
	 * @param channel2SynthData
	 * @param channel3SynthData
	 * @return
	 */
	public void mixSynthChannels(ConcurrentHashMap<Integer, Note> notesPlaying) {
		for(Map.Entry<Integer, Note> entry : notesPlaying.entrySet()) {
		    Note note = entry.getValue();
		    note.setSample(0);
		    
		    for(Map.Entry<String, Double> channel : note.getAllChannelSample().entrySet()) {
			    double value = channel.getValue();
			    note.setSample(note.getSample() + value);
			}
		}
	}
	
	/**
	 * returns a mix ready to be processed (audio processed)
	 * @param channel1SynthData
	 * @param channel2SynthData
	 * @param channel3SynthData
	 * @return
	 */
	public double mixOutputSample(ConcurrentHashMap<Integer, Note> notesPlaying) {
		double mixedSample = 0;
		
		for(Map.Entry<Integer, Note> entry : notesPlaying.entrySet()) {
		    Note note = entry.getValue();
		    mixedSample = mixedSample + note.getSample();
		}
		
		mixedSample = mixedSample * ((8000/numOfChannels)*volumeMaster);
		
		return mixedSample;
	}

}
