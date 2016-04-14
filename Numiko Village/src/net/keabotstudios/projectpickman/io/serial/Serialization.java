package net.keabotstudios.projectpickman.io.serial;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.keabotstudios.projectpickman.io.console.Logger;

public class Serialization {

	/** Defines program that serializes. */
	public static final byte[] HEADER = "PKM".getBytes();
	/** Defines version of program that serializes. */
	public static final short VERSION = 0x0100;
	public static final byte flags = 0x0;
	
	/** 
	 * Writes a byte to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Byte that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 1)
	 */
	public static int writeBytes(byte[] dest, int pointer, byte value) {
		assert(dest.length > pointer + Type.getSize(Type.BYTE));
		dest[pointer++] = value;
		return pointer;
	}
	
	/** 
	 * Writes a byte array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Byte array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, byte[] src) {
		assert(dest.length > pointer + Type.getSize(Type.BYTE) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a short to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Short that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 2)
	 */
	public static int writeBytes(byte[] dest, int pointer, short value) {
		assert(dest.length > pointer + Type.getSize(Type.SHORT));
		dest[pointer++] = (byte)((value >> 8) & 0xFF);
		dest[pointer++] = (byte)((value >> 0) & 0xFF);
		return pointer;
	}
	
	/** 
	 * Writes a short array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Short array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, short[] src) {
		assert(dest.length > pointer + Type.getSize(Type.SHORT) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a char to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Char that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 1)
	 */
	public static int writeBytes(byte[] dest, int pointer, char value) {
		assert(dest.length > pointer + Type.getSize(Type.CHAR));
		dest[pointer++] = (byte)((value >> 8) & 0xFF);
		dest[pointer++] = (byte)((value >> 0) & 0xFF);
		return pointer;
	}
	
	/** 
	 * Writes a char array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Char array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, char[] src) {
		assert(dest.length > pointer + Type.getSize(Type.CHAR) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a integer to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Integer that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 4)
	 */
	public static int writeBytes(byte[] dest, int pointer, int value) {
		assert(dest.length > pointer + Type.getSize(Type.INTEGER));
		dest[pointer++] = (byte)((value >> 24) & 0xFF);
		dest[pointer++] = (byte)((value >> 16) & 0xFF);
		dest[pointer++] = (byte)((value >> 8) & 0xFF);
		dest[pointer++] = (byte)((value >> 0) & 0xFF);
		return pointer;
	}
	
	/** 
	 * Writes a integer array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Integer array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, int[] src) {
		assert(dest.length > pointer + Type.getSize(Type.INTEGER) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a long to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Long that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 8)
	 */
	public static int writeBytes(byte[] dest, int pointer, long value) {
		assert(dest.length > pointer + Type.getSize(Type.LONG));
		dest[pointer++] = (byte)((value >> 56) & 0xFF);
		dest[pointer++] = (byte)((value >> 48) & 0xFF);
		dest[pointer++] = (byte)((value >> 40) & 0xFF);
		dest[pointer++] = (byte)((value >> 32) & 0xFF);
		dest[pointer++] = (byte)((value >> 24) & 0xFF);
		dest[pointer++] = (byte)((value >> 16) & 0xFF);
		dest[pointer++] = (byte)((value >> 8) & 0xFF);
		dest[pointer++] = (byte)((value >> 0) & 0xFF);
		return pointer;
	}
	
