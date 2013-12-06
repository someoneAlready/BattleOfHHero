package com.me.battleofhero;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new Battle_of_Hero(), "Battle of Hero", 800, 480,
				true);
	}
}
