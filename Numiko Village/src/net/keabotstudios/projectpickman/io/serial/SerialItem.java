package net.keabotstudios.projectpickman.io.serial;

public abstract class SerialItem {
	
	public final byte containerType;
	public short nameLength;
	public byte[] name;
	public int size = Type.getSize(Type.BYTE) + Type.getSize(Type.SHORT) + Type.getSize(Type.INTEGER);
	
	protected SerialItem(byte containerType, String name) {
		this.containerType = containerType;
		setName(name);
	}
	
	public void setName(String name) {
		assert(name.length() <= Short.MAX_VALUE);
		
		if(this.name != null)
			size -= nameLength;
		
		nameLength = (short) name.length();
		this.name = name.getBytes();
		size += nameLength;
	}
	
	public int getSize() {
		return size;
	}
	
	protected int writeBytes(byte[] dest, int pointer) {
		pointer = Serialization.writeBytes(dest, pointer, containerType);
		pointer = Serialization.writeBytes(dest, pointer, nameLength);
		pointer = Serialization.writeBytes(dest, pointer, name);
		pointer = Serialization.writeBytes(dest, pointer, size);
		return pointer;
	}

}
