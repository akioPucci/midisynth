package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import genius.Genius;
import key.KeyManagement;
import paint.Paint;
import synth.AudioSynth;

@SuppressWarnings("serial")
public class TelaSintetizador extends JFrame {
	private AudioSynth synth;

	private JButton button[];

	private JMenuBar Bar;
	private JMenu menu;
	private JMenuItem exit;
	private JMenuItem MIDI;

	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;

	private JSlider Ampl1;
	private JSlider Ampl2;
	private JSlider Ampl3;
	private JLabel Ampl_Text1;
	private JLabel Ampl_Text2;
	private JLabel Ampl_Text3;
	private JLabel Ampl_Value_Text1;
	private JLabel Ampl_Value_Text2;
	private JLabel Ampl_Value_Text3;
	private JLabel Des_Ondas1;
	private JLabel Des_Ondas2;
	private JLabel Des_Ondas3;
	private JSlider Freq1;
	private JSlider Freq2;
	private JSlider Freq3;
	private JLabel Freq_Text1;
	private JLabel Freq_Text2;
	private JLabel Freq_Text3;
	private JLabel Freq_Value_Text1;
	private JLabel Freq_Value_Text2;
	private JLabel Freq_Value_Text3;
	private JSlider Master_Slider;
	private JLabel Master_Text;
	private JLabel Master_Value_Text;
	private JLabel Osc_Text1;
	private JLabel Osc_Text2;
	private JLabel Osc_Text3;
	private JComboBox<String> Sel_Ondas1;
	private JComboBox<String> Sel_Ondas2;
	private JComboBox<String> Sel_Ondas3;
	private JSeparator Separador1;
	private JSeparator Separador2;
	private JSeparator Separador3;
	private JSeparator Separador4;
	private JSeparator Separador5;
	private JSeparator Separador6;
	private JSeparator Separador7;
	private JSeparator Separador8;
	private JSeparator Separador9;
	private JSeparator Separador10;

	private Icon Onda_Triangular;
	private Icon Onda_Quadrada;
	private Icon Onda_Senoidal;
	private Icon Onda_Dente_Serra;

	private JSlider Attack_JSlider;
	private JLabel Attack_Text;
	private JLabel Attack_Value;
	private JSlider Decay_JSlider;
	private JLabel Decay_Text;
	private JLabel Decay_Value;
	private JSlider Release_JSlider;
	private JLabel Release_Text;
	private JLabel Release_Value;
	private JSlider Sustain_JSlider;
	private JLabel Sustain_Text;
	private JLabel Sustain_Value;
	private JSeparator VE_Separador1;
	private JSeparator VE_Separador2;
	private JSeparator VE_Separador3;
	private JSeparator VE_Separador4;
	private JLabel Volume_Envelope_Text;

	private JLabel sintetizador_Text;

	private String path;

