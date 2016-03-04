package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;

import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.font.Font;
import net.keabotstudios.projectpickman.loading.Textures;

public class PlayerHud extends HudSet {
	
	private Player player;

	public PlayerHud(Player player) {
		super(10, 10);
		this.player = player;
		this.add("hpText", new HudText(0, 0, "HP:", Font.MAIN, Color.BLACK, 1));
		this.add("nrgText", new HudText(0, 10, "NRG:", Font.MAIN, Color.BLACK, 1));
		this.add("hpBar", new HudBar(3 + this.get("nrgText").getWidth(), 2, 75, player.getMaxHealth(), player.getHealth(), Color.RED.brighter()));
		this.add("nrgBar", new HudBar(3 + this.get("nrgText").getWidth(), 12, 75, player.getMaxEnergy(), player.getEnergy(), Color.YELLOW));
		this.add("weaponSelector", new HudImage(0, 20, 1, Textures.getTexture("weaponSelector")));
		this.add("textBox", new HudDialougeBox());
	}
	
	public void update() {
		super.update();

		((HudBar) this.get("hpBar")).setMaxValue(player.getMaxHealth());
		((HudBar) this.get("hpBar")).setValue(player.getHealth());
		((HudBar) this.get("nrgBar")).setMaxValue(player.getMaxEnergy());
		((HudBar) this.get("nrgBar")).setValue(player.getEnergy());
	}

}
