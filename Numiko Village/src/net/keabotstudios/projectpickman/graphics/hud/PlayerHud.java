package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.Animation;
import net.keabotstudios.projectpickman.graphics.text.RichText;
import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.util.MathUtils;

public class PlayerHud extends HudSet {

	private Player player;
	
	private static final int BG_ALPHA = 255;
	private static final int FG_X_PADDING = 2;
	private static final int FG_HP_Y = 7;
	
	private final int numDigitsMoney;

	public PlayerHud(Player player) {
		super(0, 0);
		this.player = player;
		this.numDigitsMoney = MathUtils.getNumDigits(player.getMaxMoney());
		this.add("hpText", new HudText(FG_X_PADDING, FG_HP_Y - 2, "HP", Font.MAIN, Color.WHITE, 1));
		this.add("hpBar", new HudBar(this.get("hpText").getX() + this.get("hpText").getWidth() + 2, FG_HP_Y, 100, player.getMaxHealth(), player.getHealth(), Color.RED.brighter()));
		this.add("weaponSelector", new HudImage(References.WIDTH - Textures.getTexture("weaponSelector").getWidth() - FG_X_PADDING, 2, 1, Textures.getTexture("weaponSelector")));
		Animation coin = new Animation();
		coin.setFrames(Textures.getSpriteSheet("coin")[0]);
		coin.setDelay(15);
		this.add("moneyText", new HudText(this.get("hpBar").getX() + this.get("hpBar").getWidth() + 8, FG_HP_Y - 2, "" + MathUtils.formatNumber(player.getMoney(), numDigitsMoney), Font.MAIN, Color.WHITE, 1));
		this.add("coin", new HudImage(this.get("moneyText").getX() + this.get("moneyText").getWidth(), FG_HP_Y - 5, 1, coin));

		this.add("textBox", new HudDialougeBox(Font.MAIN));
		this.setDialouge(null);
	}

	int i = 0;
	public void update() {
		super.update();
		i++;
		if(i == 2) {
			i = 0;
			player.addMoney(1);
		}
		((HudBar) this.get("hpBar")).setMaxValue(player.getMaxHealth());
		((HudBar) this.get("hpBar")).setValue(player.getHealth());
		((HudText) this.get("moneyText")).setText(MathUtils.formatNumber(player.getMoney(), numDigitsMoney));
	}

	public void setDialouge(RichText text) {
		if (text == null) {
			this.get("textBox").hide();
		}else
			this.get("textBox").unhide();
		((HudDialougeBox) this.get("textBox")).changeText(text);
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, BG_ALPHA));
		g.fillRect(x, y, References.WIDTH, 20);
		super.render(g);
	}

}
