package net.keabotstudios.projectpickman.gamestate;

import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.io.Input;

public abstract class GameState {

	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void init();

	public abstract void update();

	public abstract void render(Graphics2D g);

	public abstract void handleInput(Input input);

}
