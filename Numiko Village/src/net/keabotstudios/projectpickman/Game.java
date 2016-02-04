package net.keabotstudios.projectpickman;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import net.keabotstudios.projectpickman.util.ShaderUtils;

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
		
		int shader = ShaderUtils.load("res/shaders/shader.vert", "res/shaders/shader.frag");
		glUseProgram(shader);
		
		while(running) {
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
