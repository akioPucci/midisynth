package gui;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Teclado{
    public JButton Do1;
    public JButton Do2;
    public JButton Do3;
    public JButton Do4;
    public JButton DoSus1;
    public JButton DoSus2;
    public JButton DoSus3;
    
    public JButton Fa1;
    public JButton Fa2;
    public JButton Fa3;
    public JButton FaSus1;
    public JButton FaSus2;
    public JButton FaSus3;
    
    public JButton La1;
    public JButton La2;
    public JButton La3;
    public JButton LaSus1;
    public JButton LaSus2;
    public JButton LaSus3;
    
    public JButton Mi1;
    public JButton Mi2;
    public JButton Mi3;
    
    public JButton Re1;
    public JButton Re2;
    public JButton Re3;
    public JButton ReSus1;
    public JButton ReSus2;
    public JButton ReSus3;
    
    public JButton Si1;
    public JButton Si2;
    public JButton Si3;
    
    public JButton Sol1;
    public JButton Sol2;
    public JButton Sol3;
    public JButton SolSus1;
    public JButton SolSus2;
    public JButton SolSus3;
    
    private Icon icone1;
    private Icon icone2;
    private static Redimensionamento p;
    
    public Teclado() {
    	p = new Redimensionamento(1807, 1036);
    	
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
        
        icone1 = new ImageIcon();
        icone2 = new ImageIcon();
        
        try {
			Imagem img = new Imagem("images\\1.jpg");
			icone1 = p.redimensionarImg(img.imagem, 
					p.ProporcaoW(img.wmax), 
					p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        DoSus1.setIcon(icone1);
        DoSus1.setBounds(p.ProporcaoW(120), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus1.setIcon(icone1);
        ReSus1.setBounds(p.ProporcaoW(215), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        FaSus1.setIcon(icone1);
        FaSus1.setBounds(p.ProporcaoW(340), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus1.setIcon(icone1);
        SolSus1.setBounds(p.ProporcaoW(427), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus1.setIcon(icone1);
        LaSus1.setBounds(p.ProporcaoW(515), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        DoSus2.setIcon(icone1);
        DoSus2.setBounds(p.ProporcaoW(640), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus2.setIcon(icone1);
        ReSus2.setBounds(p.ProporcaoW(740), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        FaSus2.setIcon(icone1);
        FaSus2.setBounds(p.ProporcaoW(865), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus2.setIcon(icone1);
        SolSus2.setBounds(p.ProporcaoW(952), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus2.setIcon(icone1);
        LaSus2.setBounds(p.ProporcaoW(1040), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        DoSus3.setIcon(icone1);
        DoSus3.setBounds(p.ProporcaoW(1165), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus3.setIcon(icone1);
        ReSus3.setBounds(p.ProporcaoW(1265), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        FaSus3.setIcon(icone1);
        FaSus3.setBounds(p.ProporcaoW(1390), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus3.setIcon(icone1);
        SolSus3.setBounds(p.ProporcaoW(1477), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus3.setIcon(icone1);
        LaSus3.setBounds(p.ProporcaoW(1565), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
                
        try {
			Imagem img = new Imagem("images\\2.png");
			icone2 = p.redimensionarImg(img.imagem, 
					p.ProporcaoW(img.wmax), 
					p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Do1.setIcon(icone2);
        Do1.setBounds(p.ProporcaoW(75), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re1.setIcon(icone2);
        Re1.setBounds(p.ProporcaoW(150), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi1.setIcon(icone2);
        Mi1.setBounds(p.ProporcaoW(225), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa1.setIcon(icone2);
        Fa1.setBounds(p.ProporcaoW(300), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol1.setIcon(icone2);
        Sol1.setBounds(p.ProporcaoW(375), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La1.setIcon(icone2);
        La1.setBounds(p.ProporcaoW(450), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si1.setIcon(icone2);
        Si1.setBounds(p.ProporcaoW(525), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Do2.setIcon(icone2);
        Do2.setBounds(p.ProporcaoW(600), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re2.setIcon(icone2);
        Re2.setBounds(p.ProporcaoW(675), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi2.setIcon(icone2);
        Mi2.setBounds(p.ProporcaoW(750), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa2.setIcon(icone2);
        Fa2.setBounds(p.ProporcaoW(825), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol2.setIcon(icone2);
        Sol2.setBounds(p.ProporcaoW(900), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La2.setIcon(icone2);
        La2.setBounds(p.ProporcaoW(975), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si2.setIcon(icone2);
        Si2.setBounds(p.ProporcaoW(1050), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Do3.setIcon(icone2);
        Do3.setBounds(p.ProporcaoW(1125), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re3.setIcon(icone2);
        Re3.setBounds(p.ProporcaoW(1200), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi3.setIcon(icone2);
        Mi3.setBounds(p.ProporcaoW(1275), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa3.setIcon(icone2);
        Fa3.setBounds(p.ProporcaoW(1350), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol3.setIcon(icone2);
        Sol3.setBounds(p.ProporcaoW(1425), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La3.setIcon(icone2);
        La3.setBounds(p.ProporcaoW(1500), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si3.setIcon(icone2);
        Si3.setBounds(p.ProporcaoW(1575), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));
        
        Do4.setIcon(icone2);
        Do4.setBounds(p.ProporcaoW(1650), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));
    }
    

	public JButton[] createJButtonArray() {
		JButton button[] = new JButton[37];

		button[0] = this.Do1;
		button[1] = this.DoSus1;
		button[2] = this.Re1;
		button[3] = this.ReSus1;
		button[4] = this.Mi1;
		button[5] = this.Fa1;
		button[6] = this.FaSus1;
		button[7] = this.Sol1;
		button[8] = this.SolSus1;
		button[9] = this.La1;
		button[10] = this.LaSus1;
		button[11] = this.Si1;

		button[12] = this.Do2;
		button[13] = this.DoSus2;
		button[14] = this.Re2;
		button[15] = this.ReSus2;
		button[16] = this.Mi2;
		button[17] = this.Fa2;
		button[18] = this.FaSus2;
		button[19] = this.Sol2;
		button[20] = this.SolSus2;
		button[21] = this.La2;
		button[22] = this.LaSus2;
		button[23] = this.Si2;

		button[24] = this.Do3;
		button[25] = this.DoSus3;
		button[26] = this.Re3;
		button[27] = this.ReSus3;
		button[28] = this.Mi3;
		button[29] = this.Fa3;
		button[30] = this.FaSus3;
		button[31] = this.Sol3;
		button[32] = this.SolSus3;
		button[33] = this.La3;
		button[34] = this.LaSus3;
		button[35] = this.Si3;

		button[36] = this.Do4;

		return button;
	}
	
	public void AtualizarTeclado(int w, int h) {
		p.w_Resizable = w;
		p.h_Resizable = h;
		
		try {
			Imagem img = new Imagem("images\\1.jpg");
			icone1 = p.redimensionarImg(img.imagem, 
					p.ProporcaoW(img.wmax), 
					p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Imagem img = new Imagem("images\\2.png");
			icone2 = p.redimensionarImg(img.imagem, 
					p.ProporcaoW(img.wmax), 
					p.ProporcaoH(img.hmax));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        DoSus1.setBounds(p.ProporcaoW(120), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus1.setBounds(p.ProporcaoW(215), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        FaSus1.setBounds(p.ProporcaoW(340), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus1.setBounds(p.ProporcaoW(427), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus1.setBounds(p.ProporcaoW(515), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        DoSus2.setBounds(p.ProporcaoW(640), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus2.setBounds(p.ProporcaoW(740), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        FaSus2.setBounds(p.ProporcaoW(865), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus2.setBounds(p.ProporcaoW(952), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus2.setBounds(p.ProporcaoW(1040), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        DoSus3.setBounds(p.ProporcaoW(1165), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        ReSus3.setBounds(p.ProporcaoW(1265), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        FaSus3.setBounds(p.ProporcaoW(1390), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        SolSus3.setBounds(p.ProporcaoW(1477), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));

        LaSus3.setBounds(p.ProporcaoW(1565), p.ProporcaoH(602), p.ProporcaoW(45), 
        		p.ProporcaoH(215));
        
        Do1.setBounds(p.ProporcaoW(75), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re1.setBounds(p.ProporcaoW(150), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi1.setBounds(p.ProporcaoW(225), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa1.setBounds(p.ProporcaoW(300), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol1.setBounds(p.ProporcaoW(375), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La1.setBounds(p.ProporcaoW(450), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si1.setBounds(p.ProporcaoW(525), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Do2.setBounds(p.ProporcaoW(600), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re2.setBounds(p.ProporcaoW(675), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi2.setBounds(p.ProporcaoW(750), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa2.setBounds(p.ProporcaoW(825), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol2.setBounds(p.ProporcaoW(900), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La2.setBounds(p.ProporcaoW(975), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si2.setBounds(p.ProporcaoW(1050), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Do3.setBounds(p.ProporcaoW(1125), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Re3.setBounds(p.ProporcaoW(1200), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Mi3.setBounds(p.ProporcaoW(1275), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Fa3.setBounds(p.ProporcaoW(1350), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Sol3.setBounds(p.ProporcaoW(1425), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        La3.setBounds(p.ProporcaoW(1500), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));

        Si3.setBounds(p.ProporcaoW(1575), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));
        
        Do4.setBounds(p.ProporcaoW(1650), p.ProporcaoH(602), p.ProporcaoW(75), 
        		p.ProporcaoH(350));
	}
}