	/** 
	 * Writes a long array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Long array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, long[] src) {
		assert(dest.length > pointer + Type.getSize(Type.LONG) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a float to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Float that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 4)
	 */
	public static int writeBytes(byte[] dest, int pointer, float value) {
		assert(dest.length > pointer + Type.getSize(Type.FLOAT));
		int data = Float.floatToIntBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	/** 
	 * Writes a float array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Float array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length * double size in bytes)
	 */
	public static int writeBytes(byte[] dest, int pointer, float[] src) {
		assert(dest.length > pointer + Type.getSize(Type.FLOAT) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a double to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Double that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 8)
	 */
	public static int writeBytes(byte[] dest, int pointer, double value) {
		assert(dest.length > pointer + Type.getSize(Type.DOUBLE));
		long data = Double.doubleToLongBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	/** 
	 * Writes a double array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Double array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length * 8)
	 */
	public static int writeBytes(byte[] dest, int pointer, double[] src) {
		assert(dest.length > pointer + Type.getSize(Type.DOUBLE) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a boolean to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param value Boolean that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + 8)
	 */
	public static int writeBytes(byte[] dest, int pointer, boolean value) {
		assert(dest.length > pointer + Type.getSize(Type.BOOLEAN));
		dest[pointer++] = (byte) (value ? 1 : 0);
		return pointer;
	}
	
	/** 
	 * Writes a boolean array to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param src Boolean array that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + src.length)
	 */
	public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
		assert(dest.length > pointer + Type.getSize(Type.BOOLEAN) * src.length);
		for(int i = 0; i < src.length; i++)
			pointer = writeBytes(dest, pointer, src[i]);
		return pointer;
	}
	
	/** 
	 * Writes a string to a byte array.
	 * @param dest Where the data gets written to.
	 * @param pointer Where the data gets written in <b>dest</b>.
	 * @param string String that gets written to <b>dest</b>.
	 * @return pointer after the data was added. (i.e. returns pointer + string.length() + 2)
	 */
	public static int writeBytes(byte[] dest, int pointer, String string) {
		assert(dest.length > pointer + Type.getSize(Type.SHORT) + Type.getSize(Type.BYTE) * string.length());
		pointer = writeBytes(dest, pointer, string.length());
		return writeBytes(dest, pointer, string.getBytes());
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The byte read.
	 */
	public static byte readByte(byte[] src, int pointer) {
		return src[pointer];
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The char read.
	 */
	public static char readChar(byte[] src, int pointer) {
		return (char) src[pointer];
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The short read.
	 */
	public static short readShort(byte[] src, int pointer) {
		return (short) ((src[pointer] << 8) | src[pointer + 1]);
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The integer read.
	 */
	public static int readInt(byte[] src, int pointer) {
		return (int) ((src[pointer] << 24) | (src[pointer + 1] << 16) | (src[pointer + 2] << 8) | src[pointer + 3]);
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The long read.
	 */
	public static long readLong(byte[] src, int pointer) {
		return (long) ((src[pointer] << 56) | (src[pointer + 1] << 48) | (src[pointer + 2] << 40) | (src[pointer + 3] << 32) | (src[pointer + 4] << 24) | (src[pointer + 5] << 16) | (src[pointer + 6] << 8) | src[pointer + 7]);
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The float read.
	 */
	public static float readFloat(byte[] src, int pointer) {
		return Float.intBitsToFloat(readInt(src, pointer));
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The double read.
	 */
	public static double readDouble(byte[] src, int pointer) {
		return Double.longBitsToDouble(readLong(src, pointer));
	}
	
	/**
	 * @param src Byte array to read from.
	 * @param pointer Where to read from the byte array.
	 * @return The boolean read.
	 */
	public static boolean readBoolean(byte[] src, int pointer) {
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}
	
	/**
	 * Saves a byte array to a file.
	 * @param path The path to write the data to. (i.e. path/to/file.extention)
	 * @param data The data to write to the file.
	 */
	public static void saveToFile(String path, byte[] data) {
		try {
			Logger.info("Writing file to: " + path + ", size: " + data.length + " bytes");
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a byte array from a file.
	 * @param path The path to read the data from. (i.e. path/to/file.extention)
	 * @return The data to read from the file.
	 */
	public static byte[] loadFromFile(String path) {
		try {
			byte[] data = Files.readAllBytes(Paths.get(path));
			Logger.info("Reading file from: " + path + ", size: " + data.length + " bytes");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
