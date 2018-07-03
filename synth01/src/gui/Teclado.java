package gui;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Teclado {
    public static JButton Do1;
    public static JButton Do2;
    public static JButton Do3;
    public static JButton Do4;
    public static JButton DoSus1;
    public static JButton DoSus2;
    public static JButton DoSus3;
    
    public static JButton Fa1;
    public static JButton Fa2;
    public static JButton Fa3;
    public static JButton FaSus1;
    public static JButton FaSus2;
    public static JButton FaSus3;
    
    public static JButton La1;
    public static JButton La2;
    public static JButton La3;
    public static JButton LaSus1;
    public static JButton LaSus2;
    public static JButton LaSus3;
    
    public static JButton Mi1;
    public static JButton Mi2;
    public static JButton Mi3;
    
    public static JButton Re1;
    public static JButton Re2;
    public static JButton Re3;
    public static JButton ReSus1;
    public static JButton ReSus2;
    public static JButton ReSus3;
    
    public static JButton Si1;
    public static JButton Si2;
    public static JButton Si3;
    
    public static JButton Sol1;
    public static JButton Sol2;
    public static JButton Sol3;
    public static JButton SolSus1;
    public static JButton SolSus2;
    public static JButton SolSus3;
        
    public static Icon tecla_preta;
    public static Icon tecla_branca;
    public static Icon tecla_preta_press;
    public static Icon tecla_branca_press;
    
    public Teclado() {
    	redimensionarIcones();
    	
    	DoSus1 = new JButton();
        ReSus1 = new JButton();
        FaSus1 = new JButton();
        SolSus1 = new JButton();
        LaSus1 = new JButton();
        
        Do1 = new JButton();
        Re1 = new JButton();
        Mi1 = new JButton();
        Fa1 = new JButton();
        Sol1 = new JButton();
        La1 = new JButton();
        Si1 = new JButton();
        
        DoSus2 = new JButton();
        ReSus2 = new JButton();
        FaSus2 = new JButton();
        SolSus2 = new JButton();
        LaSus2 = new JButton();
        
        Do2 = new JButton();
        Re2 = new JButton();
        Mi2 = new JButton();
        Fa2 = new JButton();
        Sol2 = new JButton();
        La2 = new JButton();
        Si2 = new JButton();
        
        DoSus3 = new JButton();
        ReSus3 = new JButton();
        FaSus3 = new JButton();
        SolSus3 = new JButton();
        LaSus3 = new JButton();
        
        Do3 = new JButton();
        Re3 = new JButton();
        Mi3 = new JButton();
        Fa3 = new JButton();
        Sol3 = new JButton();
        La3 = new JButton();
        Si3 = new JButton();
        
        Do4 = new JButton();
                
        DoSus1.setIcon(tecla_preta);
        DoSus1.setBounds(TelaInicial.p.ProporcaoW(180), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        ReSus1.setIcon(tecla_preta);
        ReSus1.setBounds(TelaInicial.p.ProporcaoW(275), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));
        
        FaSus1.setIcon(tecla_preta);
        FaSus1.setBounds(TelaInicial.p.ProporcaoW(400), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        SolSus1.setIcon(tecla_preta);
        SolSus1.setBounds(TelaInicial.p.ProporcaoW(487), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        LaSus1.setIcon(tecla_preta);
        LaSus1.setBounds(TelaInicial.p.ProporcaoW(575), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));
        
        DoSus2.setIcon(tecla_preta);
        DoSus2.setBounds(TelaInicial.p.ProporcaoW(700), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        ReSus2.setIcon(tecla_preta);
        ReSus2.setBounds(TelaInicial.p.ProporcaoW(800), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        FaSus2.setIcon(tecla_preta);
        FaSus2.setBounds(TelaInicial.p.ProporcaoW(925), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        SolSus2.setIcon(tecla_preta);
        SolSus2.setBounds(TelaInicial.p.ProporcaoW(1012), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        LaSus2.setIcon(tecla_preta);
        LaSus2.setBounds(TelaInicial.p.ProporcaoW(1100), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));
        
        DoSus3.setIcon(tecla_preta);
        DoSus3.setBounds(TelaInicial.p.ProporcaoW(1225), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        ReSus3.setIcon(tecla_preta);
        ReSus3.setBounds(TelaInicial.p.ProporcaoW(1325), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        FaSus3.setIcon(tecla_preta);
        FaSus3.setBounds(TelaInicial.p.ProporcaoW(1450), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        SolSus3.setIcon(tecla_preta);
        SolSus3.setBounds(TelaInicial.p.ProporcaoW(1537), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));

        LaSus3.setIcon(tecla_preta);
        LaSus3.setBounds(TelaInicial.p.ProporcaoW(1625), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(45), TelaInicial.p.ProporcaoH(215));
                
        Do1.setIcon(tecla_branca);
        Do1.setBounds(TelaInicial.p.ProporcaoW(135), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Re1.setIcon(tecla_branca);
        Re1.setBounds(TelaInicial.p.ProporcaoW(210), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Mi1.setIcon(tecla_branca);
        Mi1.setBounds(TelaInicial.p.ProporcaoW(285), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Fa1.setIcon(tecla_branca);
        Fa1.setBounds(TelaInicial.p.ProporcaoW(360), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Sol1.setIcon(tecla_branca);
        Sol1.setBounds(TelaInicial.p.ProporcaoW(435), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        La1.setIcon(tecla_branca);
        La1.setBounds(TelaInicial.p.ProporcaoW(510), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Si1.setIcon(tecla_branca);
        Si1.setBounds(TelaInicial.p.ProporcaoW(585), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Do2.setIcon(tecla_branca);
        Do2.setBounds(TelaInicial.p.ProporcaoW(660), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Re2.setIcon(tecla_branca);
        Re2.setBounds(TelaInicial.p.ProporcaoW(735), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Mi2.setIcon(tecla_branca);
        Mi2.setBounds(TelaInicial.p.ProporcaoW(810), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Fa2.setIcon(tecla_branca);
        Fa2.setBounds(TelaInicial.p.ProporcaoW(885), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Sol2.setIcon(tecla_branca);
        Sol2.setBounds(TelaInicial.p.ProporcaoW(960), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        La2.setIcon(tecla_branca);
        La2.setBounds(TelaInicial.p.ProporcaoW(1035), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Si2.setIcon(tecla_branca);
        Si2.setBounds(TelaInicial.p.ProporcaoW(1110), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Do3.setIcon(tecla_branca);
        Do3.setBounds(TelaInicial.p.ProporcaoW(1185), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Re3.setIcon(tecla_branca);
        Re3.setBounds(TelaInicial.p.ProporcaoW(1260), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Mi3.setIcon(tecla_branca);
        Mi3.setBounds(TelaInicial.p.ProporcaoW(1335), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Fa3.setIcon(tecla_branca);
        Fa3.setBounds(TelaInicial.p.ProporcaoW(1410), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Sol3.setIcon(tecla_branca);
        Sol3.setBounds(TelaInicial.p.ProporcaoW(1485), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        La3.setIcon(tecla_branca);
        La3.setBounds(TelaInicial.p.ProporcaoW(1560), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));

        Si3.setIcon(tecla_branca);
        Si3.setBounds(TelaInicial.p.ProporcaoW(1635), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));
        
        Do4.setIcon(tecla_branca);
        Do4.setBounds(TelaInicial.p.ProporcaoW(1710), TelaInicial.p.ProporcaoH(635), 
        		TelaInicial.p.ProporcaoW(75), TelaInicial.p.ProporcaoH(350));
    }
    
    private void redimensionarIcones() {
    	tecla_preta = new ImageIcon();
        tecla_branca = new ImageIcon();
        tecla_preta_press = new ImageIcon();
        tecla_branca_press = new ImageIcon();
        
        try {
			Imagem img = new Imagem("images\\1.jpg");
			tecla_preta = TelaInicial.p.redimensionarImg(img.imagem, 
					TelaInicial.p.ProporcaoW(img.wmax), 
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			Imagem img = new Imagem("images\\1-press.png");
			tecla_preta_press = TelaInicial.p.redimensionarImg(img.imagem, 
					TelaInicial.p.ProporcaoW(img.wmax), 
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			Imagem img = new Imagem("images\\2.png");
			tecla_branca = TelaInicial.p.redimensionarImg(img.imagem, 
					TelaInicial.p.ProporcaoW(img.wmax), 
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			Imagem img = new Imagem("images\\2-press.png");
			tecla_branca_press = TelaInicial.p.redimensionarImg(img.imagem, 
					TelaInicial.p.ProporcaoW(img.wmax), 
					TelaInicial.p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public static JButton[] createJButtonArray() {
		JButton button[] = new JButton[37];

		button[0] = Do1;
		button[1] = DoSus1;
		button[2] = Re1;
		button[3] = ReSus1;
		button[4] = Mi1;
		button[5] = Fa1;
		button[6] = FaSus1;
		button[7] = Sol1;
		button[8] = SolSus1;
		button[9] = La1;
		button[10] = LaSus1;
		button[11] = Si1;

		button[12] = Do2;
		button[13] = DoSus2;
		button[14] = Re2;
		button[15] = ReSus2;
		button[16] = Mi2;
		button[17] = Fa2;
		button[18] = FaSus2;
		button[19] = Sol2;
		button[20] = SolSus2;
		button[21] = La2;
		button[22] = LaSus2;
		button[23] = Si2;

		button[24] = Do3;
		button[25] = DoSus3;
		button[26] = Re3;
		button[27] = ReSus3;
		button[28] = Mi3;
		button[29] = Fa3;
		button[30] = FaSus3;
		button[31] = Sol3;
		button[32] = SolSus3;
		button[33] = La3;
		button[34] = LaSus3;
		button[35] = Si3;

		button[36] = Do4;

		return button;
	}
}