package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
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
public class Tela_Sintetizador extends JFrame{
	private AudioSynth synth;
	
	public Teclado t;
	public JButton button[];
	
	public JMenuBar Bar;
    public JMenu menu;
    public JMenuItem exit;
    public JMenuItem MIDI;
    
	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;
	
	public JSlider Ampl1;
    public JSlider Ampl2;
    public JSlider Ampl3;
    public JLabel Ampl_Text1;
    public JLabel Ampl_Text2;
    public JLabel Ampl_Text3;
    private JLabel Ampl_Value_Text1;
    private JLabel Ampl_Value_Text2;
    private JLabel Ampl_Value_Text3;
    public JLabel Des_Ondas1;
    public JLabel Des_Ondas2;
    public JLabel Des_Ondas3;
    public JSlider Freq1;
    public JSlider Freq2;
    public JSlider Freq3;
    public JLabel Freq_Text1;
    public JLabel Freq_Text2;
    public JLabel Freq_Text3;
    public JLabel Freq_Value_Text1;
    public JLabel Freq_Value_Text2;
    public JLabel Freq_Value_Text3;
    public JSlider Master_Slider;
    public JLabel Master_Text;
    public JLabel Master_Value_Text;
    public JLabel Mixer_Text;
    public JLabel Osc_Text1;
    public JLabel Osc_Text2;
    public JLabel Osc_Text3;
    public JComboBox<String> Sel_Ondas1;
    public JComboBox<String> Sel_Ondas2;
    public JComboBox<String> Sel_Ondas3;
    public JSeparator Separador1;
    public JSeparator Separador2;
    public JSeparator Separador3;
    public JSeparator Separador4;
    public JSeparator Separador5;
    public JSeparator Separador6;
    public JSeparator Separador7;
    public JSeparator Separador8;
    public JSeparator Separador9;
    public JSeparator Separador10;
	
	public Tela_Sintetizador() {
		synth = AudioSynth.getAudioSynth();
		initTela();
		t = new Teclado();
		initTeclado();
		initMenu();
		initGravador();
		initMixer();
		setAllNotFocusable();
		KeyManagement.create(this, button, 1);
	}
	
