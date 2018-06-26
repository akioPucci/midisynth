package gui;

import java.awt.Color;

import javax.swing.WindowConstants;

import genius.Genius;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import key.KeyManagement;

@SuppressWarnings("serial")
public class Tela_Sintetizador extends JFrame{
	public Teclado t;
	public JMenuBar Bar;
    public JMenu menu;
    public JMenuItem exit;
    public JMenuItem MIDI;
    
	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;
	
	public Tela_Sintetizador() {
		t = new Teclado();
		initTeclado();
		initTela();
		initMenu();
		initMixer();
		setAllNotFocusable();
		KeyManagement.create(this, this.createJButtonArray(), 1);
	}
	
	public void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(22, 24, 32));
        setMinimumSize(new Dimension(1807, 1036));
        setResizable(false); // Impede de alterar tamanho da tela
        setLocationRelativeTo(null); // Centro da tela
        getContentPane().setLayout(null);
	}
	
	public void initMenu() {
		
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
    	Tela_MIDI obj = new Tela_MIDI();
    	obj.setVisible(true);
    	this.setVisible(false);
    }
	
	private void exitActionPerformed(ActionEvent evt) {
		System.exit(0);
    }
	
	private void initMixer() {
		
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
	}
	
	private void setAllNotFocusable() {
    	JButton button[] = createJButtonArray();
    	
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
    	MIDI.setFocusable(false);
    }
	
	public JButton[] createJButtonArray() {
		JButton button[] = new JButton[37];
	    	
        button[0] = t.Do1;
        button[1] = t.DoSus1;
        button[2] = t.Re1;
        button[3] = t.ReSus1;
        button[4] = t.Mi1;
        button[5] = t.Fa1;
        button[6] = t.FaSus1;
        button[7] = t.Sol1;
        button[8] = t.SolSus1;
        button[9] = t.La1;
        button[10] = t.LaSus1;
        button[11] = t.Si1;
            
        button[12] = t.Do2;
        button[13] = t.DoSus2;
        button[14] = t.Re2;
        button[15] = t.ReSus2;
        button[16] = t.Mi2;
        button[17] = t.Fa2;
        button[18] = t.FaSus2;
        button[19] = t.Sol2;
        button[20] = t.SolSus2;
        button[21] = t.La2;
        button[22] = t.LaSus2;
        button[23] = t.Si2;
            
        button[24] = t.Do3;
        button[25] = t.DoSus3;
        button[26] = t.Re3;
        button[27] = t.ReSus3;
        button[28] = t.Mi3;
        button[29] = t.Fa3;
        button[30] = t.FaSus3;
        button[31] = t.Sol3;
        button[32] = t.SolSus3;
        button[33] = t.La3;
        button[34] = t.LaSus3;
        button[35] = t.Si3;
            
        button[36] = t.Do4;
	    	
	    return button;
	}
}