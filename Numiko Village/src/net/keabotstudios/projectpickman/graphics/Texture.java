package net.keabotstudios.projectpickman.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.keabotstudios.projectpickman.util.BufferUtils;

public class Texture {
	
	private int width, height;
	private int texture;
	
	public Texture(String path) {
		texture = load(path);
	}

	private int load(String path) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			return load(image);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int load(BufferedImage image) {
		int[] pixels = null;
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[] data = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff) >> 0;
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(pixels));
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return result;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
