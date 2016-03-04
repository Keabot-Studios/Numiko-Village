package net.keabotstudios.projectpickman.inventory.item;

import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.entity.Player.Action;
import net.keabotstudios.projectpickman.map.Level;

public class WeaponKnife extends Weapon {

	public WeaponKnife() {
		super("'Kitchen' Knife", "An old, rusty kitchen knife. There seems to be flecks of red on it...?", 0);
	}

	public void activate(Player player, Level level) {
		player.setAction(Action.SWINGINGKNIFE);
	}

}
