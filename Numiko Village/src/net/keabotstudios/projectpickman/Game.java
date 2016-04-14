package net.keabotstudios.projectpickman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import net.keabotstudios.projectpickman.gamestate.GameStateManager;
import net.keabotstudios.projectpickman.gamestate.TestState;
import net.keabotstudios.projectpickman.graphics.hud.HudText;
import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.inventory.item.Items;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.console.Console;
import net.keabotstudios.projectpickman.io.console.Logger.LogLevel;
import net.keabotstudios.projectpickman.io.serial.SerialArray;
import net.keabotstudios.projectpickman.io.serial.SerialDatabase;
import net.keabotstudios.projectpickman.io.serial.SerialField;
import net.keabotstudios.projectpickman.io.serial.SerialObject;
import net.keabotstudios.projectpickman.io.serial.Serialization;
import net.keabotstudios.projectpickman.loading.Textures;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private static JFrame frame;

	private boolean running = false;
	private Thread thread;
	private int fps, ups;

	private BufferedImage image;
	private Graphics2D g;

	private GameStateManager gsm;
	private Input input;
	
	private HudText fpsCounter, upsCounter;

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
		Font.loadFonts();
		Items.loadItems();
		
		fpsCounter = new HudText(0, 0, "FPS: 0", Font.MAIN, Color.GREEN, 1);
		upsCounter = new HudText(0, 15, "UPS: 0", Font.MAIN, Color.GREEN, 1);

		gsm = new GameStateManager(input);
		gsm.push(new TestState(gsm));

		Random rand = new Random();
		int[] arrayData = new int[100];
		for (int i = 0; i < arrayData.length; i++) {
			arrayData[i] = rand.nextInt();
		}

		SerialDatabase database = new SerialDatabase("Database");

		SerialArray array = SerialArray.create("RandomNumbers", arrayData); // Correct Size: 412
		SerialField xField = SerialField.create("x", 10);
		SerialField yField = SerialField.create("y", 10);

		SerialObject object = new SerialObject("Entity");
		object.addArray(array);
		object.addField(xField);
		object.addField(yField);

		database.addObject(object);

		byte[] stream = new byte[object.getSize()];
		object.writeBytes(stream, 0);
		Serialization.saveToFile("test.nvd", stream);
	}

	public void run() {
		try {
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
					upsCounter.setText("UPS: " + ups);
					fpsCounter.setText("FPS: " + fps);
					updates = 0;
					frames = 0;
				}
			}
		} catch (Exception e) {
			Game.handleException(e);
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
		
		if(GameInfo.debugMode) {
			upsCounter.render(g, 5, 25);
			fpsCounter.render(g, 5, 25);
		}
	}

	public static void main(String args[]) {
		if (args.length == 1 && args[0].equals("debug")) {
			GameInfo.debugMode = true;
			GameInfo.log_level = LogLevel.INFO;
			new Console();
		} else {
			GameInfo.log_level = LogLevel.FATAL;
		}
		new Game().start();
	}

	public JFrame getFrame() {
		return frame;
	}

	public static void handleException(Exception ex) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ex.printStackTrace();
			String exMessage = "";
			exMessage += ex.getClass().toString() + ": " + ex.getMessage() + "\n";
			for (StackTraceElement e : ex.getStackTrace()) {
				exMessage += "    at " + e.getClassName() + "." + e.getMethodName() + "(" + Class.forName(e.getClassName()).getSimpleName() + ".java:" + e.getLineNumber() + ")\n";
			}
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout());
			JTextArea message = new JTextArea("The game crashed!\nCopy this message, and send it to the devs:");
			message.setEditable(false);
			message.setOpaque(false);
			pane.add(message, BorderLayout.NORTH);
			JTextArea exceptionMessage = new JTextArea(exMessage);
			exceptionMessage.setEditable(false);
			exceptionMessage.setOpaque(false);
			exceptionMessage.setForeground(Color.RED);
			pane.add(exceptionMessage, BorderLayout.CENTER);
			JOptionPane.showConfirmDialog(frame, pane, References.NAME, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (Exception e) {
		}
	}

}
