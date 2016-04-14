package net.keabotstudios.projectpickman.io.serial;

import java.util.ArrayList;
import java.util.List;

public class SerialDatabase extends SerialItem {

	private static final byte[] DATABASE_HEAD = "PKM_DB".getBytes();
	private short objectCount;
	private List<SerialObject> objects = new ArrayList<SerialObject>();
	
	public SerialDatabase(String name) {
		super(ContainerType.DATABASE, name);
		size += Type.getSize(Type.SHORT) + DATABASE_HEAD.length;
	}
	
	public void addObject(SerialObject object) {
		objects.add(object);
		size += object.getSize();
		objectCount = (short) objects.size();
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = Serialization.writeBytes(dest, pointer, DATABASE_HEAD);
		pointer = super.writeBytes(dest, pointer);
		pointer = Serialization.writeBytes(dest, pointer, objectCount);
		for(SerialObject object : objects)
			pointer = object.writeBytes(dest, pointer);
		
		return pointer;
	}
	
}
