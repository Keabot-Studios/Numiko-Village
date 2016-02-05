package net.keabotstudios.projectpickman.gamestate;

import net.keabotstudios.projectpickman.graphics.Background;
import net.keabotstudios.projectpickman.input.Input;

public class MainMenuState extends GameState {
	
	private Background bg;

	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		bg = new Background("/test_bg.png");
	}

	public void update() {

	}

	public void render() {
		bg.render();
	}

	public void handleInput(Input input) {

	}

}
