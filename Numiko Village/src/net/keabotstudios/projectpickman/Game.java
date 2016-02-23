package net.keabotstudios.projectpickman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.keabotstudios.projectpickman.gamestate.GameStateManager;
import net.keabotstudios.projectpickman.gamestate.TestState;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.loading.Textures;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	private boolean running = false;
	private Thread thread;
	private int fps, ups;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	private Input input;
	
	public Game() {
		Dimension size = new Dimension(References.WIDTH * References.SCALE, References.HEIGHT * References.SCALE);
		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		
		frame = new JFrame(References.NAME);
		frame.add(this);
		frame.setSize(size);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImages(Arrays.asList(Textures.icons));
		frame.setVisible(true);
	}

	public void start() {
		running = true;
		thread = new Thread(this, References.NAME + " - Main");
		thread.start();
	}

	private void init() {
		image = new BufferedImage(References.WIDTH, References.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		input = new Input(this);
		Textures.init();
		Textures.loadTextures();

		gsm = new GameStateManager(input);
		gsm.push(new TestState(gsm));
	}

	@Override
	public void run() {
		init();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / References.MAX_UPS;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ups = updates;
				fps = frames;
				updates = 0;
				frames = 0;
			}
		}
	}

	private void update() {
		gsm.update();
		input.update();
	}

	private void render() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		gsm.render(g);
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}

	public static void main(String args[]) {
		new Game().start();
	}
	
	public JFrame getFrame() {
		return frame;
	}

}
