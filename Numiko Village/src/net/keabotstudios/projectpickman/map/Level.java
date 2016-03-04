package net.keabotstudios.projectpickman.map;

import java.util.ArrayList;

import net.keabotstudios.projectpickman.entity.GameObject;
import net.keabotstudios.projectpickman.entity.Player;

public class Level {
	
	private TileMap map;
	private Player player;
	private ArrayList<GameObject> objects;
	private int playerStartX = 0, playerStartY = 0;

	public Level(int playerStartX, int playerStartY, TileSet tileSet, String mapFile) {
		this.map = new TileMap(tileSet, mapFile);
		this.playerStartX = playerStartX;
		this.playerStartY = playerStartY;
		this.player = new Player(map, "testy");
	}

}
