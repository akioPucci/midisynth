package gui;

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
    
    private Icon icone;
    
    public Teclado() {
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
        
        icone = new ImageIcon("images/1.jpg");
        
        DoSus1.setIcon(icone);
        DoSus1.setBounds(120, 602, 45, 215);

        ReSus1.setIcon(icone);
        ReSus1.setBounds(215, 602, 45, 215);

        FaSus1.setIcon(icone);
        FaSus1.setBounds(340, 602, 45, 215);

        SolSus1.setIcon(icone);
        SolSus1.setBounds(427, 602, 46, 215);

        LaSus1.setIcon(icone);
        LaSus1.setBounds(515, 602, 45, 215);
        
        DoSus2.setIcon(icone);
        DoSus2.setBounds(640, 602, 45, 215);

        ReSus2.setIcon(icone);
        ReSus2.setBounds(740, 602, 45, 215);

        FaSus2.setIcon(icone);
        FaSus2.setBounds(865, 602, 45, 215);

        SolSus2.setIcon(icone);
        SolSus2.setBounds(952, 602, 45, 215);

        LaSus2.setIcon(icone);
        LaSus2.setBounds(1040, 602, 45, 215);
        
        DoSus3.setIcon(icone);
        DoSus3.setBounds(1165, 602, 45, 215);

        ReSus3.setIcon(icone);
        ReSus3.setBounds(1265, 602, 45, 215);

        FaSus3.setIcon(icone);
        FaSus3.setBounds(1390, 602, 45, 215);

        SolSus3.setIcon(icone);
        SolSus3.setBounds(1477, 602, 45, 215);

        LaSus3.setIcon(icone);
        LaSus3.setBounds(1565, 602, 45, 215);
        
        icone = new ImageIcon("images/2.png");

        Do1.setIcon(icone);
        Do1.setBounds(75, 602, 75, 350);

        Re1.setIcon(icone);
        Re1.setBounds(150, 602, 75, 350);

        Mi1.setIcon(icone);
        Mi1.setBounds(225, 602, 75, 350);

        Fa1.setIcon(icone);
        Fa1.setBounds(300, 602, 75, 350);

        Sol1.setIcon(icone);
        Sol1.setBounds(375, 602, 75, 350);

        La1.setIcon(icone);
        La1.setBounds(450, 602, 75, 350);

        Si1.setIcon(icone);
        Si1.setBounds(525, 602, 75, 350);

        Do2.setIcon(icone);
        Do2.setBounds(600, 602, 75, 350);

        Re2.setIcon(icone);
        Re2.setBounds(675, 602, 75, 350);

        Mi2.setIcon(icone);
        Mi2.setBounds(750, 602, 75, 350);

        Fa2.setIcon(icone);
        Fa2.setBounds(825, 602, 75, 350);

        Sol2.setIcon(icone);
        Sol2.setBounds(900, 602, 75, 350);

        La2.setIcon(icone);
        La2.setBounds(975, 602, 75, 350);

        Si2.setIcon(icone);
        Si2.setBounds(1050, 602, 75, 350);

        Do3.setIcon(icone);
        Do3.setBounds(1125, 602, 75, 350);

        Re3.setIcon(icone);
        Re3.setBounds(1200, 602, 75, 350);

        Mi3.setIcon(icone);
        Mi3.setBounds(1275, 602, 75, 350);

        Fa3.setIcon(icone);
        Fa3.setBounds(1350, 602, 75, 350);

        Sol3.setIcon(icone);
        Sol3.setBounds(1425, 602, 75, 350);

        La3.setIcon(icone);
        La3.setBounds(1500, 602, 75, 350);

        Si3.setIcon(icone);
        Si3.setBounds(1575, 602, 75, 350);
        
        Do4.setIcon(icone);
        Do4.setBounds(1650, 602, 75, 350);
    }
    

	public JButton[] createJButtonArray() {
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