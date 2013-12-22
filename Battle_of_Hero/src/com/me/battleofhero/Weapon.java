package com.me.battleofhero;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public class Weapon {
	static final int width = 64;
	static final int height = 64;
	static Texture Image;

	Rectangle position;
	int path;

	static void create(){
		//Image = new Texture(Gdx.files.internal("firex.png"));
	}
	Weapon(Rectangle position, int path) {
		this.position = position;
		this.path = path;
	}

	void draw(SpriteBatch batch) {
		batch.draw(Battle_of_Hero.Image, position.x, position.y);
	}
	
	void move(){
		position.x += (path==0?-1:1) * 500 * Gdx.graphics.getDeltaTime();
	}
}
