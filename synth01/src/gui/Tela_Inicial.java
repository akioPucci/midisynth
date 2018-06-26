package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Tela_Inicial extends JFrame{
	
	public Tela_Inicial() {
		initTela();
		initComponents();
	}
	
	public void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 799));
        setResizable(false); // Impede de alterar tamanho da tela
        setLocationRelativeTo(null); // Centro da tela
        setVisible(true);
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
	
	public void initComponents() {
		JButton MIDI = new JButton();
		JButton synth = new JButton();
		JLabel planoFundo = new JLabel();
		
		MIDI.setFont(new Font("Segoe Script", 0, 48));
        MIDI.setForeground(new Color(255, 0, 255));
        MIDI.setText("MIDI");
        MIDI.setBorder(BorderFactory.createBevelBorder
        		(0, new Color(153, 0, 153), 
        		new Color(102, 0, 153), 
        		new Color(102, 204, 255), 
        		new Color(102, 255, 204)));
        MIDI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MIDIActionPerformed(evt);
            }
        });
        getContentPane().add(MIDI);
        MIDI.setBounds(175, 300, 350, 200);

        synth.setFont(new Font("Segoe Script", 0, 48));
        synth.setForeground(new Color(255, 0, 255));
        synth.setText("Sintetizador");
        synth.setBorder(BorderFactory.createBevelBorder
        		(0, new Color(153, 0, 153), 
        		new Color(102, 0, 153), 
        		new Color(102, 204, 255), 
        		new Color(102, 255, 204)));
        synth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                synthActionPerformed(evt);
            }
        });
        getContentPane().add(synth);
        synth.setBounds(650, 300, 350, 200);

        planoFundo.setIcon(new ImageIcon("images/music.jpg"));
        getContentPane().add(planoFundo);
        planoFundo.setBounds(0, 0, 1200, 799);

        pack();
	}
	
	private void MIDIActionPerformed(ActionEvent evt) {
		Tela_MIDI obj = new Tela_MIDI();
		obj.setVisible(true);
		this.setVisible(false);
    }                                    

    private void synthActionPerformed(ActionEvent evt) {
    	Tela_Sintetizador obj = new Tela_Sintetizador();
    	obj.setVisible(true);
    	this.setVisible(false);
    }
}
