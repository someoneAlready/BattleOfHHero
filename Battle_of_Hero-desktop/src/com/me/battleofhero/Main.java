package com.me.battleofhero;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Battle_of_Hero";
		cfg.width = 1200;
		cfg.height = 600;

		new LwjglApplication(new Battle_of_Hero(), cfg);
	}
}
