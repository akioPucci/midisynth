package gui;

import genius.Genius;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import key.KeyManagement;
import midi.MidiSynth;

@SuppressWarnings("serial")
public class Tela_MIDI extends JFrame {
	public static Teclado t;
	public JButton button[];
	
	public JMenuBar Bar;
	public JMenu menu;
	public JMenuItem exit;
	public JMenuItem synth;

	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;
	
	private static MidiSynth midi;
	
	private JComboBox<String> Instrumentos;
    private JComboBox<String> Tipo;
    private String[][] Ins;
    
    private Redimensionamento p;

	public Tela_MIDI() {
		p = new Redimensionamento();
		initTela();
		initTeclado();
		initMenu();
		initInstrumentos();
		initGravador();
		setAllNotFocusable();
		KeyManagement.create(this, button, 0);
	}

	public void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(22, 24, 32));
		setMinimumSize(new Dimension(p.ProporcaoW(1807), p.ProporcaoH(1036)));
		setResizable(false); // Impede de alterar tamanho da tela
		setLocationRelativeTo(null); // Centro da tela
		getContentPane().setLayout(null);
		verifyIfNimbusIsInstalled();
	}
	
	/**
	 * Verifiy if the LookAndFeel Nimbus is installed
	 */
	
	private void verifyIfNimbusIsInstalled() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Tela_MIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Tela_MIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Tela_MIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Tela_MIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}

	public void initMenu() {
		Bar = new JMenuBar();
		menu = new JMenu();
		synth = new JMenuItem();
		exit = new JMenuItem();

		menu.setText("Menu");

		synth.setText("Sintetizador");
		menu.add(synth);
		synth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				synthActionPerformed(evt);
			}
		});

		exit.setText("Sair");
		menu.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});

		Bar.add(menu);
		setJMenuBar(Bar);
	}

	private void synthActionPerformed(ActionEvent evt) {
		Tela_Inicial.ts.setVisible(true);
		this.setVisible(false);
	}

	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
	
	public void initInstrumentos() {
		midi = MidiSynth.getMidiSynth();
		
		Instrumentos = new JComboBox<>();
        Tipo = new JComboBox<>();
        
        Ins = new String[15][];
        
        Ins[0] = new String[] {"Acoustic Grand Piano", "Bright Acoustic Piano", 
        		"Electric Grand Piano", "Honky-tonk Piano", "Electric Piano 1", 
        		"Electric Piano 2", "Harpsichord", "Clavinet"};
        
        Ins[1] = new String[] {"Celesta", "Glockenspiel", "Music Box", 
        		"Vibraphone", "Marimba", "Xylophone", "Tubular Bells", "Dulcimer"};
        
        Ins[2] = new String[] {"Drawbar Organ", "Percussive Organ", "Rock Organ", "Church Organ",
        		"Reed Organ", "Accordion", "Harmonica", "Tango Accordion"};
        
        Ins[3] = new String[] {"Acoustic Guitar (nylon)", "Acoustic Guitar (steel)", 
        		"Electric Guitar (jazz)", "Electric Guitar (clean)", "Electric Guitar (muted)",
        		"Overdriven Guitar", "Distortion Guitar", "Guitar harmonics"};
        
        Ins[4] = new String[] {"Acoustic Bass", "Electric Bass (finger)", "Electric Bass (pick)", 
        		"Fretless Bass", "Slap Bass 1", "Slap Bass 2", "Synth Bass 1", "Synth Bass 2"};
        
        Ins[5] = new String[] {"Violin", "Viola", "Cello", "Contrabass", "Tremolo Strings",
        		"Pizzicato Strings", "Orchestral Harp", "Timpani", "String Ensemble 1", 
        		"String Ensemble 2", "Synth Strings 1", "Synth Strings 2", "Choir Aahs",
        		"Voice Oohs", "Synth Voice", "Orchestra Hit"};
        
        Ins[6] = new String[] {"Trumpet", "Trombone", "Tuba", "Muted Trumpet", "French Horn",
        		"Brass Section", "Synth Brass 1", "Synth Brass 2"};
        
        Ins[7] = new String[] {"Soprano Sax", "Alto Sax", "Tenor Sax", "Baritone Sax", "Oboe",
        		"English Horn", "Bassoon", "Clarinet"};
        
        Ins[8] = new String[] {"Piccolo", "Flute", "Recorder", "Pan Flute", "Blown Bottle",
        		"Shakuhachi", "Whistle", "Ocarina"};
        
        Ins[9] = new String[] {"Lead 1 (square)", "Lead 2 (sawtooth)", "Lead 3 (calliope)",
        		"Lead 4 (chiff)", "Lead 5 (charang)", "Lead 6 (voice)", "Lead 7 (fifths)", 
        		"Lead 8 (bass + lead)"};
        
        Ins[10] = new String[] {"Pad 1 (new age)", "Pad 2 (warm)", "Pad 3 (polysynth)", 
        		"Pad 4 (choir)", "Pad 5 (bowed)", "Pad 6 (metallic)", "Pad 7 (halo)", 
        		"Pad 8 (sweep)"};
        
        Ins[11] = new String[] {"FX 1 (rain)", "FX 2 (soundtrack)", "FX 3 (crystal)", 
        		"FX 4 (atmosphere)", "FX 5 (brightness)", "FX 6 (goblins)", "FX 7 (echoes)",
        		"FX 8 (sci-fi)"};
        
        Ins[12] = new String[] {"Sitar", "Banjo", "Shamisen", "Koto", "Kalimba", "Bag pipe",
        		"Fiddle", "Shanai"};
        
        Ins[13] = new String[] {"Tinkle Bell", "Agogo", "Steel Drums", "Woodblock", 
        		"Taiko Drum", "Melodic Tom", "Synth Drum"};
        
        Ins[14] = new String[] {"Reverse Cymbal", "Guitar Fret Noise", "Breath Noise",
        		"Seashore", "Bird Tweet", "Telephone Ring", "Helicopter", "Applause", "Gunshot"};
        
        Instrumentos.setModel(new DefaultComboBoxModel<>((String[]) Ins[0]));
        Instrumentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                InstrumentosActionPerformed(evt);
            }
        });
        getContentPane().add(Instrumentos);
        Instrumentos.setBounds(p.ProporcaoW(74), p.ProporcaoH(62), p.ProporcaoW(175), 
        		p.ProporcaoH(25));

        Tipo.setModel(new DefaultComboBoxModel<>(new String[] { "Piano", "Chromatic Percussion",
        		"Organ", "Guitar", "Bass", "Strings", "Brass", "Reed", "Pipe", "Synth Lead",
        		"Synth Pad", "Synth Effects", "Ethnic", "Percussive", "Sound effects"}));
        Tipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TipoActionPerformed(evt);
            }
        });
        getContentPane().add(Tipo);
        Tipo.setBounds(p.ProporcaoW(257), p.ProporcaoH(62), p.ProporcaoW(175), p.ProporcaoH(25));
	}
	
	private void InstrumentosActionPerformed(ActionEvent evt) {
		int s1 = (int) Tipo.getSelectedIndex();
		int s2 = (int) Instrumentos.getSelectedIndex();
		
		if (s1 < 6) {
			midi.changeInstrument((s1 * 8 + s2));
		} else if (s1 != 14) {
			midi.changeInstrument((((s1 + 1) * 8) + s2));
		} else {
			midi.changeInstrument(119 + s2);
		}
	}
	
	private void TipoActionPerformed(ActionEvent evt) {
		int i = (int) Tipo.getSelectedIndex();
		
		Instrumentos.setModel(new DefaultComboBoxModel<>((String []) Ins[i]));
		
		if (i < 6) {
			midi.changeInstrument((i * 8));
		} else if (i != 14) {
			midi.changeInstrument(((i + 1) * 8));
		} else {
			midi.changeInstrument(119);
		}
	}
	
	public void initGravador() {
		gravar = new JButton();
		gravar.setBounds(p.ProporcaoW(1100), p.ProporcaoH(100), p.ProporcaoW(100), 
				p.ProporcaoH(50));
		gravar.setText("Gravar");
		gravar.setVisible(true);
		getContentPane().add(gravar);

		gravar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KeyManagement.startRecording(0);

			}
		});

		pausar = new JButton();
		pausar.setBounds(p.ProporcaoW(1200), p.ProporcaoH(100), p.ProporcaoW(100), 
				p.ProporcaoH(50));
		pausar.setText("Parar");
		pausar.setVisible(true);
		getContentPane().add(pausar);

		pausar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = JOptionPane.showInputDialog("Nome do arquivo");
				KeyManagement.stopRecording(nome);

			}
		});

		reproduzir = new JButton();
		reproduzir.setBounds(p.ProporcaoW(1300), p.ProporcaoH(100), p.ProporcaoW(100), 
				p.ProporcaoH(50));
		reproduzir.setText("Reproduzir");
		reproduzir.setVisible(true);
		getContentPane().add(reproduzir);

		reproduzir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser(System
						.getProperty("user.dir") + "/gravacoes");

				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = file.showSaveDialog(null);
				if (i == 1) {
					// TODO nao escolheu nada
				} else {
					File arquivo = file.getSelectedFile();
					int last = arquivo.getPath().lastIndexOf('\\');
					String path = arquivo.getPath().substring(last + 1);
					path = path.substring(0, path.indexOf('.'));
					try {
						KeyManagement.playRecord(path);
					} catch (Exception e1) {
						// TODO arquivo invalido
						// e1.printStackTrace();
					}
				}

			}
		});

		genius = new JButton();
		genius.setBounds(p.ProporcaoW(1400), p.ProporcaoH(100), p.ProporcaoW(100), 
				p.ProporcaoH(50));
		genius.setText("Genius");
		genius.setVisible(true);
		getContentPane().add(genius);

		genius.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser(System
						.getProperty("user.dir") + "/gravacoes");

				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = file.showSaveDialog(null);
				if (i == 1) {
					// TODO nao escolheu nada
				} else {
					File arquivo = file.getSelectedFile();
					int last = arquivo.getPath().lastIndexOf('\\');
					String path = arquivo.getPath().substring(last + 1);
					path = path.substring(0, path.indexOf('.'));
					try {
						Genius.startGenius(path);
						// KeyManagement.playRecord(path);
					} catch (Exception e1) {
						// TODO arquivo invalido
						JOptionPane.showMessageDialog(null, "Erro", "Arquivo invalido", 
								JOptionPane.ERROR_MESSAGE);
						// e1.printStackTrace();
					}
				}

			}
		});
	}

	public void initTeclado() {
		t = new Teclado();
		
		getContentPane().add(t.DoSus1);
		getContentPane().add(t.ReSus1);
		getContentPane().add(t.FaSus1);
		getContentPane().add(t.SolSus1);
		getContentPane().add(t.LaSus1);
		getContentPane().add(t.DoSus2);
		getContentPane().add(t.ReSus2);
		getContentPane().add(t.FaSus2);
		getContentPane().add(t.SolSus2);
		getContentPane().add(t.LaSus2);
		getContentPane().add(t.DoSus3);
		getContentPane().add(t.ReSus3);
		getContentPane().add(t.FaSus3);
		getContentPane().add(t.SolSus3);
		getContentPane().add(t.LaSus3);
		getContentPane().add(t.Do1);
		getContentPane().add(t.Re1);
		getContentPane().add(t.Mi1);
		getContentPane().add(t.Fa1);
		getContentPane().add(t.Sol1);
		getContentPane().add(t.La1);
		getContentPane().add(t.Si1);
		getContentPane().add(t.Do2);
		getContentPane().add(t.Re2);
		getContentPane().add(t.Mi2);
		getContentPane().add(t.Fa2);
		getContentPane().add(t.Sol2);
		getContentPane().add(t.La2);
		getContentPane().add(t.Si2);
		getContentPane().add(t.Do3);
		getContentPane().add(t.Re3);
		getContentPane().add(t.Mi3);
		getContentPane().add(t.Fa3);
		getContentPane().add(t.Sol3);
		getContentPane().add(t.La3);
		getContentPane().add(t.Si3);
		getContentPane().add(t.Do4);
		
		button = t.createJButtonArray();
	}

	private void setAllNotFocusable() {
		for (int i = 0; i < button.length; i++) {
			button[i].setFocusable(false);
		}

		gravar.setFocusable(false);
		pausar.setFocusable(false);
		reproduzir.setFocusable(false);
		genius.setFocusable(false);

		Bar.setFocusable(false);
		menu.setFocusable(false);
		exit.setFocusable(false);
		synth.setFocusable(false);
		
		Instrumentos.setFocusable(false);
		Tipo.setFocusable(false);
	}
}