package net.keabotstudios.projectpickman.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.graphics.Animation;
import net.keabotstudios.projectpickman.io.Input;
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
	protected int leftTile;
	protected int rightTile;
	protected int topTile;
	protected int bottomTile;
	protected boolean topCollision;
	protected boolean leftCollision;
	protected boolean rightCollision;
	protected boolean bottomCollision;
	protected boolean bottomLedge;

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
	protected boolean ledgeFall;
	protected boolean ignoreCollision;

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

	public void calculateCollision(double x, double y) {
		topCollision = leftCollision = rightCollision = bottomCollision = bottomLedge = false;
		int xl = (int) (x - cwidth / 2);
		int xr = (int) (x + cwidth / 2 - 1);
		int yt = (int) (y - cheight / 2);
		int yb = (int) (y + cheight / 2 - 1);
		leftTile = xl / References.TILE_SIZE;
		rightTile = xr / References.TILE_SIZE;
		topTile = yt / References.TILE_SIZE;
		bottomTile = yb / References.TILE_SIZE;
		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			return;
		}
		for (int i = 0; i < rightTile - leftTile + 1; i++) {
			topCollision |= tileMap.getType(topTile, leftTile + i) == TileType.SOLID;
			bottomCollision |= tileMap.getType(bottomTile, leftTile + i) == TileType.SOLID;
			bottomLedge |= tileMap.getType(bottomTile, leftTile + i) == TileType.PLATFORM;
		}
		for (int i = 0; i < bottomTile - topTile + 1; i++) {
			leftCollision |= tileMap.getType(topTile + i, leftTile) == TileType.SOLID;
			rightCollision |= tileMap.getType(topTile + i, rightTile) == TileType.SOLID;
		}
	}

	public boolean checkTileMapCollision() {
		if (ignoreCollision)
			return false;
		currCol = (int) x / References.TILE_SIZE;
		currRow = (int) y / References.TILE_SIZE;

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		boolean collision = false;

		calculateCollision(x, ydest);
		if (dy < 0) {
			if (topCollision) {
				dy = 0;
				ytemp = (topTile + 1) * References.TILE_SIZE + cheight / 2;
				collision = true;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomCollision) {
				dy = 0;
				falling = false;
				ytemp = bottomTile * References.TILE_SIZE - cheight / 2;
				collision = true;
				ledgeFall = false;
			} else if (!ledgeFall && bottomLedge && (y + cheight / 2 - 1) - bottomTile * References.TILE_SIZE < dy) {
				dy = 0;
				falling = false;
				ytemp = bottomTile * References.TILE_SIZE - cheight / 2;
				collision = true;
			} else {
				ytemp += dy;
			}
			if (ledgeFall) {
				ledgeFall = tileMap.getType((int) (y - cheight / 2 + 1) / References.TILE_SIZE, (int) x / References.TILE_SIZE) != TileType.PLATFORM;
			}
		}

		calculateCollision(xdest, y);
		if (dx < 0) {
			if (leftCollision) {
				dx = 0;
				xtemp = (leftTile + 1) * References.TILE_SIZE + cwidth / 2;
				collision = true;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (rightCollision) {
				dx = 0;
				xtemp = rightTile * References.TILE_SIZE - cwidth / 2;
				collision = true;
			} else {
				xtemp += dx;
			}
		}

		if (!falling) {
			calculateCollision(x, ydest + 1);
			if (!bottomCollision && !bottomLedge) {
				falling = true;
			}
		}

		return collision;
	}

	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}
	
	public int getXOnScreen() {
		return (int) (x + xmap) - width / 2;
	}
	
	public int getYOnScreen() {
		return (int) (y + ymap) - height / 2;
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
		xmap = tileMap.getX();
		ymap = tileMap.getY();
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

	public boolean notOnScreen() {
		return x + xmap + width < 0 || x + xmap + width > References.WIDTH || y + ymap + height < 0 || y + ymap + height > References.HEIGHT;
	}

	public abstract void handleInput(Input input);

	public abstract void update();

	public abstract void render(Graphics2D g);

}
