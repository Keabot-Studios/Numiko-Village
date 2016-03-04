package net.keabotstudios.projectpickman.inventory.item;

public class Items {
	
	public static Item knife, gun, bombs, sickle, machineGun, sword;
	
	public static void loadItems() {
		knife = new WeaponKnife();
	}

}
