package net.keabotstudios.projectpickman.map;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.graphics.Texture;
import net.keabotstudios.projectpickman.graphics.TileSet;
import net.keabotstudios.projectpickman.graphics.VertexArray;

public class TileMap {

	private double x;
	private double y;

	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	private double tween;

	// tileset
	private TileSet tileSet;

	// map
	private int[][] map;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// drawing
	private BufferedImage mapImage;
	private Texture mapTexture;
	private VertexArray verticies;
	private int rowOffset;
	private int colOffset;

	public TileMap(TileSet tileSet, String mapFile) {
		this.tileSet = tileSet;
		this.tween = 0.07;
		loadMap(mapFile);
		
		float[] verticies = {
				0.0f
		};
	}

	public void loadMap(String mapFile) {
		try {
			InputStream in = getClass().getResourceAsStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * References.TILE_SIZE;
			height = numRows * References.TILE_SIZE;

			xmin = References.WIDTH - width;
			xmax = 0;
			ymin = References.HEIGHT - height;
			ymax = 0;

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setTween(double d) {
		tween = d;
	}

	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = References.WIDTH - i1;
		ymin = References.WIDTH - i2;
		xmax = i3;
		ymax = i4;
	}

	public void setPosition(double x, double y) {

		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();

		colOffset = (int) -this.x / References.TILE_SIZE;
		rowOffset = (int) -this.y / References.TILE_SIZE;

	}

	public void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}
	
	public void render() {
		
	}

}
