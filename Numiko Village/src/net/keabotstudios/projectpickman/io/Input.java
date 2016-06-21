package net.keabotstudios.projectpickman.io;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.keabotstudios.projectpickman.Game;
import net.keabotstudios.projectpickman.io.Input.InputAxis;
import net.keabotstudios.projectpickman.io.console.Logger;

public class Input implements KeyListener, MouseMotionListener, MouseListener, FocusListener {

	private static final int NUM_KEYS = KeyEvent.KEY_LAST;
	private static final int NUM_MBTNS = MouseEvent.MOUSE_LAST;

	private static float DEADZONE = 0.25f;

	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] lastKeys = new boolean[NUM_KEYS];
	private boolean[] mouseButtons = new boolean[NUM_MBTNS];
	private boolean[] lastMouseButtons = new boolean[NUM_MBTNS];
	private int mouseX = 0, mouseY = 0;

	private Controller activeController = null;
	private HashMap<Component.Identifier, Float> controllerAxes = new HashMap<Component.Identifier, Float>();
	private HashMap<Component.Identifier, Float> lastControllerAxes = new HashMap<Component.Identifier, Float>();

	public enum InputAxis {
		UP(KeyEvent.VK_UP, Component.Identifier.Axis.Y, -DEADZONE, Component.Identifier.Axis.POV, 0.25f),
		DOWN(KeyEvent.VK_DOWN, Component.Identifier.Axis.Y, DEADZONE, Component.Identifier.Axis.POV, 0.75f),
		LEFT(KeyEvent.VK_LEFT, Component.Identifier.Axis.X, -DEADZONE, Component.Identifier.Axis.POV, 1.0f),
		RIGHT(KeyEvent.VK_RIGHT, Component.Identifier.Axis.X, DEADZONE, Component.Identifier.Axis.POV, 0.5f),
		ACTION1(KeyEvent.VK_Z, Component.Identifier.Button._0, 1.0f),
		ACTION2(KeyEvent.VK_X, Component.Identifier.Button._1, 1.0f),
		ACTION3(KeyEvent.VK_A, Component.Identifier.Button._2, 1.0f),
		ACTION4(KeyEvent.VK_S, Component.Identifier.Button._3, 1.0f),
		F1(KeyEvent.VK_F1),
		F2(KeyEvent.VK_F2),
		F3(KeyEvent.VK_F3),
		F4(KeyEvent.VK_F4),
		F5(KeyEvent.VK_F5),
		F6(KeyEvent.VK_F6);

		private int keyCode;
		private Identifier identifier1 = null, identifier2 = null;
		private float actZone1 = 0.0f, actZone2 = 0.0f;

		private InputAxis(int keyCode) {
			this.keyCode = keyCode;
		}

		private InputAxis(int keyCode, Identifier identifier, float actZone) {
			this.keyCode = keyCode;
			this.identifier2 = identifier;
			this.actZone2 = actZone;
		}

		private InputAxis(int keyCode, Identifier identifier1, float actZone1, Identifier identifier2, float actZone2) {
			this.keyCode = keyCode;
			this.identifier1 = identifier1;
			this.actZone1 = actZone1;
			this.identifier2 = identifier2;
			this.actZone2 = actZone2;
		}

		public int getKeyCode() {
			return keyCode;
		}

		public Identifier getIdentifier1() {
			return identifier1;
		}

		public Identifier getIdentifier2() {
			return identifier2;
		}

		public float getActZone1() {
			return actZone1;
		}

		public float getActZone2() {
			return actZone2;
		}
	}

	public Input(Game game) {
		game.getFrame().addKeyListener(this);
		game.getFrame().addMouseMotionListener(this);
		game.getFrame().addMouseListener(this);

		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

		for (int i = 0; i < ca.length; i++) {
			if (ca[i].getType() == Controller.Type.GAMEPAD) {
				activeController = ca[i];
				break;
			}
		}
		if (!hasController()) {
			Logger.info("No gamepad detected.");
		} else {
			Logger.info("Gamepad detected: " + activeController.getName());
			Component[] components = activeController.getComponents();
			for (Component c : components) {
				controllerAxes.put(c.getIdentifier(), 0.0f);
				lastControllerAxes.put(c.getIdentifier(), 0.0f);
			}
		}
	}

	public void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (lastKeys[i] != keys[i]) {
				lastKeys[i] = keys[i];
			}
		}

		for (int i = 0; i < NUM_MBTNS; i++) {
			if (lastMouseButtons[i] != mouseButtons[i]) {
				lastMouseButtons[i] = mouseButtons[i];
			}
		}

		if (hasController()) {
			activeController.poll();
			EventQueue queue = activeController.getEventQueue();
			Event event = new Event();
			while (queue.getNextEvent(event)) {
				Component comp = event.getComponent();
				float value = event.getValue();
				controllerAxes.replace(comp.getIdentifier(), value);
			}
			for (Component comp : activeController.getComponents()) {
				if (lastControllerAxes.get(comp.getIdentifier()) != controllerAxes.get(comp.getIdentifier())) {
					lastControllerAxes.replace(comp.getIdentifier(), controllerAxes.get(comp.getIdentifier()));
				}
			}
		}

	}

	public boolean getInput(InputAxis axis) {
		if (keys[axis.getKeyCode()])
			return true;
		if (hasController()) {
			float value = 0.0f;
			if (axis.getIdentifier1() != null) {
				value = controllerAxes.get(axis.getIdentifier1()).floatValue();
				if (axis.getActZone1() > 0) {
					if (value > axis.getActZone1())
						return true;
				} else {
					if (value < axis.getActZone1())
						return true;
				}

			}
			value = 0.0f;
			if (axis.getIdentifier2() != null) {
				value = controllerAxes.get(axis.getIdentifier2()).floatValue();
				if (value == axis.getActZone2())
					return true;
			}

		}
		return false;
	}

	public boolean getInputTapped(InputAxis axis) {
		if (keys[axis.getKeyCode()] != lastKeys[axis.getKeyCode()])
			return true;
		if (hasController()) {
			float value = 0.0f;
			float lastValue = 0.0f;
			if (axis.getIdentifier1() != null) {
				value = controllerAxes.get(axis.getIdentifier1()).floatValue();
				lastValue = lastControllerAxes.get(axis.getIdentifier1()).floatValue();
				if (axis.getActZone1() > 0) {
					if (lastValue != value && value > axis.getActZone1())
						return true;
				} else {
					if (lastValue != value && value < axis.getActZone1())
						return true;
				}

			}
			value = 0.0f;
			lastValue = 0.0f;
			if (axis.getIdentifier2() != null) {
				value = controllerAxes.get(axis.getIdentifier2()).floatValue();
				lastValue = lastControllerAxes.get(axis.getIdentifier2()).floatValue();
				if (lastValue != value && value == axis.getActZone2())
					return true;
			}
		}
		return false;
	}

	public boolean isMouseButtonClicked(int mbutton) {
		if (mbutton >= mouseButtons.length)
			return false;
		return mouseButtons[mbutton] && !lastMouseButtons[mbutton];
	}

	public boolean isMouseButtonPressed(int mbutton) {
		if (mbutton >= mouseButtons.length)
			return false;
		return mouseButtons[mbutton];
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() >= mouseButtons.length)
			return;
		mouseButtons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() >= mouseButtons.length)
			return;
		mouseButtons[e.getButton()] = false;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < NUM_KEYS; i++) {
			keys[i] = false;
		}
		for (int i = 0; i < NUM_MBTNS; i++) {
			mouseButtons[i] = false;
		}
	}

	public boolean hasController() {
		return activeController != null;
	}

}
