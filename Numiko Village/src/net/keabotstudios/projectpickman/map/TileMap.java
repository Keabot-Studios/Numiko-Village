package net.keabotstudios.projectpickman.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.graphics.Shader;
import net.keabotstudios.projectpickman.graphics.Texture;
import net.keabotstudios.projectpickman.graphics.VertexArray;
import net.keabotstudios.projectpickman.map.Tile.TileType;
import net.keabotstudios.projectpickman.math.Matrix4f;
import net.keabotstudios.projectpickman.math.Vector3f;

public class TileMap {

	private Vector3f position;

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
	private VertexArray vertexArray;
	private int rowOffset;
	private int colOffset;

	public TileMap(TileSet tileSet, String mapFile, float renderScale) {
		this.tileSet = tileSet;
		this.tween = 0.07;
		this.position = new Vector3f();
		loadMap(mapFile, renderScale);
	}

	public void loadMap(String mapFile, float renderScale) {
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

			mapImage = new BufferedImage(References.TILE_SIZE * numCols, References.TILE_SIZE * numRows, BufferedImage.TYPE_INT_ARGB);
			Graphics mg = mapImage.getGraphics();
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					mg.drawImage(tileSet.getTile(map[row][col]).getTexture(), col * References.TILE_SIZE, row * References.TILE_SIZE, References.TILE_SIZE, References.TILE_SIZE, null);
				}
			}
			mg.dispose();

			float iWidth = width * renderScale;
			float iHeight = height * renderScale;
			float[] verticies = new float[] { -iWidth / 2.0f, -iHeight / 2.0f, 1.0f, -iWidth / 2.0f, iHeight / 2.0f, 1.0f, iWidth / 2.0f, iHeight / 2.0f, 1.0f, iWidth / 2.0f, -iHeight / 2.0f, 1.0f };
			byte[] indices = { 0, 1, 2, 2, 3, 0 };
			float[] tcs = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };

			vertexArray = new VertexArray(verticies, indices, tcs);
			mapTexture = new Texture(mapImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public double getx() {
		return position.x;
	}

	public double gety() {
		return position.y;
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

	public TileType getType(int row, int col) {
		int rc = map[row][col];
		return tileSet.getTile(rc).getType();
	}

	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = References.WIDTH - i1;
		ymin = References.WIDTH - i2;
		xmax = i3;
		ymax = i4;
	}

	public void setPosition(double x, double y) {
		this.position.x += (x - this.position.x) * tween;
		this.position.y += (y - this.position.y) * tween;

		fixBounds();

		colOffset = (int) -this.position.x / References.TILE_SIZE;
		rowOffset = (int) -this.position.y / References.TILE_SIZE;

	}

	public void fixBounds() {
		if (this.position.x < xmin)
			this.position.x = xmin;
		if (this.position.y < ymin)
			this.position.y = ymin;
		if (this.position.x > xmax)
			this.position.x = xmax;
		if (this.position.y > ymax)
			this.position.y = ymax;
	}

	public void render() {
		Shader.TILEMAP.enable();
		Shader.TILEMAP.setUniformMat4f("ml_matrix", Matrix4f.translate(position));
		mapTexture.bind();
		vertexArray.render();
		Shader.TILEMAP.disable();
	}

}
