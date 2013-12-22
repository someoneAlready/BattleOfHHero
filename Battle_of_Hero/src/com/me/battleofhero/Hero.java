package com.me.battleofhero;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class Hero {
	final int HP = 100, MG = 200;
	static final Vector2 v[] = { new Vector2(-200, 0), new Vector2(200, 0),
			new Vector2(0, 300), new Vector2(0, -300) };

	Texture Image[] = new Texture[2];
	Hp hp, mg;
	Rectangle position;
	int path, gravity, id;
	Array<Weapon> weapons = new Array<Weapon>();

	Hero(String s1, String s2, Rectangle position, int path, int id) {
		Image[0] = new Texture(Gdx.files.internal(s1));
		Image[1] = new Texture(Gdx.files.internal(s2));
		this.position = position;
		this.path = path;
		this.id = id;
		hp = new Hp(HP, HP, id, new Vector2((id == 0 ? 10 : Battle_of_Hero.width
				- Hp.width - 10), Battle_of_Hero.height - 30), Color.RED);
		mg = new Hp(MG, MG/2, id, new Vector2((id == 0 ? 10 : Battle_of_Hero.width
				- Hp.width - 10), Battle_of_Hero.height - 70), Color.BLUE);
	}

	void draw(SpriteBatch batch) {
		hp.draw(batch);
		mg.draw(batch);
		batch.draw(Image[path], position.x, position.y);
		for (Weapon weapon : weapons)
			weapon.draw(batch);
	}

	void move() {
		if (position.y != 0) {
			gravity += 4;
			position.y = Math.max(0,
					position.y - gravity * Gdx.graphics.getDeltaTime());
		} else
			gravity = 0;

		Iterator<Weapon> it = weapons.iterator();
		while (it.hasNext()) {
			Weapon weapon = it.next();
			weapon.move();
			if (weapon.position.x < 0
					|| weapon.position.y > Battle_of_Hero.width - Weapon.width)
				it.remove();
			else {
				if (weapon.position
						.overlaps(Battle_of_Hero.hero[1 - id].position)) {

					Battle_of_Hero.hero[1 - id].hp.lose();
					Battle_of_Hero.hero[1-id].mg.add();
					it.remove();

				}
			}
		}

	}

	void gao(char c) {
		for (int i = 0; i < 4; ++i)
			if (c == Battle_of_Hero.send[i]) {
				position.x += v[i].x * Gdx.graphics.getDeltaTime();
				position.y += v[i].y * Gdx.graphics.getDeltaTime();
				if (i < 2)
					path = i;
				break;
			}
		edgeCheck(position);
		if (c == ' ' && mg.hp > 0) {
			weapons.add(new Weapon(new Rectangle(position.x
					+ (path == 1 ? position.width : -Weapon.width), position.y
					+ position.height / 2, Weapon.width, Weapon.height), path));
			mg.lose();
		}
	}

	void edgeCheck(Rectangle position) {
		position.x = Math.max(0, position.x);
		position.x = Math
				.min(Battle_of_Hero.width - position.width, position.x);
		position.y = Math.max(0, position.y);
		position.y = Math.min(Battle_of_Hero.height - position.height,
				position.y);
	}
}
