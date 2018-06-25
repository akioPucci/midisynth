package midi;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;


public class Midi extends JFrame {
  MidiChannel channel; // The channel we play on: 10 is for percussion

  int velocity = 100; // Default volume is 50%

  public static void main(String[] args) throws MidiUnavailableException {
    // We don't need a Sequencer in this example, since we send MIDI
    // events directly to the Synthesizer instead.
    Synthesizer synthesizer = MidiSystem.getSynthesizer();
    synthesizer.open();
    JFrame frame = new Midi(synthesizer);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(50, 128); // We use window width as volume control
    frame.setVisible(true);
  }

  public Midi(Synthesizer synth) {
    super("Fck");

    // Channel 10 is the GeneralMidi percussion channel. In Java code, we
    // number channels from 0 and use channel 9 instead.
    channel = synth.getChannels()[3];
    
    
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key >= 35 && key <= 81) {
          channel.noteOn(key, velocity);
        }
      }

      public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key >= 35 && key <= 81)
          channel.noteOff(key);
      }
    });

  }
}