	public void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(22, 24, 32));
        setMinimumSize(new Dimension(1807, 1036));
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
			java.util.logging.Logger.getLogger(Tela_Inicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Tela_Inicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Tela_Inicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Tela_Inicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
	public void initMenu() {
		Bar = new JMenuBar();
        menu = new JMenu();
        MIDI = new JMenuItem();
        exit = new JMenuItem();
        
        menu.setText("Menu");

        MIDI.setText("MIDI");
        menu.add(MIDI);
        MIDI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MIDIActionPerformed(evt);
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
	
	private void MIDIActionPerformed(ActionEvent evt) {
		Tela_Inicial.tm.setVisible(true);
		this.setVisible(false);
    }
	
	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
    }
	
	private void initGravador() {
		gravar = new JButton();
		gravar.setBounds(1100, 100, 100, 50);
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
		pausar.setBounds(1200, 100, 100, 50);
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
		reproduzir.setBounds(1300, 100, 100, 50);
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
		genius.setBounds(1400, 100, 100, 50);
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
						JOptionPane.showMessageDialog(null, "Erro", "Arquivo invalido", JOptionPane.ERROR_MESSAGE);
						// e1.printStackTrace();
					}
				}

			}
		});
	}
	
	private void initMixer() {
		Master_Slider = new JSlider();
        Master_Text = new JLabel();
        Mixer_Text = new JLabel();
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
        
        getContentPane().add(Master_Slider);
        Master_Slider.setBounds(440, 550, 200, 20);
        Master_Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                MasterStateChanged(evt);
            }
        });
        
        Master_Text.setForeground(new Color(255, 255, 255));
        Master_Text.setText("Master");
        getContentPane().add(Master_Text);
        Master_Text.setBounds(525, 574, 50, 16);

        Mixer_Text.setFont(new Font("Times New Roman", 0, 48));
        Mixer_Text.setForeground(new Color(255, 255, 255));
        Mixer_Text.setText("Mixer");
        getContentPane().add(Mixer_Text);
        Mixer_Text.setBounds(490, 20, 180, 90);

        Des_Ondas1.setIcon(new ImageIcon("images/senoidal.png"));
        getContentPane().add(Des_Ondas1);
        Des_Ondas1.setBounds(190, 173, 50, 50);

        Des_Ondas2.setIcon(new ImageIcon("images/senoidal.png"));
        getContentPane().add(Des_Ondas2);
        Des_Ondas2.setBounds(450, 173, 50, 50);

        Des_Ondas3.setIcon(new ImageIcon("images/senoidal.png"));
        getContentPane().add(Des_Ondas3);
        Des_Ondas3.setBounds(710, 173, 50, 50);

        Sel_Ondas1.setModel(new DefaultComboBoxModel<>(new String[] { "Senoidal", "Quadrada", "Triangular", "Dente de Serra", "Desenhar" }));
        Sel_Ondas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sel_Ondas1ActionPerformed(evt);
            }
        });
        getContentPane().add(Sel_Ondas1);
        Sel_Ondas1.setBounds(250, 180, 130, 30);

        Sel_Ondas2.setModel(new DefaultComboBoxModel<>(new String[] { "Senoidal", "Quadrada", "Triangular", "Dente de Serra", "Desenhar" }));
        Sel_Ondas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sel_Ondas2ActionPerformed(evt);
            }
        });
        getContentPane().add(Sel_Ondas2);
        Sel_Ondas2.setBounds(510, 180, 130, 30);

        Sel_Ondas3.setModel(new DefaultComboBoxModel<>(new String[] { "Senoidal", "Quadrada", "Triangular", "Dente de Serra", "Desenhar" }));
        Sel_Ondas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sel_Ondas3ActionPerformed(evt);
            }
        });
        getContentPane().add(Sel_Ondas3);
        Sel_Ondas3.setBounds(770, 180, 130, 30);

        Ampl1.setFont(new Font("Tahoma", 0, 24));;
        Ampl1.setOrientation(JSlider.VERTICAL);
        Ampl1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Ampl1StateChanged(evt);
            }
        });
        getContentPane().add(Ampl1);
        Ampl1.setBounds(195, 230, 35, 200);

        Ampl2.setFont(new Font("Tahoma", 0, 24));
        Ampl2.setOrientation(JSlider.VERTICAL);
        Ampl2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Ampl2StateChanged(evt);
            }
        });
        getContentPane().add(Ampl2);
        Ampl2.setBounds(455, 230, 35, 200);

        Ampl3.setFont(new Font("Tahoma", 0, 24));
        Ampl3.setOrientation(JSlider.VERTICAL);
        Ampl3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Ampl3StateChanged(evt);
            }
        });
        getContentPane().add(Ampl3);
        Ampl3.setBounds(715, 230, 35, 200);

        Freq1.setMaximum(5);
        Freq1.setOrientation(JSlider.VERTICAL);
        Freq1.setValue(2);
        Freq1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Freq1StateChanged(evt);
            }
        });
        getContentPane().add(Freq1);
        Freq1.setBounds(315, 230, 35, 200);

        Freq2.setMaximum(5);
        Freq2.setOrientation(JSlider.VERTICAL);
        Freq2.setValue(2);
        Freq2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Freq2StateChanged(evt);
            }
        });
        getContentPane().add(Freq2);
        Freq2.setBounds(585, 230, 35, 200);

        Freq3.setMaximum(5);
        Freq3.setOrientation(JSlider.VERTICAL);
        Freq3.setValue(2);
        Freq3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                Freq3StateChanged(evt);
            }
        });
        getContentPane().add(Freq3);
        Freq3.setBounds(845, 230, 35, 200);

        Osc_Text1.setFont(new Font("Times New Roman", 0, 24));
        Osc_Text1.setForeground(new Color(255, 255, 255));
        Osc_Text1.setText("Oscilador 1");
        getContentPane().add(Osc_Text1);
        Osc_Text1.setBounds(210, 140, 130, 28);

        Osc_Text2.setFont(new Font("Times New Roman", 0, 24));
        Osc_Text2.setForeground(new Color(255, 255, 255));
        Osc_Text2.setText("Oscilador 2");
        getContentPane().add(Osc_Text2);
        Osc_Text2.setBounds(490, 140, 130, 28);

        Osc_Text3.setFont(new Font("Times New Roman", 0, 24));
        Osc_Text3.setForeground(new Color(255, 255, 255));
        Osc_Text3.setText("Oscilador 3");
        getContentPane().add(Osc_Text3);
        Osc_Text3.setBounds(740, 140, 130, 28);

        Ampl_Text1.setForeground(new Color(255, 255, 255));
        Ampl_Text1.setText("Amplitude");
        getContentPane().add(Ampl_Text1);
        Ampl_Text1.setBounds(184, 440, 57, 16);

        Ampl_Text2.setForeground(new Color(255, 255, 255));
        Ampl_Text2.setText("Amplitude");
        getContentPane().add(Ampl_Text2);
        Ampl_Text2.setBounds(444, 440, 57, 16);

        Ampl_Text3.setForeground(new Color(255, 255, 255));
        Ampl_Text3.setText("Amplitude");
        getContentPane().add(Ampl_Text3);
        Ampl_Text3.setBounds(703, 440, 57, 16);

        Freq_Text1.setForeground(new Color(255, 255, 255));
        Freq_Text1.setText("Frequência");
        getContentPane().add(Freq_Text1);
        Freq_Text1.setBounds(302, 440, 70, 16);

        Freq_Text2.setForeground(new Color(255, 255, 255));
        Freq_Text2.setText("Frequência");
        getContentPane().add(Freq_Text2);
        Freq_Text2.setBounds(572, 440, 70, 16);

        Freq_Text3.setForeground(new Color(255, 255, 255));
        Freq_Text3.setText("Frequência");
        getContentPane().add(Freq_Text3);
        Freq_Text3.setBounds(835, 440, 70, 16);
        
        getContentPane().add(Separador1);
        Separador1.setBounds(150, 120, 780, 10);
        
        getContentPane().add(Separador2);
        Separador2.setBounds(150, 480, 780, 10);

        Separador3.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador3);
        Separador3.setBounds(150, 120, 10, 360);

        Separador4.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador4);
        Separador4.setBounds(410, 120, 30, 360);

        Separador5.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador5);
        Separador5.setBounds(670, 120, 30, 360);

        Separador6.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador6);
        Separador6.setBounds(930, 120, 30, 360);

        Separador7.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador7);
        Separador7.setBounds(270, 480, 20, 80);

        Separador8.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador8);
        Separador8.setBounds(540, 480, 20, 70);

        Separador9.setOrientation(SwingConstants.VERTICAL);
        getContentPane().add(Separador9);
        Separador9.setBounds(800, 480, 20, 80);
        
        getContentPane().add(Separador10);
        Separador10.setBounds(270, 560, 530, 10);
        
        Ampl_Value_Text1.setForeground(new Color(255, 255, 255));
        Ampl_Value_Text1.setText("50");
        getContentPane().add(Ampl_Value_Text1);
        Ampl_Value_Text1.setBounds(230, 240, 41, 16);

        Freq_Value_Text1.setForeground(new Color(255, 255, 255));
        Freq_Value_Text1.setText("2");
        getContentPane().add(Freq_Value_Text1);
        Freq_Value_Text1.setBounds(360, 240, 41, 16);

        Ampl_Value_Text2.setForeground(new Color(255, 255, 255));
        Ampl_Value_Text2.setText("50");
        getContentPane().add(Ampl_Value_Text2);
        Ampl_Value_Text2.setBounds(480, 240, 41, 16);

        Freq_Value_Text2.setForeground(new Color(255, 255, 255));
        Freq_Value_Text2.setText("2");
        getContentPane().add(Freq_Value_Text2);
        Freq_Value_Text2.setBounds(620, 240, 41, 16);

        Ampl_Value_Text3.setForeground(new Color(255, 255, 255));
        Ampl_Value_Text3.setText("50");
        getContentPane().add(Ampl_Value_Text3);
        Ampl_Value_Text3.setBounds(740, 240, 41, 16);

        Freq_Value_Text3.setForeground(new Color(255, 255, 255));
        Freq_Value_Text3.setText("2");
        getContentPane().add(Freq_Value_Text3);
        Freq_Value_Text3.setBounds(880, 240, 41, 16);

        Master_Value_Text.setForeground(new Color(255, 255, 255));
        Master_Value_Text.setText("50");
        getContentPane().add(Master_Value_Text);
        Master_Value_Text.setBounds(590, 560, 41, 16);
	}
	
	private void MasterStateChanged(ChangeEvent evt) {
		Master_Value_Text.setText(String.valueOf(Master_Slider.getValue()));
    }
	
	private void Ampl1StateChanged(ChangeEvent evt) {
		Ampl_Value_Text1.setText(String.valueOf(Ampl1.getValue()));
    }
	
	private void Freq1StateChanged(ChangeEvent evt) {
		Freq_Value_Text1.setText(String.valueOf(Freq1.getValue()));
    }
	
	private void Ampl2StateChanged(ChangeEvent evt) {
		Ampl_Value_Text2.setText(String.valueOf(Ampl2.getValue()));
    }
	
	private void Freq2StateChanged(ChangeEvent evt) {
		Freq_Value_Text2.setText(String.valueOf(Freq2.getValue()));
    }
	
	private void Ampl3StateChanged(ChangeEvent evt) {
		Ampl_Value_Text3.setText(String.valueOf(Ampl3.getValue()));
    }
	
	private void Freq3StateChanged(ChangeEvent evt) {
		Freq_Value_Text3.setText(String.valueOf(Freq3.getValue()));
    }
	
	private void Sel_Ondas1ActionPerformed(java.awt.event.ActionEvent evt) {
		String s = (String) Sel_Ondas1.getSelectedItem();
		if(s == "Senoidal") {
			synth.setOscType(0, "sine");
			Des_Ondas1.setIcon(new ImageIcon("images/senoidal.png"));
		} else if (s == "Quadrada") {
			synth.setOscType(0, "square");
			Des_Ondas1.setIcon(new ImageIcon("images/quadrada.png"));
		} else if (s == "Triangular") {
			synth.setOscType(0, "triangle");
			Des_Ondas1.setIcon(new ImageIcon("images/triangular.png"));
		} else if (s == "Dente de Serra") {
			synth.setOscType(0, "saw");
			Des_Ondas1.setIcon(new ImageIcon("images/dente_serra.png"));
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(0);
			new Thread(pt).start();
		}
	}                                          

	private void Sel_Ondas2ActionPerformed(java.awt.event.ActionEvent evt) {
		String s = (String) Sel_Ondas2.getSelectedItem();
		if(s == "Senoidal") {
			synth.setOscType(1, "sine");
			Des_Ondas2.setIcon(new ImageIcon("images/senoidal.png"));
		} else if (s == "Quadrada") {
			synth.setOscType(1, "square");
			Des_Ondas2.setIcon(new ImageIcon("images/quadrada.png"));
		} else if (s == "Triangular") {
			synth.setOscType(1, "triangle");
			Des_Ondas2.setIcon(new ImageIcon("images/triangular.png"));
		} else if (s == "Dente de Serra") {
			synth.setOscType(1, "saw");
			Des_Ondas2.setIcon(new ImageIcon("images/dente_serra.png"));
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(1);
			new Thread(pt).start();
		}
	}
	
	private void Sel_Ondas3ActionPerformed(java.awt.event.ActionEvent evt) {
		String s = (String) Sel_Ondas3.getSelectedItem();
		if(s == "Senoidal") {
			synth.setOscType(2, "sine");
			Des_Ondas3.setIcon(new ImageIcon("images/senoidal.png"));
		} else if (s == "Quadrada") {
			synth.setOscType(2, "square");
			Des_Ondas3.setIcon(new ImageIcon("images/quadrada.png"));
		} else if (s == "Triangular") {
			synth.setOscType(2, "triangle");
			Des_Ondas3.setIcon(new ImageIcon("images/triangular.png"));
		} else if (s == "Dente de Serra") {
			synth.setOscType(2, "saw");
			Des_Ondas3.setIcon(new ImageIcon("images/dente_serra.png"));
		} else if (s == "Desenhar") {
			PaintThread pt = new PaintThread(2);
			new Thread(pt).start();
		}
	}
	
	class PaintThread extends Thread {
		int oscTarget;
		
		public PaintThread(int oscTarget){
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
	
	public void initTeclado() {
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
    	
    	/* Barra Menu */
    	
		Bar.setFocusable(false);
    	menu.setFocusable(false);
    	exit.setFocusable(false);
    	MIDI.setFocusable(false);
    	
    	/* JSlider's */
    	
    	Ampl1.setFocusable(false);
        Ampl2.setFocusable(false);
        Ampl3.setFocusable(false);
        
        Freq1.setFocusable(false);
        Freq2.setFocusable(false);
        Freq3.setFocusable(false);
        
        Master_Slider.setFocusable(false);
        
        /* JComboBox */
        
        Sel_Ondas1.setFocusable(false);
        Sel_Ondas2.setFocusable(false);
        Sel_Ondas3.setFocusable(false);
    }
}