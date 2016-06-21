package net.keabotstudios.projectpickman.gamestate;

import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.graphics.hud.PlayerHud;
import net.keabotstudios.projectpickman.graphics.text.RichText;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.Paths;
import net.keabotstudios.projectpickman.map.Level;
import net.keabotstudios.projectpickman.map.TileSet;

public class TestState extends GameState {

	private Level level;
	private PlayerHud playerHud;

	public TestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		level = new Level(100, 100, new TileSet(Paths.TILESETS + "/testTileset.png", Paths.TILESETS + "/testTileset.tsc"), Paths.MAPS + "/testMap.tmp", "grassBg", false);
		playerHud = new PlayerHud(level.getPlayer());
		RichText testText = new RichText("Well, <c2><playername><c0>, you made it.<n>Now let's see...<n>HOW LONG YOU CAN LIVE!");
	}

	public void update() {
		level.update();
		playerHud.update();
	}

	public void render(Graphics2D g) {
		level.render(g);
		playerHud.render(g);
	}

	public void handleInput(Input input) {
		level.handleInput(input);
	}

}
