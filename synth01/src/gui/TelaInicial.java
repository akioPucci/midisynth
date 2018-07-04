package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class TelaInicial extends JFrame {
	public static TelaSintetizador ts;
	public static TelaMIDI tm;
	public static Redimensionamento p;
	
	public TelaInicial(){
		p = new Redimensionamento();
		initTela();
		initComponents();
	}
	
	private void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(p.ProporcaoW(1200), p.ProporcaoH(799)));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
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
			java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
	private void initComponents(){
		JButton MIDI = new JButton();
		JButton synth = new JButton();
		JLabel planoFundo = new JLabel();
		
		MIDI.setFont(new Font("Segoe Script", 0, p.ProporcaoFonte(48)));
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
        MIDI.setBounds(p.ProporcaoW(175), p.ProporcaoH(300), p.ProporcaoW(350), p.ProporcaoH(200));

        synth.setFont(new Font("Segoe Script", 0, p.ProporcaoFonte(48)));
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
        synth.setBounds(p.ProporcaoW(650), p.ProporcaoH(300), p.ProporcaoW(350), p.ProporcaoH(200));
        
        try {
			Imagem img = new Imagem("images/music.jpg");
			planoFundo.setIcon(p.redimensionarImg(img.imagem, p.ProporcaoW(img.wmax),
					p.ProporcaoH(img.hmax)));
		} catch (IOException e) {
			e.printStackTrace();
		}
        getContentPane().add(planoFundo);
        planoFundo.setBounds(0, 0, p.ProporcaoW(1200), p.ProporcaoH(799));

        pack();
	}
	
	private void MIDIActionPerformed(ActionEvent evt) {
		tm = new TelaMIDI();
		tm.setVisible(true);
		this.setVisible(false);
    }                                    

    private void synthActionPerformed(ActionEvent evt) {
    	ts = new TelaSintetizador();
    	ts.setVisible(true);
    	this.setVisible(false);
    }
}
