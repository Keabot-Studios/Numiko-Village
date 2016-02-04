package net.keabotstudios.projectpickman.graphics;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;

import net.keabotstudios.projectpickman.math.Vector3f;
import net.keabotstudios.projectpickman.util.ShaderUtils;

public class Shader {
	
	private int ID;
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll() {
		
	}
	
	public int getUniform(String name) {
		return glGetUniformLocation(ID, name);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void enable() {
		glUseProgram(ID);
	}
	
	public void disable() {
		glUseProgram(0);
	}

}
