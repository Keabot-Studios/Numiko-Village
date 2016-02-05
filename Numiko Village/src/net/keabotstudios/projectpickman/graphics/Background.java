package net.keabotstudios.projectpickman.graphics;

public class Background {

	private VertexArray background;
	private Texture texture;

	public Background(String imagePath) {
		float[] verticies = { -10.0f, -10.0f * 9.0f / 16.0f, 0.0f, -10.0f, 10.0f * 9.0f / 16.0f, 0.0f, 10.0f, 10.0f * 9.0f / 16.0f, 0.0f, 10.0f, -10.0f * 9.0f / 16.0f, 0.0f };
		byte[] indicies = { 0, 1, 2, 2, 3, 0 };
		float[] tcs = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };

		background = new VertexArray(verticies, indicies, tcs);
		texture = new Texture(imagePath);
	}

	public void render() {
		texture.bind();
		Shader.BG.enable();
		background.render();
		Shader.BG.disable();
		texture.unbind();
	}

}
