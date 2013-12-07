package com.me.battleofhero;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hp {
	static final int width = 256, height = 16;
	int hp, id, HP;
	Vector2 position;
	Color color;

	Hp(int HP, int hp, int id, Vector2 position, Color color) {
		this.hp = hp;
		this.HP = HP;
		this.id = id;
		this.position = position;
		this.color = color;
	}

	void draw(SpriteBatch batch) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.drawRectangle(0, 0, width, height);

		pixmap.setColor(Color.WHITE);
		pixmap.fillRectangle(1, 1, width - 2, height - 2);
		pixmap.setColor(color);
		pixmap.fillRectangle(1, 1, width * hp / HP - 2, height - 2);
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion pix = new TextureRegion(pixmaptex, width, height);
		batch.draw(pix, position.x, position.y);
	}

	void lose() {
		hp = Math.max(0, hp - 1);
	}

	void add() {
		hp = Math.min(HP, hp + 1);
	}
}
