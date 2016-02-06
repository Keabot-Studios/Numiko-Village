package net.keabotstudios.projectpickman.entity;

import java.awt.Rectangle;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.graphics.Animation;
import net.keabotstudios.projectpickman.map.Tile;
import net.keabotstudios.projectpickman.map.Tile.TileType;
import net.keabotstudios.projectpickman.map.TileMap;

public abstract class GameObject {

	// tile stuff
	protected TileMap tileMap;
	protected double xmap;
	protected double ymap;

	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	// dimensions
	protected int width;
	protected int height;

	// collision box
	protected int cwidth;
	protected int cheight;

	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;

	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;

	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	// constructor
	public GameObject(TileMap tm) {
		tileMap = tm;
		animation = new Animation();
		facingRight = true;
	}

	public boolean intersects(GameObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}

	public boolean contains(GameObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}

	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x - cwidth / 2, (int) y - cheight / 2, cwidth, cheight);
	}

	public void calculateCorners(double x, double y) {
		int leftTile = (int) (x - cwidth / 2) / References.TILE_SIZE;
		int rightTile = (int) (x + cwidth / 2 - 1) / References.TILE_SIZE;
		int topTile = (int) (y - cheight / 2) / References.TILE_SIZE;
		int bottomTile = (int) (y + cheight / 2 - 1) / References.TILE_SIZE;
		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		TileType tl = tileMap.getType(topTile, leftTile);
		TileType tr = tileMap.getType(topTile, rightTile);
		TileType bl = tileMap.getType(bottomTile, leftTile);
		TileType br = tileMap.getType(bottomTile, rightTile);
		topLeft = tl == TileType.SOLID;
		topRight = tr == TileType.SOLID;
		bottomLeft = bl == TileType.SOLID || bl == TileType.PLATFORM;
		bottomRight = br == TileType.SOLID || br == TileType.PLATFORM;
	}

	public void checkTileMapCollision() {

		currCol = (int) x / References.TILE_SIZE;
		currRow = (int) y / References.TILE_SIZE;

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		calculateCorners(x, ydest);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				ytemp = currRow * References.TILE_SIZE + cheight / 2;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * References.TILE_SIZE - cheight / 2;
			} else {
				ytemp += dy;
			}
		}

		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * References.TILE_SIZE + cwidth / 2;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * References.TILE_SIZE - cwidth / 2;
			} else {
				xtemp += dx;
			}
		}

		if (!falling) {
			calculateCorners(x, ydest + 1);
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}

	}

	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCWidth() {
		return cwidth;
	}

	public int getCHeight() {
		return cheight;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}

	public abstract void render();

}
