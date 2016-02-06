package net.keabotstudios.projectpickman.map;

import net.keabotstudios.projectpickman.graphics.Shader;
import net.keabotstudios.projectpickman.graphics.Texture;
import net.keabotstudios.projectpickman.graphics.VertexArray;

public class Background {

	private VertexArray background;
	private Texture texture;

	public Background(String imagePath, float scale) {
		texture = new Texture(imagePath);
		float width = texture.getWidth() * scale;
		float height = texture.getHeight() * scale;
		float[] verticies = { -width / 2.0f, -height / 2.0f, 0.0f, -width / 2.0f, height / 2.0f, 0.0f, width / 2.0f, height / 2.0f, 0.0f, width / 2.0f, -height / 2.0f, 0.0f };
		byte[] indices = { 0, 1, 2, 2, 3, 0 };
		float[] tcs = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };

		background = new VertexArray(verticies, indices, tcs);
	}

	public void render() {
		texture.bind();
		Shader.BG.enable();
		background.render();
		Shader.BG.disable();
		texture.unbind();
	}

}
