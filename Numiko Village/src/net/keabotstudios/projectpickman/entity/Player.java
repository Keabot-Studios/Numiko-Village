package net.keabotstudios.projectpickman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import net.keabotstudios.projectpickman.graphics.Animation;
import net.keabotstudios.projectpickman.inventory.item.Item;
import net.keabotstudios.projectpickman.inventory.item.Items;
import net.keabotstudios.projectpickman.inventory.item.Weapon;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.Input.InputAxis;
import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.map.TileMap;
import net.keabotstudios.projectpickman.util.MathUtils;

public class Player extends GameObject {

	// stats
	private int health;
	private int maxHealth;
	private int money;
	private int maxMoney;

	// actions
	private boolean dashing;
	private boolean dead;
	private boolean knockback;
	private boolean flinching;
	private long flinchTimer;

	// animation
	private HashMap<Action, BufferedImage[]> sprites;
	private Action currentAction;

	private ArrayList<Item> items;
	private ArrayList<Weapon> weapons;
	private int selectedWeapon;

	public enum Action {
		IDLE(20, 32, 32), WALKING(5, 32, 32), JUMPING(-1, 32, 32), FALLING(-1, 32, 32), SWINGINGKNIFE(3, 40, 32);

		private int delay, spriteWidth, spriteHeight;

		private Action(int delay, int spriteWidth, int spriteHeight) {
			this.delay = delay;
			this.spriteWidth = spriteWidth;
			this.spriteHeight = spriteHeight;
		}

		public int getDelay() {
			return delay;
		}

		public int getSpriteWidth() {
			return spriteWidth;
		}

		public int getSpriteHeight() {
			return spriteHeight;
		}
	}

	public Player(TileMap tm, String textureName, boolean limitedEnergy) {
		super(tm);

		width = 32;
		height = 32;
		cwidth = 23;
		cheight = 29;

		moveSpeed = 0.5;
		maxSpeed = 1.8;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.5;
		stopJumpSpeed = 0.3;

		facingRight = true;

		health = maxHealth = 25;
		money = 0;
		maxMoney = 999;

		items = new ArrayList<Item>();
		weapons = new ArrayList<Weapon>();
		weapons.add((Weapon) Items.knife);
		selectedWeapon = 0;

		sprites = new HashMap<Action, BufferedImage[]>();
		try {
			BufferedImage spriteSheet = Textures.getTexture(textureName);

			int totalY = 0;
			for (Action a : Action.values()) {
				int numberOfFrames = spriteSheet.getWidth() / a.getSpriteWidth();
				BufferedImage[] bi = new BufferedImage[numberOfFrames];
				for (int i = 0; i < numberOfFrames; i++) {
					bi[i] = spriteSheet.getSubimage(i * a.getSpriteWidth(), totalY, a.getSpriteWidth(), a.getSpriteHeight());
				}
				totalY += a.getSpriteHeight();
				sprites.put(a, bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		setAction(Action.IDLE);
	}

	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = Action.IDLE;
		stop();
	}

	public void stop() {
		left = right = up = down = flinching = jumping = false;
	}

	private void setDashing(boolean b) {
		if (dy != 0)
			return;
		dashing = b;
	}

	public void setAction(Action action) {
		currentAction = action;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(currentAction.getDelay());
		width = currentAction.getSpriteWidth();
		height = currentAction.getSpriteHeight();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = MathUtils.clamp(health, 0, maxHealth);
	}

	public int getMoney() {
		return money;
	}

	public int getMaxMoney() {
		return maxMoney;
	}

	public void setMoney(int money) {
		this.money = MathUtils.clamp(money, 0, maxMoney);
	}
	
	public void setMaxMoney(int maxMoney) {
		this.maxMoney = maxMoney;
		this.money = MathUtils.clamp(money, 0, maxMoney);
	}

	public void addMoney(int amount) {
		this.money = MathUtils.clamp(money += amount, 0, maxMoney);
	}

	public void removeMoney(int amount) {
		this.money = MathUtils.clamp(money -= amount, 0, maxMoney);
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		this.health = MathUtils.clamp(health, 0, maxHealth);
	}

	public int getSelectedWeapon() {
		return selectedWeapon;
	}

	public void handleInput(Input input) {
		setUp(input.getInput(InputAxis.UP));
		setDown(input.getInput(InputAxis.DOWN));
		setLeft(input.getInput(InputAxis.LEFT));
		setRight(input.getInput(InputAxis.RIGHT));
		setJumping(input.getInput(InputAxis.ACTION1));
		setDashing(input.getInput(InputAxis.ACTION2));
		if(input.getInputTapped(InputAxis.ACTION3)) changeWeapon();
	}

	public void changeWeapon() {

	}

	int nrgRegenTimer = 0;

	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (dy > 0) {
			if (currentAction != Action.FALLING) {
				setAction(Action.FALLING);
			}
		} else if (dy < 0) {
			if (currentAction != Action.JUMPING) {
				setAction(Action.JUMPING);
			}
		} else if (left || right) {
			if (currentAction != Action.WALKING) {
				setAction(Action.WALKING);
			}
		} else {
			if (currentAction != Action.IDLE) {
				setAction(Action.IDLE);
			}
		}
		animation.update();

		if (right)
			facingRight = true;
		if (left)
			facingRight = false;
	}

	private void getNextPosition() {
		if (knockback) {
			dy += fallSpeed * 2;
			if (!falling)
				knockback = false;
			return;
		}

		double maxSpeed = this.maxSpeed;
		if (dashing)
			maxSpeed *= 1.75;

		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}

		if (falling) {
			dy += fallSpeed;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}

	}

	public void render(Graphics2D g) {
		setMapPosition();
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0)
				return;
		}

		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), width, height, null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - height / 2 + width), (int) (y + ymap - height / 2), -width, height, null);
		}
	}

}
