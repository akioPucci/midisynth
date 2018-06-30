package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Redimensionamento {
	private Toolkit tk;
	private Dimension d;
	private int w_projetado; // Largura da tela em que foi projetado
	private int h_projetado; // Altura da tela em que foi projetado
	public int w_Resizable; // Largura da tela alterada
	public int h_Resizable; // Altura da tela alterada
	
	public Redimensionamento(int w, int h) {
		tk = Toolkit.getDefaultToolkit();
	    d = tk.getScreenSize();
	    w_projetado = w;
	    h_projetado = h;
	    w_Resizable = w;
	    h_Resizable = h;
	}
	
	public int ProporcaoW(int x) {
		double pw;
		
		pw = (w_Resizable / (double) w_projetado) * (d.width / 1920.0);
		
		return (int)(pw*x);
	}
	
	public int ProporcaoH(int y) {
	    double ph;
	    
	    ph = (h_Resizable / (double) h_projetado) * (d.height / 1080.0); 
	    
		return (int)(ph*y);
	}
	
	public ImageIcon redimensionarImg(Image img, int new_w, int new_h) throws IOException {
		BufferedImage new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();
		g.drawImage(img, 0, 0, new_w, new_h, null);
		g.dispose();

		return new ImageIcon(new_img);		
	}
}
