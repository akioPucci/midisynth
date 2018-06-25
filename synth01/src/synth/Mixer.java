package synth;

public class Mixer {
	
	private double volumeMaster;
	private double[] volumeChannel;
	private boolean[] channelOn;
	
	public Mixer(int numOfChannels) {
		this.volumeMaster = 1;
		this.volumeChannel = new double[numOfChannels];
		this.channelOn = new boolean[numOfChannels];
		
	}
	
	
	public double getVolumeMaster() {
		return volumeMaster;
	}


	public void setVolumeMaster(double volumeMaster) {
		this.volumeMaster = volumeMaster;
	}


	public double[] getVolumeChannel() {
		return volumeChannel;
	}


	public void setVolumeChannel(double[] volumeChannel) {
		this.volumeChannel = volumeChannel;
	}


	public boolean[] getChannelOn() {
		return channelOn;
	}


	public void setChannelOn(boolean[] channelOn) {
		this.channelOn = channelOn;
	}


	public double[] mixSynthChannels(double[] channel1SynthData, double[] channel2SynthData, double[] channel3SynthData) {
		double[] mixedSynthChannel = new double[38];
		for(int i = 0; i < 38; i++) {
				mixedSynthChannel[i] = channel1SynthData[i] + channel2SynthData[i] + channel3SynthData[i];
			
		}
		
		return mixedSynthChannel;
	}
	
	public double mixOutputSample(double[] channel1SynthData, double[] channel2SynthData, double[] channel3SynthData) {
		double[] noteToMix = mixSynthChannels(channel1SynthData, channel2SynthData, channel3SynthData);
		double mixedSample = 0;
		
		for(int i = 0; i < 38; i++ ) {
			mixedSample = mixedSample + noteToMix[i];
		}
		
		return mixedSample;
	}

}
