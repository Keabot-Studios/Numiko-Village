package net.keabotstudios.projectpickman.io.serial;

public class SerialField extends SerialItem {

	public byte type;
	public byte[] data;
	
	private SerialField(String name) {
		super(ContainerType.FIELD, name);
		size += Type.getSize(Type.BYTE);
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = super.writeBytes(dest, pointer);
		System.out.println((Type.getSize(Type.BYTE) * 2 + Type.getSize(type) + Type.getSize(Type.SHORT) + Type.getSize(Type.INTEGER)));
		pointer = Serialization.writeBytes(dest, pointer, type);
		pointer = Serialization.writeBytes(dest, pointer, data);
		return pointer;
	}
	
	public static SerialField create(String name, byte value) {
		SerialField field = new SerialField(name);
		field.type = Type.BYTE;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static SerialField create(String name, char value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.CHAR;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}

	public static SerialField create(String name, short value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.SHORT;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static SerialField create(String name, int value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.INTEGER;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}

	public static SerialField create(String name, long value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.LONG;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static SerialField create(String name, float value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.FLOAT;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}
	
	public static SerialField create(String name, double value) {
		SerialField field = new SerialField(name);
		field.setName(name);
		field.type = Type.DOUBLE;
		field.size += Type.getSize(field.type);
		field.data = new byte[Type.getSize(field.type)];
		Serialization.writeBytes(field.data, 0, value);
		return field;
	}

}
