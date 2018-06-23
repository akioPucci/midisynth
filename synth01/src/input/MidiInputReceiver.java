package input;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import key.KeyManagement;
import synth.AudioSynth;

public class MidiInputReceiver implements Receiver {
	AudioSynth synth = AudioSynth.getAudioSynth();
	public String name;

	public MidiInputReceiver(String name) {
		this.name = name;
	}

	public void send(MidiMessage msg, long timeStamp) {

		byte[] b = msg.getMessage();
		String tmp = bits(b[0]);
		int message = convertBits(tmp);
		int code = convertBits(bits(b[1]));
		System.out.println("Message = " + message + " code = " + code);
		
				
		if (message == 144) 
			KeyManagement.playNoteFromMIDI(getNote(code));
		else if (message == 128)
			KeyManagement.pauseNoteFromMIDI(getNote(code));
		
	}

	public static int getNote(int code) {
		return (code - 48);
	}
	
	public String bits(byte b) {
		String bits = "";
		for (int bit = 7; bit >= 0; --bit) {
			bits = bits + ((b >>> bit) & 1);
		}
		return bits;
	}

	public int convertBits(String bits) {
		int res = 0;
		int size = bits.length();

		for (int i = size - 1; i >= 0; i--) {
			if (bits.charAt(i) == '1') {
				res += 1 << (size - i - 1);
			}

		}
		return res;
	}

	@Override
	public void close() {
		

	}
}