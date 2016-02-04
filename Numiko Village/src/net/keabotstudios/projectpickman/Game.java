package net.keabotstudios.projectpickman;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import net.keabotstudios.projectpickman.graphics.Shader;
import net.keabotstudios.projectpickman.math.Vector3f;

public class Game implements Runnable {
	
	private boolean running = false;
	private Thread thread;
	
	public void start() {
		running = true;
		thread = new Thread(this, References.NAME + " - Display");
		thread.start();
	}
	
	private void init() {
		String version = glGetString(GL_VERSION);
		System.out.println("OpenGL version:" + version);
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(References.WIDTH, References.HEIGHT));
			Display.setTitle(References.NAME);
			ContextAttribs context = new ContextAttribs(3, 3);
			if(System.getProperty("os.name").contains("Mac")) context = new ContextAttribs(3, 2);
			Display.create(new PixelFormat(), context.withProfileCore(true));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		init();
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		Shader shader = new Shader("res/shaders/shader.vert", "res/shaders/shader.frag");
		shader.enable();
		Random r = new Random();
		while(running) {
			shader.setUniform3f("col", new Vector3f(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			render();
			Display.update();
			if(Display.isCloseRequested()) running = false;
		}
		Display.destroy();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	public static void main(String args[]) {
		new Game().start();
	}
	
}
