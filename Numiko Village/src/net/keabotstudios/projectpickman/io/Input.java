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
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.keabotstudios.projectpickman.Game;

public class Input implements KeyListener, MouseMotionListener, MouseListener, FocusListener {

	private static final int NUM_KEYS = KeyEvent.KEY_LAST;
	private static final int NUM_MBTNS = MouseEvent.MOUSE_LAST;
	
	private static float DEADZONE = 0.25f;

	private static int UP = KeyEvent.VK_UP;
	private static int DOWN = KeyEvent.VK_DOWN;
	private static int LEFT = KeyEvent.VK_LEFT;
	private static int RIGHT = KeyEvent.VK_RIGHT;

	private static int ACTION1 = KeyEvent.VK_Z;
	private static int ACTION2 = KeyEvent.VK_X;
	private static int ACTION3 = KeyEvent.VK_C;

	private static int FN_1 = KeyEvent.VK_F1;
	private static int FN_2 = KeyEvent.VK_F2;
	private static int FN_3 = KeyEvent.VK_F3;

	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] lastKeys = new boolean[NUM_KEYS];
	private boolean[] mouseButtons = new boolean[NUM_MBTNS];
	private boolean[] lastMouseButtons = new boolean[NUM_MBTNS];
	private int mouseX = 0, mouseY = 0;

	private Controller activeController = null;
	private HashMap<Component.Identifier, Float> controllerAxes = new HashMap<Component.Identifier, Float>();

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
		}
	}

	public boolean up() {
		if(keys[UP]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Axis.POV).floatValue();
			if(value == 0.25f) return true;
			value = controllerAxes.get(Component.Identifier.Axis.Y).floatValue();
			if(value < -DEADZONE) return true;
		}
		return false;
	}

	public boolean down() {
		if(keys[DOWN]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Axis.POV).floatValue();
			if(value == 0.75f) return true;
			value = controllerAxes.get(Component.Identifier.Axis.Y).floatValue();
			if(value > DEADZONE) return true;
		}
		return false;
	}

	public boolean left() {
		if(keys[LEFT]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Axis.POV).floatValue();
			if(value == 1.0f) return true;
			value = controllerAxes.get(Component.Identifier.Axis.X).floatValue();
			if(value < -DEADZONE) return true;
		}
		return false;
	}

	public boolean right() {
		if(keys[RIGHT]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Axis.POV).floatValue();
			if(value == 0.5f) return true;
			value = controllerAxes.get(Component.Identifier.Axis.X).floatValue();
			if(value > DEADZONE) return true;
		}
		return false;
	}
	
	public boolean action1() {
		if(keys[ACTION1]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Button._0).floatValue();
			if(value == 1.0f) return true;
		}
		return false;
	}

	public boolean action2() {
		if(keys[ACTION2]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Button._1).floatValue();
			if(value == 1.0f) return true;
		}
		return false;
	}

	public boolean action3() {
		if(keys[ACTION3]) return true;
		if(hasController()) {
			float value = controllerAxes.get(Component.Identifier.Button._2).floatValue();
			if(value == 1.0f) return true;
		}
		return false;
	}
	
	public boolean fn1() {
		return keys[FN_1];
	}
	
	public boolean fn2() {
		return keys[FN_2];
	}
	
	public boolean fn3() {
		return keys[FN_3];
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
