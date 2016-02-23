package net.keabotstudios.projectpickman.map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.entity.GameObject;
import net.keabotstudios.projectpickman.map.Tile.TileType;

public class TileMap {

	// position
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
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(TileSet tileSet, String mapFile) {
		this.tileSet = tileSet;
		this.numRowsToDraw = (int) (References.HEIGHT / References.TILE_SIZE + 2f);
		this.numColsToDraw = (int) (References.WIDTH / References.TILE_SIZE + 2f);
		this.tween = 0.07;
		loadMap(mapFile);
	}

	private void loadMap(String mapFile) {
		try {
			InputStream in = getClass().getResourceAsStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = (int) (numCols * References.TILE_SIZE);
			height = (int) (numRows * References.TILE_SIZE);

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
	
	public double getX() {
		return x;
	}

	public double getY() {
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

	public Tile getTile(int row, int col) {
		int rc = map[row][col];
		if (rc == -1)
			return new Tile(TileType.NORMAL, null);
		return tileSet.getTile(rc);
	}

	public TileType getType(int row, int col) {
		return getTile(row, col).getType();
	}

	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = References.WIDTH - i1;
		ymin = References.HEIGHT - i2;
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

	public void render(Graphics2D g) {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if (row >= numRows)
				break;

			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

				if (col >= numCols)
					break;
				int tileID = map[row][col];
				if (tileID < 0 || tileID >= tileSet.getAmountOfTiles())
					continue;
				g.drawImage(tileSet.getTile(tileID).getTexture(), (int) (x + col * References.TILE_SIZE), (int) (y + row * References.TILE_SIZE), References.TILE_SIZE, References.TILE_SIZE, null);
			}
		}
	}

	public void centerOn(GameObject o) {
		setPosition(References.WIDTH / 2 - o.getx(), References.HEIGHT / 2 - o.gety());
	}

}
