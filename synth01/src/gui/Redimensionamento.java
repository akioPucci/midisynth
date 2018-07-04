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
	
	public Redimensionamento() {
		tk = Toolkit.getDefaultToolkit();
	    d = tk.getScreenSize();
	}
	
	public int ProporcaoW(int x) {
		double pw, w = 1920.0;
		
		pw = d.width / w;
		
		return (int)(pw*x);
	}
	
	public int ProporcaoH(int y) {
	    double ph, h = 1080.0;
	    
	    ph = d.height / h; 
	    		
		return (int)(ph*y);
	}
	
	public int ProporcaoFonte(int pt) {
		double pw, w = 1920.0;
		
		pw = (d.width / w) * (3/4);
		
		return (int)(pt*pw);
	}
	
	public ImageIcon redimensionarImg(Image img, int new_w, int new_h) throws IOException {
		BufferedImage new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();
		g.drawImage(img, 0, 0, new_w, new_h, null);
		g.dispose();

		return new ImageIcon(new_img);		
	}
}
