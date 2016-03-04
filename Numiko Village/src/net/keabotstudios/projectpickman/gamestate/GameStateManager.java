package net.keabotstudios.projectpickman.gamestate;

import java.awt.Graphics2D;
import java.util.LinkedList;

import net.keabotstudios.projectpickman.io.Input;

public class GameStateManager {

	private boolean blockInput = false;

	private Input input;
	private LinkedList<GameState> states;

	public GameStateManager(Input input) {
		states = new LinkedList<GameState>();
		this.input = input;
	}

	public void update() {
		if (!blockInput)
			states.peek().handleInput(input);
		states.peek().update();
	}

	public void render(Graphics2D g) {
		states.peek().render(g);
	}

	public void pop() {
		states.pop();
	}

	public void push(GameState s) {
		s.init();
		states.push(s);
	}

	public void set(GameState s) {
		states.pop();
		s.init();
		states.push(s);
	}

	public void blockInput(boolean b) {
		blockInput = b;
	}

	public Input getInput() {
		return input;
	}
}
