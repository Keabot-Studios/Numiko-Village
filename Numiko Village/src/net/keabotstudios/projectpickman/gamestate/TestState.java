package net.keabotstudios.projectpickman.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.hud.HudBar;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.Path;
import net.keabotstudios.projectpickman.map.Background;
import net.keabotstudios.projectpickman.map.TileMap;
import net.keabotstudios.projectpickman.map.TileSet;

public class TestState extends GameState {

	private TileMap map;
	private Player player;
	private HudBar hpBar;
	private HudBar nrgBar;
	private Background bg;

	public TestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		map = new TileMap(new TileSet(Path.TILESETS + "/testTileset.png", Path.TILESETS + "/testTileset.tsc"), Path.MAPS + "/testMap.tmp");
		player = new Player(map, "testy");
		player.setPosition(100, 100);
		hpBar = new HudBar(10, 10, 75, player.getMaxHealth(), player.getHealth(), Color.RED.brighter());
		nrgBar = new HudBar(10, 20, 75, player.getMaxEnergy(), player.getEnergy(), Color.YELLOW);
		bg = new Background("grassBg");
	}

	public void update() {
		player.update();
		map.centerOn(player);
		hpBar.update();
		nrgBar.update();
		
		hpBar.setMaxValue(player.getMaxHealth());
		hpBar.setValue(player.getHealth());
		nrgBar.setMaxValue(player.getMaxEnergy());
		nrgBar.setValue(player.getEnergy());
		
		bg.setOffset((int) (map.getX() / 2), 0);
	}

	public void render(Graphics2D g) {
		bg.render(g);
		map.render(g);
		player.render(g);
		hpBar.render(g);
		nrgBar.render(g);
	}

	public void handleInput(Input input) {
		player.handleInput(input);
		if(input.action3()) {
			player.setPosition(100, 100);
		}
	}

}
