package paint;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import key.Pair;

/**
 * Paint Canvas to draw the wave
 * 
 * @author Carolina Arenas Okawa
 * @author Eric
 * @author Fernando Akio
 * @author Vinícius
 */
public class Paint {

	private Semaphore semaphore = new Semaphore(0);
	private BufferedImage canvasImage;
	private JPanel gui;
	private Color color = Color.WHITE;

	private BufferedImage colorSample = new BufferedImage(16, 16,
			BufferedImage.TYPE_INT_RGB);
	private JLabel imageLabel;

	private Stroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 1.7f);
	private RenderingHints renderingHints;

	private static List<Pair<Integer, Integer>> pontos = null;

	private int Xsize = 1000;
	private int Ysize = 300;

	private double x[];

	/**
	 * gets the canvas and the hud
	 * @param semaphore
	 * 					for communication thread
	 * @return
	 * 			canvas window
	 */
	public JComponent getGui(Semaphore semaphore) {

		pontos = new ArrayList<Pair<Integer, Integer>>();

		for (int i = 0; i < Xsize; i++) {
			pontos.add(new Pair<Integer, Integer>(i, 0));
		}

		if (gui == null) {
			Map<Key, Object> hintsMap = new HashMap<RenderingHints.Key, Object>();
			hintsMap.put(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			hintsMap.put(RenderingHints.KEY_DITHERING,
					RenderingHints.VALUE_DITHER_ENABLE);
			hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			renderingHints = new RenderingHints(hintsMap);

			setImage(new BufferedImage(Xsize, Ysize, BufferedImage.TYPE_INT_RGB));
			gui = new JPanel(new BorderLayout(4, 4));
			gui.setBorder(new EmptyBorder(5, 3, 5, 3));

			JPanel imageView = new JPanel(new GridBagLayout());
			imageView.setPreferredSize(new Dimension(Xsize + 200, Ysize + 200));
			imageLabel = new JLabel(new ImageIcon(canvasImage));
			JScrollPane imageScroll = new JScrollPane(imageView);
			imageView.add(imageLabel);
			imageLabel.addMouseMotionListener(new ImageMouseMotionListener());
			gui.add(imageScroll, BorderLayout.CENTER);

			JToolBar tb = new JToolBar();
			tb.setFloatable(false);

			setColor(Color.WHITE);

			JButton clearButton = new JButton("Limpar");
			tb.add(clearButton);
			clearButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					clear(canvasImage);

				}
			});

			JButton finish = new JButton("Salvar");
			tb.add(finish);
			finish.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					clear(canvasImage);

					semaphore.release();

				}

			});

			gui.add(tb, BorderLayout.PAGE_START);

			clear(canvasImage);
		}

		return gui;
	}

	/**
	 * generates the list of samples made from the draw
	 * @return
	 * 			the list with the samples
	 */
	public List<Pair<Integer, Double>> generatePointsList() {

		List<Pair<Integer, Double>> points = new ArrayList<Pair<Integer, Double>>();

		for (int i = 0; i < 1000; i++) {

			points.add(new Pair<Integer, Double>(i, ((double) pontos.get(i)
					.getSecond() * (-1) + (Ysize / 2))
					/ (double) (Ysize / 2)));
		}

		System.out.println("Size: " + points.size());

		return points;
	}
	
	/**
	 * clear canvas
	 * @param bi
	 */
	public void clear(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(color);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.dispose();
		imageLabel.repaint();

	}

	/**
	 * sets the canvas parameters
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.drawImage(image, 0, 0, gui);
		g.dispose();

		if (this.imageLabel != null) {
			imageLabel.setIcon(new ImageIcon(canvasImage));
			this.imageLabel.repaint();
		}
		if (gui != null) {
			gui.invalidate();
		}
	}

	/**
	 * sets the brush color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		clear(colorSample);
	}

	/**
	 * open de canvas window
	 * @param f
	 * 			canvas object
	 * @return
	 * 			null
	 */
	public double[] open(JFrame f) {

		f.setContentPane(new Paint().getGui(semaphore));
		f.setVisible(true);

		try {
			System.out.println("Semaforo");
			semaphore.acquire();

			List<Pair<Integer, Double>> l = generatePointsList();

			double x[] = new double[l.size()];
			for (Pair<Integer, Double> pair : l) {
				x[pair.getFirst()] = pair.getSecond();
			}
			System.out.println("Liberando semaforo");
			return x;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(10, 10, 1200, 1200);

		Paint p = new Paint();
		double x[] = p.open(f);
		System.out.println("size: " + x.length);
	}

	
	/**
	 * draw into canvas
	 * @param point
	 */
	public void draw(Point point) {
		Graphics2D g = this.canvasImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(Color.BLACK);
		g.setStroke(stroke);
		int n = 0;
		g.drawLine(point.x, point.y, point.x + n, point.y + n);
		g.dispose();
		this.imageLabel.repaint();

		if (point.x >= 0 && point.x < Xsize && point.y >= 0) {
			pontos.get(point.x).setSecond(point.y);
		}
	}

	/**
	 *
	 * gets mouse coordinates 
	 * @author Carolina Arenas Okawa
	 * @author Eric
	 * @author Fernando Akio
	 * @author Vinícius
	 */
	class ImageMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			draw(e.getPoint());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

	}
}