	public TelaSintetizador() {
		initTela();
		initMenu();
		initMixer();
		initEnvelope();
		initGravador();
		initTeclado();
		initOutrosComponentes();
		pack();
		setAllNotFocusable();
		KeyManagement.create(this, button, 1);
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
			java.util.logging.Logger
					.getLogger(TelaSintetizador.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger
					.getLogger(TelaSintetizador.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger
					.getLogger(TelaSintetizador.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger
					.getLogger(TelaSintetizador.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private void initMenu() {
		Bar = new JMenuBar();
		menu = new JMenu();
		MIDI = new JMenuItem();
		exit = new JMenuItem();

		menu.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		menu.setText("Menu");

		MIDI.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		MIDI.setText("MIDI");
		menu.add(MIDI);
		MIDI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MIDIActionPerformed(evt);
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

	private void MIDIActionPerformed(ActionEvent evt) {
		TelaInicial.tm = new TelaMIDI();
		TelaInicial.tm.setVisible(true);
		this.dispose();
	}

	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void initMixer() {
		synth = AudioSynth.getAudioSynth();

		Master_Slider = new JSlider();
		Master_Text = new JLabel();
		Des_Ondas1 = new JLabel();
		Des_Ondas2 = new JLabel();
		Des_Ondas3 = new JLabel();
		Sel_Ondas1 = new JComboBox<>();
		Sel_Ondas2 = new JComboBox<>();
		Sel_Ondas3 = new JComboBox<>();
		Ampl1 = new JSlider();
		Ampl2 = new JSlider();
		Ampl3 = new JSlider();
		Freq1 = new JSlider();
		Freq2 = new JSlider();
		Freq3 = new JSlider();
		Osc_Text1 = new JLabel();
		Osc_Text2 = new JLabel();
		Osc_Text3 = new JLabel();
		Ampl_Text1 = new JLabel();
		Ampl_Text2 = new JLabel();
		Ampl_Text3 = new JLabel();
		Freq_Text1 = new JLabel();
		Freq_Text2 = new JLabel();
		Freq_Text3 = new JLabel();
		Separador1 = new JSeparator();
		Separador2 = new JSeparator();
		Separador3 = new JSeparator();
		Separador4 = new JSeparator();
		Separador5 = new JSeparator();
		Separador6 = new JSeparator();
		Separador7 = new JSeparator();
		Separador8 = new JSeparator();
		Separador9 = new JSeparator();
		Separador10 = new JSeparator();
		Ampl_Value_Text1 = new JLabel();
		Freq_Value_Text1 = new JLabel();
		Ampl_Value_Text2 = new JLabel();
		Freq_Value_Text2 = new JLabel();
		Ampl_Value_Text3 = new JLabel();
		Freq_Value_Text3 = new JLabel();
		Master_Value_Text = new JLabel();

		Onda_Triangular = new ImageIcon();
		Onda_Quadrada = new ImageIcon();
		Onda_Senoidal = new ImageIcon();
		Onda_Dente_Serra = new ImageIcon();

		redimensionarIcones();

		getContentPane().add(Master_Slider);
		Master_Slider.setBounds(TelaInicial.p.ProporcaoW(425),
				TelaInicial.p.ProporcaoH(550), TelaInicial.p.ProporcaoW(200),
				TelaInicial.p.ProporcaoH(20));
		Master_Slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				MasterStateChanged(evt);
			}
		});

		Master_Text.setForeground(new Color(255, 255, 255));
		Master_Text
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Master_Text.setText("Master");
		getContentPane().add(Master_Text);
		Master_Text.setBounds(TelaInicial.p.ProporcaoW(510),
				TelaInicial.p.ProporcaoH(574), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(16));

		Des_Ondas1.setIcon(Onda_Triangular);
		getContentPane().add(Des_Ondas1);
		Des_Ondas1.setBounds(TelaInicial.p.ProporcaoW(161),
				TelaInicial.p.ProporcaoH(173), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(50));

		Des_Ondas2.setIcon(Onda_Quadrada);
		getContentPane().add(Des_Ondas2);
		Des_Ondas2.setBounds(TelaInicial.p.ProporcaoW(421),
				TelaInicial.p.ProporcaoH(173), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(50));

		Des_Ondas3.setIcon(Onda_Dente_Serra);
		getContentPane().add(Des_Ondas3);
		Des_Ondas3.setBounds(TelaInicial.p.ProporcaoW(681),
				TelaInicial.p.ProporcaoH(173), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(50));

		Sel_Ondas1.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Sel_Ondas1.setModel(new DefaultComboBoxModel<>(new String[] {
				"Senoidal", "Quadrada", "Triangular", "Dente de Serra",
				"Desenhar" }));
		Sel_Ondas1.setSelectedItem("Triangular");
		synth.setOscType(0, "triangle");
		Sel_Ondas1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Sel_Ondas1ActionPerformed(evt);
			}
		});
		getContentPane().add(Sel_Ondas1);
		Sel_Ondas1.setBounds(TelaInicial.p.ProporcaoW(237),
				TelaInicial.p.ProporcaoH(180), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(30));

		Sel_Ondas2.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Sel_Ondas2.setModel(new DefaultComboBoxModel<>(new String[] {
				"Senoidal", "Quadrada", "Triangular", "Dente de Serra",
				"Desenhar" }));
		Sel_Ondas2.setSelectedItem("Quadrada");
		synth.setOscType(1, "square");
		Sel_Ondas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Sel_Ondas2ActionPerformed(evt);
			}
		});
		getContentPane().add(Sel_Ondas2);
		Sel_Ondas2.setBounds(TelaInicial.p.ProporcaoW(497),
				TelaInicial.p.ProporcaoH(180), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(30));

		Sel_Ondas3.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Sel_Ondas3.setModel(new DefaultComboBoxModel<>(new String[] {
				"Senoidal", "Quadrada", "Triangular", "Dente de Serra",
				"Desenhar" }));
		Sel_Ondas3.setSelectedItem("Dente de Serra");
		synth.setOscType(1, "saw");
		Sel_Ondas3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Sel_Ondas3ActionPerformed(evt);
			}
		});
		getContentPane().add(Sel_Ondas3);
		Sel_Ondas3.setBounds(TelaInicial.p.ProporcaoW(757),
				TelaInicial.p.ProporcaoH(180), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(30));

		Ampl1.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(10)));
		Ampl1.setOrientation(JSlider.VERTICAL);
		// Ampl1.setMinorTickSpacing(1);
		// Ampl1.setMajorTickSpacing(10);
		// Ampl1.setPaintTicks(true);
		// Ampl1.setPaintLabels(true);
		// Ampl1.setSnapToTicks(true); // Para somente sobre os ticks
		Ampl1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Ampl1StateChanged(evt);
			}
		});
		getContentPane().add(Ampl1);
		Ampl1.setBounds(TelaInicial.p.ProporcaoW(161),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(200));

		Ampl2.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(24)));
		Ampl2.setOrientation(JSlider.VERTICAL);
		Ampl2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Ampl2StateChanged(evt);
			}
		});
		getContentPane().add(Ampl2);
		Ampl2.setBounds(TelaInicial.p.ProporcaoW(421),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(200));

		Ampl3.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(24)));
		Ampl3.setOrientation(JSlider.VERTICAL);
		Ampl3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Ampl3StateChanged(evt);
			}
		});
		getContentPane().add(Ampl3);
		Ampl3.setBounds(TelaInicial.p.ProporcaoW(681),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(200));

		Freq1.setMaximum(5);
		Freq1.setOrientation(JSlider.VERTICAL);
		Freq1.setValue(2);
		Freq1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Freq1StateChanged(evt);
			}
		});
		getContentPane().add(Freq1);
		Freq1.setBounds(TelaInicial.p.ProporcaoW(237),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(200));

		Freq2.setMaximum(5);
		Freq2.setOrientation(JSlider.VERTICAL);
		Freq2.setValue(2);
		Freq2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Freq2StateChanged(evt);
			}
		});
		getContentPane().add(Freq2);
		Freq2.setBounds(TelaInicial.p.ProporcaoW(497),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(200));

		Freq3.setMaximum(5);
		Freq3.setOrientation(JSlider.VERTICAL);
		Freq3.setValue(2);
		Freq3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Freq3StateChanged(evt);
			}
		});
		getContentPane().add(Freq3);
		Freq3.setBounds(TelaInicial.p.ProporcaoW(757),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(130),
				TelaInicial.p.ProporcaoH(200));

		Osc_Text1.setFont(new Font("Times New Roman", 0, TelaInicial.p
				.ProporcaoW(24)));
		Osc_Text1.setForeground(new Color(255, 255, 255));
		Osc_Text1.setText("Oscilador 1");
		getContentPane().add(Osc_Text1);
		Osc_Text1.setBounds(TelaInicial.p.ProporcaoW(209),
				TelaInicial.p.ProporcaoH(125), TelaInicial.p.ProporcaoW(110),
				TelaInicial.p.ProporcaoH(28));

		Osc_Text2.setFont(new Font("Times New Roman", 0, TelaInicial.p
				.ProporcaoW(24)));
		Osc_Text2.setForeground(new Color(255, 255, 255));
		Osc_Text2.setText("Oscilador 2");
		getContentPane().add(Osc_Text2);
		Osc_Text2.setBounds(TelaInicial.p.ProporcaoW(469),
				TelaInicial.p.ProporcaoH(125), TelaInicial.p.ProporcaoW(110),
				TelaInicial.p.ProporcaoH(28));

		Osc_Text3.setFont(new Font("Times New Roman", 0, TelaInicial.p
				.ProporcaoW(24)));
		Osc_Text3.setForeground(new Color(255, 255, 255));
		Osc_Text3.setText("Oscilador 3");
		getContentPane().add(Osc_Text3);
		Osc_Text3.setBounds(TelaInicial.p.ProporcaoW(729),
				TelaInicial.p.ProporcaoH(125), TelaInicial.p.ProporcaoW(110),
				TelaInicial.p.ProporcaoH(28));

		Ampl_Text1.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Ampl_Text1.setForeground(new Color(255, 255, 255));
		Ampl_Text1.setText("Amplitude");
		getContentPane().add(Ampl_Text1);
		Ampl_Text1.setBounds(TelaInicial.p.ProporcaoW(158),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(56),
				TelaInicial.p.ProporcaoH(16));

		Ampl_Text2.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Ampl_Text2.setForeground(new Color(255, 255, 255));
		Ampl_Text2.setText("Amplitude");
		getContentPane().add(Ampl_Text2);
		Ampl_Text2.setBounds(TelaInicial.p.ProporcaoW(418),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(56),
				TelaInicial.p.ProporcaoH(16));

		Ampl_Text3.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Ampl_Text3.setForeground(new Color(255, 255, 255));
		Ampl_Text3.setText("Amplitude");
		getContentPane().add(Ampl_Text3);
		Ampl_Text3.setBounds(TelaInicial.p.ProporcaoW(678),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(56),
				TelaInicial.p.ProporcaoH(16));

		Freq_Text1.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Freq_Text1.setForeground(new Color(255, 255, 255));
		Freq_Text1.setText("Frequência");
		getContentPane().add(Freq_Text1);
		Freq_Text1.setBounds(TelaInicial.p.ProporcaoW(271),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(62),
				TelaInicial.p.ProporcaoH(16));

		Freq_Text2.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Freq_Text2.setForeground(new Color(255, 255, 255));
		Freq_Text2.setText("Frequência");
		getContentPane().add(Freq_Text2);
		Freq_Text2.setBounds(TelaInicial.p.ProporcaoW(531),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(62),
				TelaInicial.p.ProporcaoH(16));

		Freq_Text3.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(12)));
		Freq_Text3.setForeground(new Color(255, 255, 255));
		Freq_Text3.setText("Frequência");
		getContentPane().add(Freq_Text3);
		Freq_Text3.setBounds(TelaInicial.p.ProporcaoW(791),
				TelaInicial.p.ProporcaoH(440), TelaInicial.p.ProporcaoW(62),
				TelaInicial.p.ProporcaoH(16));

		getContentPane().add(Separador1);
		Separador1.setBounds(TelaInicial.p.ProporcaoW(135),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(780),
				TelaInicial.p.ProporcaoH(10));

		getContentPane().add(Separador2);
		Separador2.setBounds(TelaInicial.p.ProporcaoW(135),
				TelaInicial.p.ProporcaoH(480), TelaInicial.p.ProporcaoW(780),
				TelaInicial.p.ProporcaoH(10));

		Separador3.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador3);
		Separador3.setBounds(TelaInicial.p.ProporcaoW(134),
				TelaInicial.p.ProporcaoH(101), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(380));

		Separador4.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador4);
		Separador4.setBounds(TelaInicial.p.ProporcaoW(394),
				TelaInicial.p.ProporcaoH(101), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(380));

		Separador5.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador5);
		Separador5.setBounds(TelaInicial.p.ProporcaoW(654),
				TelaInicial.p.ProporcaoH(101), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(380));

		Separador6.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador6);
		Separador6.setBounds(TelaInicial.p.ProporcaoW(914),
				TelaInicial.p.ProporcaoH(101), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(380));

		Separador7.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador7);
		Separador7.setBounds(TelaInicial.p.ProporcaoW(264),
				TelaInicial.p.ProporcaoH(481), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(80));

		Separador8.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador8);
		Separador8.setBounds(TelaInicial.p.ProporcaoW(524),
				TelaInicial.p.ProporcaoH(481), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(80));

		Separador9.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(Separador9);
		Separador9.setBounds(TelaInicial.p.ProporcaoW(784),
				TelaInicial.p.ProporcaoH(481), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(80));

		getContentPane().add(Separador10);
		Separador10.setBounds(TelaInicial.p.ProporcaoW(265),
				TelaInicial.p.ProporcaoH(560), TelaInicial.p.ProporcaoW(520),
				TelaInicial.p.ProporcaoH(10));

		Ampl_Value_Text1.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Ampl_Value_Text1.setForeground(new Color(255, 255, 255));
		Ampl_Value_Text1.setText("50");
		getContentPane().add(Ampl_Value_Text1);
		Ampl_Value_Text1.setBounds(TelaInicial.p.ProporcaoW(211),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Freq_Value_Text1.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Freq_Value_Text1.setForeground(new Color(255, 255, 255));
		Freq_Value_Text1.setText("2");
		getContentPane().add(Freq_Value_Text1);
		Freq_Value_Text1.setBounds(TelaInicial.p.ProporcaoW(327),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Ampl_Value_Text2.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Ampl_Value_Text2.setForeground(new Color(255, 255, 255));
		Ampl_Value_Text2.setText("50");
		getContentPane().add(Ampl_Value_Text2);
		Ampl_Value_Text2.setBounds(TelaInicial.p.ProporcaoW(471),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Freq_Value_Text2.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Freq_Value_Text2.setForeground(new Color(255, 255, 255));
		Freq_Value_Text2.setText("2");
		getContentPane().add(Freq_Value_Text2);
		Freq_Value_Text2.setBounds(TelaInicial.p.ProporcaoW(587),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Ampl_Value_Text3.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Ampl_Value_Text3.setForeground(new Color(255, 255, 255));
		Ampl_Value_Text3.setText("50");
		getContentPane().add(Ampl_Value_Text3);
		Ampl_Value_Text3.setBounds(TelaInicial.p.ProporcaoW(731),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Freq_Value_Text3.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Freq_Value_Text3.setForeground(new Color(255, 255, 255));
		Freq_Value_Text3.setText("2");
		getContentPane().add(Freq_Value_Text3);
		Freq_Value_Text3.setBounds(TelaInicial.p.ProporcaoW(847),
				TelaInicial.p.ProporcaoH(230), TelaInicial.p.ProporcaoW(41),
				TelaInicial.p.ProporcaoH(16));

		Master_Value_Text.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Master_Value_Text.setForeground(new Color(255, 255, 255));
		Master_Value_Text.setText("50");
		getContentPane().add(Master_Value_Text);
		Master_Value_Text.setBounds(TelaInicial.p.ProporcaoW(604),
				TelaInicial.p.ProporcaoH(574), TelaInicial.p.ProporcaoW(21),
				TelaInicial.p.ProporcaoH(16));
	}

	private void redimensionarIcones() {
		try {
			Imagem img = new Imagem("images/triangular.png");
			Onda_Triangular = TelaInicial.p.redimensionarImg(img.imagem,
					TelaInicial.p.ProporcaoW(img.wmax),
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Imagem img = new Imagem("images/quadrada.png");
			Onda_Quadrada = TelaInicial.p.redimensionarImg(img.imagem,
					TelaInicial.p.ProporcaoW(img.wmax),
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Imagem img = new Imagem("images/senoidal.png");
			Onda_Senoidal = TelaInicial.p.redimensionarImg(img.imagem,
					TelaInicial.p.ProporcaoW(img.wmax),
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Imagem img = new Imagem("images/dente_serra.png");
			Onda_Dente_Serra = TelaInicial.p.redimensionarImg(img.imagem,
					TelaInicial.p.ProporcaoW(img.wmax),
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void MasterStateChanged(ChangeEvent evt) {
		synth.setVolumeMaster(((double) Master_Slider.getValue()) / 100);
		Master_Value_Text.setText(String.valueOf(Master_Slider.getValue()));
	}

	private void Ampl1StateChanged(ChangeEvent evt) {
		synth.setOscAmp(0, ((double) Ampl1.getValue()) / 100);
		Ampl_Value_Text1.setText(String.valueOf(Ampl1.getValue()));
	}

	private void Freq1StateChanged(ChangeEvent evt) {
		synth.setOscOctave(0, Freq1.getValue());
		Freq_Value_Text1.setText(String.valueOf(Freq1.getValue()));
	}

	private void Ampl2StateChanged(ChangeEvent evt) {
		synth.setOscAmp(1, ((double) Ampl2.getValue()) / 100);
		Ampl_Value_Text2.setText(String.valueOf(Ampl2.getValue()));
	}

	private void Freq2StateChanged(ChangeEvent evt) {
		synth.setOscOctave(1, Freq2.getValue());
		Freq_Value_Text2.setText(String.valueOf(Freq2.getValue()));
	}

	private void Ampl3StateChanged(ChangeEvent evt) {
		synth.setOscAmp(2, ((double) Ampl3.getValue()) / 100);
		Ampl_Value_Text3.setText(String.valueOf(Ampl3.getValue()));
	}

	private void Freq3StateChanged(ChangeEvent evt) {
		synth.setOscOctave(2, Freq3.getValue());
		Freq_Value_Text3.setText(String.valueOf(Freq3.getValue()));
	}

	private void Sel_Ondas1ActionPerformed(ActionEvent evt) {
		String s = (String) Sel_Ondas1.getSelectedItem();
		if (s == "Senoidal") {
			synth.setOscType(0, "sine");
			Des_Ondas1.setIcon(Onda_Senoidal);
		} else if (s == "Quadrada") {
			synth.setOscType(0, "square");
			Des_Ondas1.setIcon(Onda_Quadrada);
		} else if (s == "Triangular") {
			synth.setOscType(0, "triangle");
			Des_Ondas1.setIcon(Onda_Triangular);
		} else if (s == "Dente de Serra") {
			synth.setOscType(0, "saw");
			Des_Ondas1.setIcon(Onda_Dente_Serra);
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(0);
			new Thread(pt).start();
		}
	}

	private void Sel_Ondas2ActionPerformed(ActionEvent evt) {
		String s = (String) Sel_Ondas2.getSelectedItem();
		if (s == "Senoidal") {
			synth.setOscType(1, "sine");
			Des_Ondas2.setIcon(Onda_Senoidal);
		} else if (s == "Quadrada") {
			synth.setOscType(1, "square");
			Des_Ondas2.setIcon(Onda_Quadrada);
		} else if (s == "Triangular") {
			synth.setOscType(1, "triangle");
			Des_Ondas2.setIcon(Onda_Triangular);
		} else if (s == "Dente de Serra") {
			synth.setOscType(1, "saw");
			Des_Ondas2.setIcon(Onda_Dente_Serra);
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(1);
			new Thread(pt).start();
		}
	}

	private void Sel_Ondas3ActionPerformed(ActionEvent evt) {
		String s = (String) Sel_Ondas3.getSelectedItem();
		if (s == "Senoidal") {
			synth.setOscType(2, "sine");
			Des_Ondas3.setIcon(Onda_Senoidal);
		} else if (s == "Quadrada") {
			synth.setOscType(2, "square");
			Des_Ondas3.setIcon(Onda_Quadrada);
		} else if (s == "Triangular") {
			synth.setOscType(2, "triangle");
			Des_Ondas3.setIcon(Onda_Triangular);
		} else if (s == "Dente de Serra") {
			synth.setOscType(2, "saw");
			Des_Ondas3.setIcon(Onda_Dente_Serra);
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(2);
			new Thread(pt).start();
		}
	}

	class PaintThread extends Thread {
		int oscTarget;

		public PaintThread(int oscTarget) {
			this.oscTarget = oscTarget;
		}

		public void run() {
			synth.setOscType(oscTarget, "drawn");
			JFrame f = new JFrame();
			f.setBounds(10, 10, 1200, 1200);
			Paint p = new Paint();
			synth.setOscDrawnSample(oscTarget, p.open(f));

		}
	}

	private void initEnvelope() {
		VE_Separador1 = new JSeparator();
		VE_Separador2 = new JSeparator();
		VE_Separador3 = new JSeparator();
		VE_Separador4 = new JSeparator();
		Volume_Envelope_Text = new JLabel();
		Attack_Text = new JLabel();
		Attack_Value = new JLabel();
		Attack_JSlider = new JSlider();
		Decay_Text = new JLabel();
		Decay_Value = new JLabel();
		Decay_JSlider = new JSlider();
		Sustain_Text = new JLabel();
		Sustain_Value = new JLabel();
		Sustain_JSlider = new JSlider();
		Release_Text = new JLabel();
		Release_Value = new JLabel();
		Release_JSlider = new JSlider();

		VE_Separador1.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(VE_Separador1);
		VE_Separador1.setBounds(TelaInicial.p.ProporcaoW(984),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(461));

		VE_Separador2.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(VE_Separador2);
		VE_Separador2.setBounds(TelaInicial.p.ProporcaoW(1314),
				TelaInicial.p.ProporcaoH(100), TelaInicial.p.ProporcaoW(10),
				TelaInicial.p.ProporcaoH(461));

		getContentPane().add(VE_Separador3);
		VE_Separador3.setBounds(TelaInicial.p.ProporcaoW(985),
				TelaInicial.p.ProporcaoH(99), TelaInicial.p.ProporcaoW(330),
				TelaInicial.p.ProporcaoH(10));

		getContentPane().add(VE_Separador4);
		VE_Separador4.setBounds(TelaInicial.p.ProporcaoW(985),
				TelaInicial.p.ProporcaoH(560), TelaInicial.p.ProporcaoW(330),
				TelaInicial.p.ProporcaoH(10));

		Volume_Envelope_Text.setFont(new Font("Times New Roman", 1,
				TelaInicial.p.ProporcaoW(24)));
		Volume_Envelope_Text.setForeground(new Color(255, 255, 255));
		Volume_Envelope_Text.setText("Volume Envelope");
		getContentPane().add(Volume_Envelope_Text);
		Volume_Envelope_Text.setBounds(TelaInicial.p.ProporcaoW(1059),
				TelaInicial.p.ProporcaoH(125), TelaInicial.p.ProporcaoW(183),
				TelaInicial.p.ProporcaoH(25));

		Attack_Text
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Attack_Text.setForeground(new Color(255, 255, 255));
		Attack_Text.setText("Attack");
		getContentPane().add(Attack_Text);
		Attack_Text.setBounds(TelaInicial.p.ProporcaoW(1005),
				TelaInicial.p.ProporcaoH(181), TelaInicial.p.ProporcaoW(110),
				TelaInicial.p.ProporcaoH(15));

		Attack_Value
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Attack_Value.setForeground(new Color(255, 255, 255));
		Attack_Value.setHorizontalAlignment(SwingConstants.RIGHT);
		Attack_Value.setText("5 ms");
		getContentPane().add(Attack_Value);
		Attack_Value.setBounds(TelaInicial.p.ProporcaoW(1245),
				TelaInicial.p.ProporcaoH(181), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(15));

		Attack_JSlider.setMaximum(4000);
		Attack_JSlider.setMinimum(5);
		Attack_JSlider.setValue(5);
		Attack_JSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Attack_JSliderStateChanged(evt);
			}
		});
		getContentPane().add(Attack_JSlider);
		Attack_JSlider.setBounds(TelaInicial.p.ProporcaoW(1005),
				TelaInicial.p.ProporcaoH(222), TelaInicial.p.ProporcaoW(290),
				TelaInicial.p.ProporcaoH(25));

		Decay_Text.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Decay_Text.setForeground(new Color(255, 255, 255));
		Decay_Text.setText("Decay");
		getContentPane().add(Decay_Text);
		Decay_Text.setBounds(TelaInicial.p.ProporcaoW(1005),
				TelaInicial.p.ProporcaoH(273), TelaInicial.p.ProporcaoW(70),
				TelaInicial.p.ProporcaoH(15));

		Decay_Value
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Decay_Value.setForeground(new Color(255, 255, 255));
		Decay_Value.setHorizontalAlignment(SwingConstants.RIGHT);
		Decay_Value.setText("5 ms");
		getContentPane().add(Decay_Value);
		Decay_Value.setBounds(TelaInicial.p.ProporcaoW(1245),
				TelaInicial.p.ProporcaoH(273), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(15));

		Decay_JSlider.setMaximum(4000);
		Decay_JSlider.setMinimum(5);
		Decay_JSlider.setValue(5);
		Decay_JSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Decay_JSliderStateChanged(evt);
			}
		});
		getContentPane().add(Decay_JSlider);
		Decay_JSlider.setBounds(TelaInicial.p.ProporcaoW(1004),
				TelaInicial.p.ProporcaoH(314), TelaInicial.p.ProporcaoW(290),
				TelaInicial.p.ProporcaoH(25));

		Sustain_Text
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Sustain_Text.setForeground(new Color(255, 255, 255));
		Sustain_Text.setText("Sustain");
		getContentPane().add(Sustain_Text);
		Sustain_Text.setBounds(TelaInicial.p.ProporcaoW(1005),
				TelaInicial.p.ProporcaoH(365), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(15));

		Sustain_Value.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Sustain_Value.setForeground(new Color(255, 255, 255));
		Sustain_Value.setHorizontalAlignment(SwingConstants.RIGHT);
		Sustain_Value.setText("100");
		getContentPane().add(Sustain_Value);
		Sustain_Value.setBounds(TelaInicial.p.ProporcaoW(1274),
				TelaInicial.p.ProporcaoH(365), TelaInicial.p.ProporcaoW(21),
				TelaInicial.p.ProporcaoH(15));

		Sustain_JSlider.setValue(100);
		Sustain_JSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Sustain_JSliderStateChanged(evt);
			}
		});
		getContentPane().add(Sustain_JSlider);
		Sustain_JSlider.setBounds(TelaInicial.p.ProporcaoW(1004),
				TelaInicial.p.ProporcaoH(406), TelaInicial.p.ProporcaoW(290),
				TelaInicial.p.ProporcaoH(25));

		Release_Text
				.setFont(new Font("Tahoma", 0, TelaInicial.p.ProporcaoW(13)));
		Release_Text.setForeground(new Color(255, 255, 255));
		Release_Text.setText("Release");
		getContentPane().add(Release_Text);
		Release_Text.setBounds(TelaInicial.p.ProporcaoW(1005),
				TelaInicial.p.ProporcaoH(457), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(15));

		Release_Value.setFont(new Font("Tahoma", 0, TelaInicial.p
				.ProporcaoW(13)));
		Release_Value.setForeground(new Color(255, 255, 255));
		Release_Value.setHorizontalAlignment(SwingConstants.RIGHT);
		Release_Value.setText("1000 ms");
		getContentPane().add(Release_Value);
		Release_Value.setBounds(TelaInicial.p.ProporcaoW(1245),
				TelaInicial.p.ProporcaoH(457), TelaInicial.p.ProporcaoW(50),
				TelaInicial.p.ProporcaoH(15));

		Release_JSlider.setMaximum(4000);
		Release_JSlider.setMinimum(5);
		Release_JSlider.setValue(1000);
		Release_JSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				Release_JSliderStateChanged(evt);
			}
		});
		getContentPane().add(Release_JSlider);
		Release_JSlider.setBounds(TelaInicial.p.ProporcaoW(1004),
				TelaInicial.p.ProporcaoH(498), TelaInicial.p.ProporcaoW(290),
				TelaInicial.p.ProporcaoH(25));
	}

	private void Attack_JSliderStateChanged(ChangeEvent evt) {
		Attack_Value.setText(String.valueOf(Attack_JSlider.getValue()) + " ms");
		synth.setAttackEnvTime(((double) Attack_JSlider.getValue()) / 1000);
	}

	private void Decay_JSliderStateChanged(ChangeEvent evt) {
		Decay_Value.setText(String.valueOf(Decay_JSlider.getValue()) + " ms");
		synth.setDecayEnvTime(((double) Decay_JSlider.getValue()) / 1000);
	}

	private void Sustain_JSliderStateChanged(ChangeEvent evt) {
		Sustain_Value.setText(String.valueOf(Sustain_JSlider.getValue()));
		synth.setSustainEnvAmp(((double) Sustain_JSlider.getValue()) / 100);
	}

	private void Release_JSliderStateChanged(ChangeEvent evt) {
		Release_Value.setText(String.valueOf(Release_JSlider.getValue())
				+ " ms");
		synth.setReleaseEnvTime(((double) Release_JSlider.getValue()) / 1000);

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
		sintetizador_Text = new JLabel();

		sintetizador_Text.setFont(new Font("Segoe Script", 0, TelaInicial.p
				.ProporcaoW(58)));
		sintetizador_Text.setForeground(new Color(255, 0, 255));
		sintetizador_Text.setText("Sintetizador");
		getContentPane().add(sintetizador_Text);
		sintetizador_Text.setBounds(TelaInicial.p.ProporcaoW(760),
				TelaInicial.p.ProporcaoH(20), TelaInicial.p.ProporcaoW(400),
				TelaInicial.p.ProporcaoH(60));
	}

	private void setAllNotFocusable() {
		/* Botões */

		for (int i = 0; i < button.length; i++) {
			button[i].setFocusable(false);
		}

		/* Gravador */

		gravar.setFocusable(false);
		pausar.setFocusable(false);
		reproduzir.setFocusable(false);
		genius.setFocusable(false);

		/* Barra Menu */

		Bar.setFocusable(false);
		menu.setFocusable(false);
		exit.setFocusable(false);
		MIDI.setFocusable(false);

		/* JSlider */

		Ampl1.setFocusable(false);
		Ampl2.setFocusable(false);
		Ampl3.setFocusable(false);

		Freq1.setFocusable(false);
		Freq2.setFocusable(false);
		Freq3.setFocusable(false);

		Attack_JSlider.setFocusable(false);
		Decay_JSlider.setFocusable(false);
		Release_JSlider.setFocusable(false);
		Sustain_JSlider.setFocusable(false);

		Master_Slider.setFocusable(false);

		/* JComboBox */

		Sel_Ondas1.setFocusable(false);
		Sel_Ondas2.setFocusable(false);
		Sel_Ondas3.setFocusable(false);
	}
}