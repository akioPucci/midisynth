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

import key.KeyManagement;

@SuppressWarnings("serial")
public class Tela_MIDI extends JFrame {
	public static Teclado t;
	public JMenuBar Bar;
	public JMenu menu;
	public JMenuItem exit;
	public JMenuItem synth;

	private JButton gravar;
	private JButton reproduzir;
	private JButton pausar;
	private JButton genius;

	public Tela_MIDI() {
		t = new Teclado();
		initTela();
		initMenu();
		initTeclado();
		setAllNotFocusable();
		KeyManagement.create(this, t.createJButtonArray(), 0);
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

	public static void setNotVisible(int i) {
		t.createJButtonArray()[i].setVisible(false);
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
		JButton button[] = t.createJButtonArray();

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
	}
}