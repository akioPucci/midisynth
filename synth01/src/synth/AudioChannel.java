package synth;

public class AudioChannel {

	double[] keyOutput;
	
	AudioChannel(int numOfKeys){
		keyOutput = new double[numOfKeys];
	}

	public double[] getKeysOutput() {
		return keyOutput;
	}

	public void setKeysOutput(double[] keyOutput) {
		this.keyOutput = keyOutput;
	}
	
	
}
