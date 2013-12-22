package com.me.battleofhero;

import java.net.*;
import java.io.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public class Battle_of_Hero implements ApplicationListener {
	static final int width = 1200;
	static final int height = 600;

	static final int key[] = { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN,
			Keys.SPACE };
	static final char send[] = { 'L', 'R', 'U', 'D', ' ' };

	OrthographicCamera camera;
	SpriteBatch batch;

	static String server = "acm.zzu.edu.cn";
	Socket socket = null;
	DataInputStream in = null;
	DataOutputStream out = null;

	static Hero hero[] = new Hero[2];
	static Texture Image;
	static int  id2;
	String id;
	
	Battle_of_Hero(String id){
		this.id = id;
	}
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		batch = new SpriteBatch();

		try {
			socket = new Socket(server, 12306);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			ListenningThread t = new ListenningThread(in);
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		hero[0] = new Hero("hero1.png", "hero.png", new Rectangle(0, 0, 128,
				256), 1, 0);
		hero[1] = new Hero("hero1.png", "hero.png", new Rectangle(width - 128,
				0, 128, 256), 0, 1);

		Image = new Texture(Gdx.files.internal("firex.png"));
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		for (int i = 0; i < 2; ++i)
			hero[i].draw(batch);
		batch.end();

		for (int i = 0; i < 2; ++i)
			hero[i].move();

		for (int i = 0; i < key.length; ++i)
			if (Gdx.input.isKeyPressed(key[i]))
				try {
					out.writeUTF("" + send[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}

		if (hero[0].hp.hp == 0 || hero[1].hp.hp == 0) {
			int xx;
			if (hero[0].hp.hp ==0) xx=0;
			else xx=1;
			if (xx==id2)
				new Database(id, -100);
			else
				new Database(id, 100);
			Gdx.app.exit();
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	static void gao(String s) {
		int x;
		switch (s.charAt(0)) {
		case '0':
			x = 0;
			break;
		case '1':
			x = 1;
			break;
		default:
			return;
		}
		hero[x].gao(s.charAt(1));
	}
}