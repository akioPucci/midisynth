package midi;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;



public class MidiSynth {
	
	private static MidiSynth midisynth;

    private Synthesizer synthesizer;
  
    private final MidiChannel[] midiChannels;
    private final Instrument[] instruments;
    private int instrumentIndex = 0;
    
    private int numOfKeys;
    private boolean[] keyEnable;
    private int noteOffset;
    
    
    private MidiSynth() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
            System.exit(1);
        }   

        this.midiChannels = synthesizer.getChannels();

        Soundbank bank = synthesizer.getDefaultSoundbank();

        synthesizer.loadAllInstruments(bank);


        this.instruments = synthesizer.getAvailableInstruments();
        synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
        synthesizer.getChannels()[0].programChange(instrumentIndex);
        
        numOfKeys = 38;
        keyEnable = new boolean[numOfKeys];
        noteOffset = 36;

        System.out.println("[STATE] MIDI channels: " + midiChannels.length);
        System.out.println("[STATE] Instruments: " + instruments.length);
    }
    
    public static MidiSynth getMidiSynth() {
    	if(midisynth == null) {
    		midisynth = new MidiSynth();
    	}
    	
    	return midisynth;
    }
    
    public boolean getNoteStatus(int note) {
    	return keyEnable[note];
    }
    
    public void switchNoteStatus(int note) {
    	if(getNoteStatus(note))
    		keyEnable[note] = false;
    	else
    		keyEnable[note] = true;
    }


    public void noteOn(int note) {
    	if(getNoteStatus(note) == false) {
    		midiChannels[0].noteOn(note + noteOffset, 600);
    		switchNoteStatus(note);
    	}
    }
    public void noteOff(int note) {
    	midiChannels[0].noteOff(note + noteOffset, 600);
    	switchNoteStatus(note);
    }
    
    public void changeInstrument(int key) {
    	int keyboardDirection;
    	if(key == 37)
    		keyboardDirection = -1;
    	else
    		keyboardDirection = 1;
    	
    	switch(keyboardDirection) {
    	case -1: {
    		if (instrumentIndex == 0) {
    			instrumentIndex = instruments.length - 1;
    		} else {
    			instrumentIndex--;
    		}
    		
    		synthesizer.getChannels()[0].programChange(instrumentIndex);
    		System.out.println("Switched to " + 
    				instruments[instrumentIndex].getName());
    		break;
    	}
    	
    	case 1: {
    		if (instrumentIndex == instruments.length - 1) {
    			instrumentIndex = 0;
    		} else {
    			instrumentIndex++;
    		}
    		
    		synthesizer.getChannels()[0].programChange(instrumentIndex);
    		System.out.println("Switched to " + 
    				instruments[instrumentIndex].getName());
    		break;
    	
    	}
    	}
    }
}