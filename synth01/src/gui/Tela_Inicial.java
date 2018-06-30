package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Tela_Inicial extends JFrame{
	public static Tela_Sintetizador ts;
	public static Tela_MIDI tm;
	private static Redimensionamento p;
	private JButton MIDI;
	private JButton synth;
	private JLabel planoFundo;
	
	public Tela_Inicial(){
		p = new Redimensionamento(1200, 799);
		tm = new Tela_MIDI();
		ts = new Tela_Sintetizador();
		initTela();
		initComponents();
		setAllNotFocusable();
		pack();
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				p.w_Resizable = getContentPane().getWidth();
				p.h_Resizable = getContentPane().getHeight();
				Atualizar_Tela();
			}
		});
	}
	
	public void Atualizar_Tela() {		
		MIDI.setFont(new Font("Segoe Script", 0, p.ProporcaoW(48)));
		MIDI.setBounds(p.ProporcaoW(175), p.ProporcaoH(300), p.ProporcaoW(350),
        		p.ProporcaoH(200));
		
		synth.setFont(new Font("Segoe Script", 0, p.ProporcaoW(48)));
		synth.setBounds(p.ProporcaoW(650), p.ProporcaoH(300), p.ProporcaoW(350),
        		p.ProporcaoH(200));
		
		try {
			Imagem img = new Imagem("images\\music.jpg");
			planoFundo.setIcon(p.redimensionarImg(img.imagem, p.ProporcaoW(img.wmax),
					p.ProporcaoH(img.hmax)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		planoFundo.setBounds(0, 0, p.ProporcaoW(1200), p.ProporcaoH(799));
		this.repaint();
	}
	
	public void initTela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(p.ProporcaoW(1200), p.ProporcaoH(799)));
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
	
	public void initComponents(){
		MIDI = new JButton();
		synth = new JButton();
		planoFundo = new JLabel();
		
		MIDI.setFont(new Font("Segoe Script", 0, p.ProporcaoW(48)));
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
        MIDI.setBounds(p.ProporcaoW(175), p.ProporcaoH(300), p.ProporcaoW(350),
        		p.ProporcaoH(200));

        synth.setFont(new Font("Segoe Script", 0, p.ProporcaoW(48)));
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
        synth.setBounds(p.ProporcaoW(650), p.ProporcaoH(300), p.ProporcaoW(350),
        		p.ProporcaoH(200));
        
        try {
			Imagem img = new Imagem("images\\music.jpg");
			planoFundo.setIcon(p.redimensionarImg(img.imagem, p.ProporcaoW(img.wmax),
					p.ProporcaoH(img.hmax)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        getContentPane().add(planoFundo);
        planoFundo.setBounds(0, 0, p.ProporcaoW(1200), p.ProporcaoH(799));
	}
	
	private void MIDIActionPerformed(ActionEvent evt) {
		tm.setVisible(true);
		this.setVisible(false);
    }                                    

    private void synthActionPerformed(ActionEvent evt) {
    	ts.setVisible(true);
    	this.setVisible(false);
    }
    
    private void setAllNotFocusable() {
		MIDI.setFocusable(false);
		synth.setFocusable(false);
	}
}
