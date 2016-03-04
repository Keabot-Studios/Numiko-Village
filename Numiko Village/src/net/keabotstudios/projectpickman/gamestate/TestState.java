package net.keabotstudios.projectpickman.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.font.Font;
import net.keabotstudios.projectpickman.graphics.hud.HudBar;
import net.keabotstudios.projectpickman.graphics.hud.HudButtonPrompt;
import net.keabotstudios.projectpickman.graphics.hud.HudSet;
import net.keabotstudios.projectpickman.graphics.hud.HudText;
import net.keabotstudios.projectpickman.graphics.hud.PlayerHud;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.Path;
import net.keabotstudios.projectpickman.io.Input.InputAxis;
import net.keabotstudios.projectpickman.map.Background;
import net.keabotstudios.projectpickman.map.TileMap;
import net.keabotstudios.projectpickman.map.TileSet;

public class TestState extends GameState {

	private TileMap map;
	private Player player;
	private PlayerHud playerHud;
	private Background bg;

	public TestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		map = new TileMap(new TileSet(Path.TILESETS + "/testTileset.png", Path.TILESETS + "/testTileset.tsc"), Path.MAPS + "/testMap.tmp");
		player = new Player(map, "testy");
		player.setPosition(100, 100);
		playerHud = new PlayerHud(player);
		bg = new Background("grassBg");
	}

	public void update() {
		player.update();
		map.centerOn(player);
		playerHud.update();

		bg.setOffset((int) (map.getX() / 2), 0);
	}

	public void render(Graphics2D g) {
		bg.render(g);
		map.render(g);
		player.render(g);

		playerHud.render(g);
	}

	public void handleInput(Input input) {
		player.handleInput(input);
		if (input.getInput(InputAxis.F1)) {
			player.setPosition(100, 100);
		}
	}

}
