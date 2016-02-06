package net.keabotstudios.projectpickman.math;

import static java.lang.Math.*;

import java.nio.FloatBuffer;

import net.keabotstudios.projectpickman.util.BufferUtils;

public class Matrix4f {

	public float[] matrix = new float[4 * 4];

	/**
	 * @return An identity matrix.
	 * @author Richard Abbott
	 */
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();

		for (int i = 0; i < result.matrix.length; i++) {
			result.matrix[i] = 0.0f;
		}

		result.matrix[0 + 0 * 4] = 1.0f;
		result.matrix[1 + 1 * 4] = 1.0f;
		result.matrix[2 + 2 * 4] = 1.0f;
		result.matrix[3 + 3 * 4] = 1.0f;

		return result;
	}

	/**
	 * @return The product of this matrix and matrix.
	 * @param matrix
	 *            The matrix to multiply with.
	 * @author Richard Abbott
	 */
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.matrix[e + y * 4] * matrix.matrix[x + e * 4];
				}
				result.matrix[x + y * 4] = sum;
			}
		}

		return result;
	}

	/**
	 * @param vector
	 *            The position to translate to. (x, y, z)
	 * @return A translation matrix.
	 * @author Richard Abbott
	 */
	public static Matrix4f translate(Vector3f vector) {
		Matrix4f result = identity();

		result.matrix[0 + 3 * 4] = vector.x;
		result.matrix[1 + 3 * 4] = vector.y;
		result.matrix[2 + 3 * 4] = vector.z;

		return result;
	}

	/**
	 * @param angle
	 *            The angle to rotate to. (a)
	 * @return A rotation matrix.
	 * @author Richard Abbott
	 */
	public static Matrix4f rotate(float angle) {
		Matrix4f result = identity();
		float r = (float) toRadians(angle);
		float cos = (float) cos(r);
		float sin = (float) sin(r);

		result.matrix[0 + 0 * 4] = cos;
		result.matrix[1 + 0 * 4] = sin;
		result.matrix[0 + 1 * 4] = -sin;
		result.matrix[1 + 1 * 4] = cos;

		return result;
	}

	/**
	 * Creates an orthographic matrix.
	 * 
	 * @param left
	 *            Left bound.
	 * @param right
	 *            Right bound.
	 * @param bottom
	 *            Bottom bound.
	 * @param top
	 *            Top bound.
	 * @param near
	 *            Near bound.
	 * @param fat
	 *            Far bound.
	 * @return An orthographic matrix.
	 * @author Richard Abbott
	 */
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = identity();

		result.matrix[0 + 0 * 4] = 2.0f / (right - left);

		result.matrix[1 + 1 * 4] = 2.0f / (top - bottom);

		result.matrix[2 + 2 * 4] = 2.0f / (near - far);

		result.matrix[0 + 3 * 4] = (left + right) / (left - right);
		result.matrix[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.matrix[2 + 3 * 4] = (near + far) / (near - far);

		return result;
	}

	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(matrix);
	}

}
