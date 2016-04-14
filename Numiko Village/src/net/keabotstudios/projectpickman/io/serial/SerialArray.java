package net.keabotstudios.projectpickman.io.serial;

public class SerialArray extends SerialItem {
	
	public byte type;
	public int count;
	
	public byte[] byteData;
	private short[] shortData;
	private char[] charData;
	private int[] intData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;
	
	private SerialArray(String name) {
		super(ContainerType.ARRAY, name);
		size += Type.getSize(Type.BYTE) + Type.getSize(Type.INTEGER);
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		
		nameLength = (short) name.length();
		this.name = name.getBytes();
	}
	
	public void updateSize() {
		size += count * Type.getSize(type);
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = super.writeBytes(dest, pointer);
		pointer = Serialization.writeBytes(dest, pointer, type);
		pointer = Serialization.writeBytes(dest, pointer, count);
		switch(type) {
		case Type.BYTE:
			pointer = Serialization.writeBytes(dest, pointer, byteData);
			break;
		case Type.SHORT:
			pointer = Serialization.writeBytes(dest, pointer, shortData);
			break;
		case Type.CHAR:
			pointer = Serialization.writeBytes(dest, pointer, charData);
			break;
		case Type.INTEGER:
			pointer = Serialization.writeBytes(dest, pointer, intData);
			break;
		case Type.LONG:
			pointer = Serialization.writeBytes(dest, pointer, longData);
			break;
		case Type.FLOAT:
			pointer = Serialization.writeBytes(dest, pointer, floatData);
			break;
		case Type.DOUBLE:
			pointer = Serialization.writeBytes(dest, pointer, doubleData);
			break;
		case Type.BOOLEAN:
			pointer = Serialization.writeBytes(dest, pointer, booleanData);
			break;
		}
		return pointer;
	}
	
	public static SerialArray create(String name, byte[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.BYTE;
		array.count = data.length;
		array.byteData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, short[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.SHORT;
		array.count = data.length;
		array.shortData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, char[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.CHAR;
		array.count = data.length;
		array.charData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, int[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.INTEGER;
		array.count = data.length;
		array.intData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, long[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.LONG;
		array.count = data.length;
		array.longData = data;
		array.updateSize();
		return array;
	}

	public static SerialArray create(String name, float[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.FLOAT;
		array.count = data.length;
		array.floatData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, double[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.DOUBLE;
		array.count = data.length;
		array.doubleData = data;
		array.updateSize();
		return array;
	}
	
	public static SerialArray create(String name, boolean[] data) {
		SerialArray array = new SerialArray(name);
		array.type = Type.BOOLEAN;
		array.count = data.length;
		array.booleanData = data;
		array.updateSize();
		return array;
	}

}
