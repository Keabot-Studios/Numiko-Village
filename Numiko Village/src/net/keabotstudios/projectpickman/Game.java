package net.keabotstudios.projectpickman;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import net.keabotstudios.projectpickman.gamestate.GameStateManager;
import net.keabotstudios.projectpickman.gamestate.MainMenuState;
import net.keabotstudios.projectpickman.graphics.Shader;
import net.keabotstudios.projectpickman.input.Input;

public class Game implements Runnable {

	private boolean running = false;
	private Thread thread;
	private int fps, ups;

	private long window;
	private GameStateManager gsm;
	private Input input;

	public void start() {
		running = true;
		thread = new Thread(this, References.NAME + " - Main");
		thread.start();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("Could not initalize GLFW.");
			System.exit(1);
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		window = glfwCreateWindow(References.WIDTH, References.HEIGHT, References.NAME, NULL, NULL);
		if (window == NULL) {
			return;
		}

		input = new Input(window);

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - References.WIDTH) / 2, (vidmode.height() - References.HEIGHT) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE0);
		System.out.println("OpenGL: " + glGetString(GL_VERSION) + ", GPU Vendor: " + glGetString(GL_VENDOR));
		Shader.loadAll();

		gsm = new GameStateManager(input);
		gsm.push(new MainMenuState(gsm));
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

				System.out.println("UPS: " + ups + ", FPS: " + fps);
			}

			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
	}

	private void update() {
		glfwPollEvents();
		input.update();
		gsm.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		gsm.render();
		glfwSwapBuffers(window);
	}

	public static void main(String args[]) {
		new Game().start();
	}

}
