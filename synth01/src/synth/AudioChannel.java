package synth;

/**
 * Audio Channel
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinícius
 */
public class AudioChannel {

	double[] keyOutput;
	
	AudioChannel(int numOfKeys){
		keyOutput = new double[numOfKeys];
	}

	/**
	 * gets keys outputs
	 * @return
	 * 			array of each key sample value
	 */
	public double[] getKeysOutput() {
		return keyOutput;
	}

	/**
	 * set keys outputs
	 * @param keyOutput
	 * 					array of each key sample value
	 */
	public void setKeysOutput(double[] keyOutput) {
		this.keyOutput = keyOutput;
	}
	
	
}
