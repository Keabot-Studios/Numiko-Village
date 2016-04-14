package net.keabotstudios.projectpickman.io.serial;

import java.util.ArrayList;
import java.util.List;

public class SerialObject extends SerialItem {
	
	private short fieldCount;
	private List<SerialField> fields = new ArrayList<SerialField>();
	private short arrayCount;
	private List<SerialArray> arrays = new ArrayList<SerialArray>();
	
	public SerialObject(String name) {
		super(ContainerType.OBJECT, name);
		size += Type.getSize(Type.SHORT) * 2;
	}

	public void addField(SerialField field) {
		fields.add(field);
		size += field.getSize();
		fieldCount = (short) fields.size();
	}
	
	public void addArray(SerialArray array) {
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short) arrays.size();
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = super.writeBytes(dest, pointer);
		//System.out.println(size + ", " + (Type.getSize(Type.SHORT) * 3 + arrays.get(0).getSize() + fields.get(0).getSize() + fields.get(1).getSize() + Type.getSize(Type.BYTE) + Type.getSize(Type.INTEGER)));
		pointer = Serialization.writeBytes(dest, pointer, fieldCount);
		for(SerialField field : fields)
			pointer = field.writeBytes(dest, pointer);
		
		pointer = Serialization.writeBytes(dest, pointer, arrayCount);
		for(SerialArray array : arrays)
			pointer = array.writeBytes(dest, pointer);
		return pointer;
	}

}
