package net.keabotstudios.projectpickman;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import net.keabotstudios.projectpickman.input.Input;
import net.keabotstudios.projectpickman.input.Input.Keyboard;

public class Game implements Runnable {
	
	private boolean running = false;
	private Thread thread;
	
	private long window;
	
	private Input input;
	
	public void start() {
		running = true;
		thread = new Thread(this, References.NAME + " - Main");
		thread.start();
	}
	
	private void init() {
		if(glfwInit() != GL_TRUE) {
			System.err.println("Could not initalize GLFW.");
			System.exit(1);
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(References.WIDTH, References.HEIGHT, References.NAME, NULL, NULL);
		if(window == NULL) {
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
		System.out.println("OpenGL: " + glGetString(GL_VERSION) + ", GPU Vendor: " + glGetString(GL_VENDOR));
	}

	@Override
	public void run() {
		init();

		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window) == GL_TRUE) running = false;
		}
	}
	
	private void update() {
		glfwPollEvents();
		input.update();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}

	public static void main(String args[]) {
		new Game().start();
	}
	
}
