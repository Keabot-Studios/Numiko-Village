package net.keabotstudios.projectpickman.input;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {

	private static final float DEADZONE = 0.3f;

	private boolean[] keys;
	private boolean[] mouseButtons;
	private int mouseX = 0, mouseY = 0;
	private int controller = -1;
	private FloatBuffer ctrlAxes;
	private ByteBuffer ctrlBtns;

	public Input(long window) {
		keys = new boolean[65536];
		mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

		for (int i = GLFW.GLFW_JOYSTICK_1; i < GLFW.GLFW_JOYSTICK_LAST; i++) {
			if (GLFW.glfwGetJoystickName(i) != null) {
				controller = i;
				System.out.println("Using controller: " + GLFW.glfwGetJoystickName(i) + " at ID: " + i);
				break;
			}
		}
		if (controller == -1)
			System.err.println("No controllers found.");

		GLFW.glfwSetKeyCallback(window, this.new Keyboard());
		GLFW.glfwSetCursorPosCallback(window, this.new MousePosition());
		GLFW.glfwSetMouseButtonCallback(window, this.new MouseButtons());
	}

	public void update() {
		ctrlAxes = GLFW.glfwGetJoystickAxes(controller);
		ctrlBtns = GLFW.glfwGetJoystickButtons(controller);
	}

	public boolean up() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_UP] || ctrlAxes.get(2) > DEADZONE || ctrlBtns.get(11) == 1;
		else {
			return keys[GLFW.GLFW_KEY_UP];
		}
	}

	public boolean down() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_DOWN] || ctrlAxes.get(2) < DEADZONE || ctrlBtns.get(13) == 1;
		else {
			return keys[GLFW.GLFW_KEY_DOWN];
		}
	}

	public boolean left() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_LEFT] || ctrlAxes.get(1) < DEADZONE || ctrlBtns.get(14) == 1;
		else {
			return keys[GLFW.GLFW_KEY_LEFT];
		}

	}

	public boolean right() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_RIGHT] || ctrlAxes.get(1) > DEADZONE || ctrlBtns.get(12) == 1;
		else {
			return keys[GLFW.GLFW_KEY_RIGHT];
		}

	}

	public boolean action1() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_Z] || ctrlBtns.get(0) == 1;
		else {
			return keys[GLFW.GLFW_KEY_Z];
		}

	}

	public boolean action2() {
		if (controller != -1)
			return keys[GLFW.GLFW_KEY_X] || ctrlBtns.get(1) == 1;
		else {
			return keys[GLFW.GLFW_KEY_X];
		}

	}

	public int mouseX() {
		return mouseX;
	}

	public int mouseY() {
		return mouseY;
	}

	public boolean mouse1() {
		return mouseButtons[GLFW.GLFW_MOUSE_BUTTON_LEFT];
	}

	public boolean mouse2() {
		return mouseButtons[GLFW.GLFW_MOUSE_BUTTON_RIGHT];
	}

	public boolean mouse3() {
		return mouseButtons[GLFW.GLFW_MOUSE_BUTTON_MIDDLE];
	}

	public boolean hasController() {
		return controller != -1;
	}

	public class Keyboard extends GLFWKeyCallback {
		public void invoke(long window, int key, int scancode, int action, int mods) {
			keys[key] = action != GLFW.GLFW_RELEASE;
		}
	}

	public class MouseButtons extends GLFWMouseButtonCallback {
		public void invoke(long window, int button, int action, int mods) {
			if (button > mouseButtons.length)
				return;
			mouseButtons[button] = action != GLFW.GLFW_RELEASE;
		}
	}

	public class MousePosition extends GLFWCursorPosCallback {
		public void invoke(long window, double xpos, double ypos) {
			mouseX = (int) xpos;
			mouseY = (int) ypos;
		}
	}

}
