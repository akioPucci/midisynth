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

public class Paint {

	private final Semaphore semaphore = new Semaphore(0);
	private BufferedImage canvasImage;
	private JPanel gui;
	private Color color = Color.WHITE;

	private BufferedImage colorSample = new BufferedImage(16, 16,
			BufferedImage.TYPE_INT_RGB);
	private JLabel imageLabel;

	private Stroke stroke = new BasicStroke(3, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 1.7f);
	private RenderingHints renderingHints;

	private List<Pair<Integer, Integer>> pontos;

	private int Xsize = 1000;
	private int Ysize = 300;

	private double x[];

	public JComponent getGui() {

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

					List<Pair<Integer, Double>> l = generatePointsList();

					x = new double[l.size()];
					for (Pair<Integer, Double> pair : l) {
						x[pair.getFirst()] = pair.getSecond();
					}
					System.out.println("Liberando semaforo");
					semaphore.release();

				}

			});

			gui.add(tb, BorderLayout.PAGE_START);

			clear(canvasImage);
		}

		return gui;
	}

	public List<Pair<Integer, Double>> generatePointsList() {

		List<Pair<Integer, Double>> points = new ArrayList<Pair<Integer, Double>>();

		int pos = 0;
		for (int i = 0; i < 16000; i += (16000 / Xsize)) {
			for (int j = 0; j < (16000 / Xsize); j++) {
				points.add(new Pair<Integer, Double>(i + j, ((double) pontos
						.get(pos).getSecond() * (-1) + (Ysize / 2))
						/ (double) (Ysize / 2)));
			}
			pos++;
		}
		System.out.println("Size: " + points.size());

		return points;
	}

	public void clear(BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(color);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.dispose();
		imageLabel.repaint();

	}

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

	public void setColor(Color color) {
		this.color = color;
		clear(colorSample);
	}

	public double[] open(JFrame f) {

		f.setContentPane(new Paint().getGui());
		f.setVisible(true);

		try {
			System.out.println("Semaforo");
			semaphore.acquire();
			System.out.println("done");
			return x;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(10, 10, 1200, 1200);

		Paint p = new Paint();
		double x[] = p.open(f);
		System.out.println("size: " + x.length);
	}

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