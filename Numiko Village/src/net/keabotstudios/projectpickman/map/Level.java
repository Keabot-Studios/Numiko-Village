package net.keabotstudios.projectpickman.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.keabotstudios.projectpickman.entity.GameObject;
import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.io.Input;
import net.keabotstudios.projectpickman.io.Input.InputAxis;

public class Level {
	
	private Background bg;
	private TileMap map;
	private Player player;
	private ArrayList<GameObject> objects;
	private int playerStartX = 0, playerStartY = 0;

	public Level(int playerStartX, int playerStartY, TileSet tileSet, String mapFile, String background, boolean limitedEnergy) {
		this.bg = new Background(background);
		this.map = new TileMap(tileSet, mapFile);
		this.playerStartX = playerStartX;
		this.playerStartY = playerStartY;
		this.player = new Player(map, "testy", limitedEnergy);
		this.player.setPosition(playerStartX, playerStartY);
		this.objects = new ArrayList<GameObject>();
	}
	
	public void update() {
		bg.update();
		player.update();
		map.centerOn(player);
		for(GameObject o : objects) {
			o.update();
		}
		bg.setOffset((int) (map.getX() / 2), 0);
	}
	
	public void render(Graphics2D g) {
		bg.render(g);
		map.render(g);
		for(GameObject o : objects) {
			o.render(g);
		}
		player.render(g);
	}
	
	public void handleInput(Input input) {
		player.handleInput(input);
		if(input.getInput(InputAxis.F1)) {
			player.setPosition(playerStartX, playerStartY);
		}
	}
	
	public Player getPlayer() {
		return player;
	}

}
