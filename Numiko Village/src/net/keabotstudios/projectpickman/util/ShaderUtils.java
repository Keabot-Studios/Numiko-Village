package net.keabotstudios.projectpickman.util;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class ShaderUtils {
	
	public static int load(String vertPath, String fragPath) {
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertId = glCreateShader(GL_VERTEX_SHADER);
		int fragId = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertId, vert);
		glShaderSource(fragId, frag);
		glCompileShader(vertId);
		glCompileShader(fragId);
		if(glGetShaderi(vertId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertId, 2048));
		}
		if(glGetShaderi(fragId, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragId, 2048));
		}
		glAttachShader(program, vertId);
		glAttachShader(program, fragId);
		glLinkProgram(program);
		glValidateProgram(program);
		return program;
	}
}
