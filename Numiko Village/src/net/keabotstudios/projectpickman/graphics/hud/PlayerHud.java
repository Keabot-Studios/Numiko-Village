package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.text.RichText;
import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.loading.Textures;

public class PlayerHud extends HudSet {

	private Player player;

	public PlayerHud(Player player) {
		super(5, 5);
		this.player = player;
		this.add("hpText", new HudText(0, 0, "HP:", Font.MAIN, Color.BLACK, 1));
		this.add("hpBar", new HudBar(3 + this.get("hpText").getWidth(), 2, 75, player.getMaxHealth(), player.getHealth(), Color.RED.brighter()));
		if (this.player.hasLimitedEnergy()) {
			this.add("nrgText", new HudText(this.get("hpText").getX() + this.get("hpText").getWidth() + 6 + this.get("hpBar").getWidth(), 0, "NRG:", Font.MAIN, Color.BLACK, 1));
			this.add("nrgBar", new HudBar(this.get("nrgText").getX() + 3 + this.get("nrgText").getWidth(), 2, 75, player.getMaxEnergy(), player.getEnergy(), Color.YELLOW));
		}
		this.add("weaponSelector", new HudImage(References.WIDTH - Textures.getTexture("weaponSelector").getWidth() - 10, -3, 1, Textures.getTexture("weaponSelector")));

		this.add("textBox", new HudDialougeBox(Font.MAIN));
	}

	public void update() {
		super.update();

		((HudBar) this.get("hpBar")).setMaxValue(player.getMaxHealth());
		((HudBar) this.get("hpBar")).setValue(player.getHealth());
		if (this.player.hasLimitedEnergy()) {
			((HudBar) this.get("nrgBar")).setMaxValue(player.getMaxEnergy());
			((HudBar) this.get("nrgBar")).setValue(player.getEnergy());
		}
	}

	public void setDialouge(RichText text) {
		((HudDialougeBox) this.get("textBox")).changeText(text);
	}

}
