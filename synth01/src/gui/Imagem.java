package gui;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Imagem {
	public BufferedImage imagem;
	public int hmax, wmax;
	
	public Imagem(String nome) throws IOException {
		File f = new File(nome);
		this.imagem = ImageIO.read(f);
		this.hmax = this.imagem.getHeight();
		this.wmax = this.imagem.getWidth();
	}
}