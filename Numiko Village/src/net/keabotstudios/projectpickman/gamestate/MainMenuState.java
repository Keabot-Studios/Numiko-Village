package net.keabotstudios.projectpickman.gamestate;

import net.keabotstudios.projectpickman.input.Input;
import net.keabotstudios.projectpickman.map.Background;
import net.keabotstudios.projectpickman.map.TileMap;
import net.keabotstudios.projectpickman.map.TileSet;

public class MainMenuState extends GameState {

	private Background bg;
	private TileMap map;

	public MainMenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		bg = new Background("/textures/menubg1.png", 1f);
		map = new TileMap(new TileSet("/tilesets/testTileset.png", "/tilesets/testTileset.tsc"), "/maps/testMap.tmp", 3f);
	}

	public void update() {

	}

	public void render() {
		bg.render();
		map.render();
	}

	public void handleInput(Input input) {

	}

}
