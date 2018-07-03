package gui;

import genius.Genius;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import key.KeyManagement;
import midi.MidiSynth;

@SuppressWarnings("serial")
public class TelaMIDI extends JFrame {
	private JButton button[];

	private JMenuBar Bar;
	private JMenu menu;
	private JMenuItem exit;
	private JMenuItem synth;

	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;

	private MidiSynth midi;

	private JLabel Instrumentos_Text;
	private JLabel Tipo_Text;
	private JComboBox<String> Instrumentos;
	private JComboBox<String> Tipo;
	private String[][] Ins;

	private JLabel MIDI_JLabel;
	private Icon MIDI_Icon;

	private String path;

	public TelaMIDI() {
		initTela();
		initMenu();
		initInstrumentos();
		initGravador();
		initTeclado();
		initOutrosComponentes();
		pack();
		setAllNotFocusable();
		KeyManagement.create(this, button, 0);
	}

	private void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(22, 24, 32));
		setMinimumSize(new Dimension(TelaInicial.p.ProporcaoW(1920),
				TelaInicial.p.ProporcaoH(1080)));
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		verifyIfNimbusIsInstalled();
	}

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
			java.util.logging.Logger.getLogger(TelaMIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(TelaMIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(TelaMIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(TelaMIDI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private void initMenu() {
		Bar = new JMenuBar();
		menu = new JMenu();
		synth = new JMenuItem();
		exit = new JMenuItem();

		menu.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		menu.setText("Menu");

		synth.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		synth.setText("Sintetizador");
		menu.add(synth);
		synth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				synthActionPerformed(evt);
			}
		});

		exit.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
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
		TelaInicial.ts = new TelaSintetizador();
		TelaInicial.ts.setVisible(true);
		this.dispose();
	}

	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void initInstrumentos() {
		midi = MidiSynth.getMidiSynth();

		Instrumentos_Text = new JLabel();
		Tipo_Text = new JLabel();

		Instrumentos = new JComboBox<>();
		Tipo = new JComboBox<>();

		Ins = new String[15][];

		Ins[0] = new String[] { "Acoustic Grand Piano",
				"Bright Acoustic Piano", "Electric Grand Piano",
				"Honky-tonk Piano", "Electric Piano 1", "Electric Piano 2",
				"Harpsichord", "Clavinet" };

		Ins[1] = new String[] { "Celesta", "Glockenspiel", "Music Box",
				"Vibraphone", "Marimba", "Xylophone", "Tubular Bells",
				"Dulcimer" };

		Ins[2] = new String[] { "Drawbar Organ", "Percussive Organ",
				"Rock Organ", "Church Organ", "Reed Organ", "Accordion",
				"Harmonica", "Tango Accordion" };

		Ins[3] = new String[] { "Acoustic Guitar (nylon)",
				"Acoustic Guitar (steel)", "Electric Guitar (jazz)",
				"Electric Guitar (clean)", "Electric Guitar (muted)",
				"Overdriven Guitar", "Distortion Guitar", "Guitar harmonics" };

		Ins[4] = new String[] { "Acoustic Bass", "Electric Bass (finger)",
				"Electric Bass (pick)", "Fretless Bass", "Slap Bass 1",
				"Slap Bass 2", "Synth Bass 1", "Synth Bass 2" };

		Ins[5] = new String[] { "Violin", "Viola", "Cello", "Contrabass",
				"Tremolo Strings", "Pizzicato Strings", "Orchestral Harp",
				"Timpani", "String Ensemble 1", "String Ensemble 2",
				"Synth Strings 1", "Synth Strings 2", "Choir Aahs",
				"Voice Oohs", "Synth Voice", "Orchestra Hit" };

		Ins[6] = new String[] { "Trumpet", "Trombone", "Tuba", "Muted Trumpet",
				"French Horn", "Brass Section", "Synth Brass 1",
				"Synth Brass 2" };

		Ins[7] = new String[] { "Soprano Sax", "Alto Sax", "Tenor Sax",
				"Baritone Sax", "Oboe", "English Horn", "Bassoon", "Clarinet" };

		Ins[8] = new String[] { "Piccolo", "Flute", "Recorder", "Pan Flute",
				"Blown Bottle", "Shakuhachi", "Whistle", "Ocarina" };

		Ins[9] = new String[] { "Lead 1 (square)", "Lead 2 (sawtooth)",
				"Lead 3 (calliope)", "Lead 4 (chiff)", "Lead 5 (charang)",
				"Lead 6 (voice)", "Lead 7 (fifths)", "Lead 8 (bass + lead)" };

		Ins[10] = new String[] { "Pad 1 (new age)", "Pad 2 (warm)",
				"Pad 3 (polysynth)", "Pad 4 (choir)", "Pad 5 (bowed)",
				"Pad 6 (metallic)", "Pad 7 (halo)", "Pad 8 (sweep)" };

		Ins[11] = new String[] { "FX 1 (rain)", "FX 2 (soundtrack)",
				"FX 3 (crystal)", "FX 4 (atmosphere)", "FX 5 (brightness)",
				"FX 6 (goblins)", "FX 7 (echoes)", "FX 8 (sci-fi)" };

		Ins[12] = new String[] { "Sitar", "Banjo", "Shamisen", "Koto",
				"Kalimba", "Bag pipe", "Fiddle", "Shanai" };

		Ins[13] = new String[] { "Tinkle Bell", "Agogo", "Steel Drums",
				"Woodblock", "Taiko Drum", "Melodic Tom", "Synth Drum" };

		Ins[14] = new String[] { "Reverse Cymbal", "Guitar Fret Noise",
				"Breath Noise", "Seashore", "Bird Tweet", "Telephone Ring",
				"Helicopter", "Applause", "Gunshot" };

		Instrumentos_Text.setForeground(new Color(255, 255, 255));
		Instrumentos_Text.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(36)));
		Instrumentos_Text.setText("Instrumentos");
		getContentPane().add(Instrumentos_Text);
		Instrumentos_Text.setBounds(TelaInicial.p.ProporcaoW(285),
				TelaInicial.p.ProporcaoH(30), TelaInicial.p.ProporcaoW(250),
				TelaInicial.p.ProporcaoH(50));

		Instrumentos
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(48)));
		Instrumentos.setModel(new DefaultComboBoxModel<>((String[]) Ins[0]));
		Instrumentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				InstrumentosActionPerformed(evt);
			}
		});
		getContentPane().add(Instrumentos);
		Instrumentos.setBounds(TelaInicial.p.ProporcaoW(135),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(550),
				TelaInicial.p.ProporcaoH(50));

		Tipo_Text.setForeground(new Color(255, 255, 255));
		Tipo_Text.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(36)));
		Tipo_Text.setText("Tipo");
		getContentPane().add(Tipo_Text);
		Tipo_Text.setBounds(TelaInicial.p.ProporcaoW(980),
				TelaInicial.p.ProporcaoH(30), TelaInicial.p.ProporcaoW(100),
				TelaInicial.p.ProporcaoH(50));

		Tipo.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(48)));
		Tipo.setModel(new DefaultComboBoxModel<>(new String[] { "Piano",
				"Chromatic Percussion", "Organ", "Guitar", "Bass", "Strings",
				"Brass", "Reed", "Pipe", "Synth Lead", "Synth Pad",
				"Synth Effects", "Ethnic", "Percussive", "Sound effects" }));
		Tipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TipoActionPerformed(evt);
			}
		});
		getContentPane().add(Tipo);
		Tipo.setBounds(TelaInicial.p.ProporcaoW(755),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(550),
				TelaInicial.p.ProporcaoH(50));
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

		Instrumentos.setModel(new DefaultComboBoxModel<>((String[]) Ins[i]));

		if (i < 6) {
			midi.changeInstrument((i * 8));
		} else if (i != 14) {
			midi.changeInstrument(((i + 1) * 8));
		} else {
			midi.changeInstrument(119);
		}
	}

	private void initGravador() {
		gravar = new JButton();
		gravar.setBounds(TelaInicial.p.ProporcaoW(1384),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(400),
				TelaInicial.p.ProporcaoH(115));
		gravar.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(20)));
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
		pausar.setBounds(TelaInicial.p.ProporcaoW(1384),
				TelaInicial.p.ProporcaoH(215), TelaInicial.p.ProporcaoW(400),
				TelaInicial.p.ProporcaoH(115));
		pausar.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(20)));
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
		reproduzir.setBounds(TelaInicial.p.ProporcaoW(1384),
				TelaInicial.p.ProporcaoH(330), TelaInicial.p.ProporcaoW(400),
				TelaInicial.p.ProporcaoH(115));
		reproduzir.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(20)));
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
				if (i != 1) {
					File arquivo = file.getSelectedFile();
					int last = arquivo.getPath().lastIndexOf('\\');
					path = arquivo.getPath().substring(last + 1);
					path = path.substring(0, path.indexOf('.'));
					try {
						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								KeyManagement.playRecord(path);
							}
						});
						t.start();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro",
								"Arquivo invalido", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		genius = new JButton();
		genius.setBounds(TelaInicial.p.ProporcaoW(1384),
				TelaInicial.p.ProporcaoH(445), TelaInicial.p.ProporcaoW(400),
				TelaInicial.p.ProporcaoH(115));
		genius.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(20)));
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
				if (i != 1) {
					File arquivo = file.getSelectedFile();
					int last = arquivo.getPath().lastIndexOf('\\');
					path = arquivo.getPath().substring(last + 1);
					path = path.substring(0, path.indexOf('.'));
					try {
						Thread t = new Thread(new Runnable() {

							@Override
							public void run() {
								Genius.startGenius(path);
							}
						});
						t.start();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro",
								"Arquivo invalido", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
	}

	private void initTeclado() {
		new Teclado();

		getContentPane().add(Teclado.DoSus1);
		getContentPane().add(Teclado.ReSus1);
		getContentPane().add(Teclado.FaSus1);
		getContentPane().add(Teclado.SolSus1);
		getContentPane().add(Teclado.LaSus1);
		getContentPane().add(Teclado.DoSus2);
		getContentPane().add(Teclado.ReSus2);
		getContentPane().add(Teclado.FaSus2);
		getContentPane().add(Teclado.SolSus2);
		getContentPane().add(Teclado.LaSus2);
		getContentPane().add(Teclado.DoSus3);
		getContentPane().add(Teclado.ReSus3);
		getContentPane().add(Teclado.FaSus3);
		getContentPane().add(Teclado.SolSus3);
		getContentPane().add(Teclado.LaSus3);
		getContentPane().add(Teclado.Do1);
		getContentPane().add(Teclado.Re1);
		getContentPane().add(Teclado.Mi1);
		getContentPane().add(Teclado.Fa1);
		getContentPane().add(Teclado.Sol1);
		getContentPane().add(Teclado.La1);
		getContentPane().add(Teclado.Si1);
		getContentPane().add(Teclado.Do2);
		getContentPane().add(Teclado.Re2);
		getContentPane().add(Teclado.Mi2);
		getContentPane().add(Teclado.Fa2);
		getContentPane().add(Teclado.Sol2);
		getContentPane().add(Teclado.La2);
		getContentPane().add(Teclado.Si2);
		getContentPane().add(Teclado.Do3);
		getContentPane().add(Teclado.Re3);
		getContentPane().add(Teclado.Mi3);
		getContentPane().add(Teclado.Fa3);
		getContentPane().add(Teclado.Sol3);
		getContentPane().add(Teclado.La3);
		getContentPane().add(Teclado.Si3);
		getContentPane().add(Teclado.Do4);

		button = Teclado.createJButtonArray();
	}

	private void initOutrosComponentes() {
		MIDI_JLabel = new JLabel();

		MIDI_Icon = new ImageIcon();

		try {
			Imagem img = new Imagem("images/MIDI.png");
			MIDI_Icon = TelaInicial.p.redimensionarImg(img.imagem,
					TelaInicial.p.ProporcaoW(img.wmax),
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			e.printStackTrace();
		}

		MIDI_JLabel.setIcon(MIDI_Icon);
		getContentPane().add(MIDI_JLabel);
		MIDI_JLabel.setBounds(TelaInicial.p.ProporcaoW(335),
				TelaInicial.p.ProporcaoH(200), TelaInicial.p.ProporcaoW(729),
				TelaInicial.p.ProporcaoH(366));
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