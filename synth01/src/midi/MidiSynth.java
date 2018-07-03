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


/**
 * Paint Canvas to draw the wave
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinicius
 */
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
   
    /**
     * Get status of a specific note
     * @param note
     * 				note number (from the piano row)
     * @return
     * 				true if is playing, otherwise false
     */
    public boolean getNoteStatus(int note) {
    	return keyEnable[note];
    }
    
    /**
     * turns on/off the note status
     * @param note
     * 				note number (from the piano row)
     */
    public void switchNoteStatus(int note) {
    	if(getNoteStatus(note))
    		keyEnable[note] = false;
    	else
    		keyEnable[note] = true;
    }


    /**
     * plays the note
     * @param note
     * 				note number (from the piano row)
     */
    public void noteOn(int note) {
    	if(getNoteStatus(note) == false) {
    		midiChannels[0].noteOn(note + noteOffset, 600);
    		switchNoteStatus(note);
    	}
    }
    
    /**
     * stops the note
     * @param note
     * 				note number (from the piano row)
     */
    public void noteOff(int note) {
    	midiChannels[0].noteOff(note + noteOffset, 600);
    	switchNoteStatus(note);
    }
    
    /**
     * change midi's instrument
     * @param instrumentNumber
     * 				number from the instrument (there are 128 different instruments)
     */
    public void changeInstrument(int instrumentNumber) {
        synthesizer.getChannels()[0].programChange(instrumentNumber);
    }
}