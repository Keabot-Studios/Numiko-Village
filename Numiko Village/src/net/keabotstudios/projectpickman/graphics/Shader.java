package net.keabotstudios.projectpickman.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import net.keabotstudios.projectpickman.math.Matrix4f;
import net.keabotstudios.projectpickman.math.Vector3f;
import net.keabotstudios.projectpickman.util.ShaderUtils;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader TILEMAP;
	public static Shader BG;
	
	private boolean enabled = false;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll() {
		TILEMAP = new Shader("res/shaders/tilemap/shader.vert", "res/shaders/tilemap/shader.frag");
		BG = new Shader("res/shaders/bg/shader.vert", "res/shaders/bg/shader.frag");
		BG.setUniformMat4f("pr_matrix", Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f));
		BG.setUniform1f("tex", 0);
		BG.disable();
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		int result = glGetUniformLocation(ID, name);
		if(result == -1) System.err.println("Could not find uniform variable: '" + name + "'!");
		else locationCache.put(name, result);
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		if(!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		if(!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), true, matrix.toFloatBuffer());
	}
	
	public void enable() {
		enabled = true;
		glUseProgram(ID);
	}
	
	public void disable() {
		enabled = true;
		glUseProgram(0);
	}

}
