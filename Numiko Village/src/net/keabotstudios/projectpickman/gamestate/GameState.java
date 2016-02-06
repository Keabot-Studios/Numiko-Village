package net.keabotstudios.projectpickman.gamestate;

import net.keabotstudios.projectpickman.input.Input;

public abstract class GameState {

	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void init();

	public abstract void update();

	public abstract void render();

	public abstract void handleInput(Input input);

}
