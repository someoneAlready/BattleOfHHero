package com.me.battleofhero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.*;

public class Battle extends Stage {

	private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private World world = new World(new Vector2(0, -9.8f), true);
	OrthographicCamera camera = new OrthographicCamera(800, 480);

	static Body b[] = new Body[1000000];
	int idx = -1;

	static boolean begin = false;

	public Battle() {
		super(800, 480, true);

		while (begin != true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		PolygonShape box = new PolygonShape();
		box.setAsBox(30, 30);
		addShape(box, new Vector2(-300, 0), BodyType.DynamicBody);
		addShape(box, new Vector2(300, 0), BodyType.DynamicBody);

		EdgeShape edge = new EdgeShape();
		edge.set(new Vector2(0, 0), new Vector2(800, 0));
		addShape(edge, new Vector2(-400, 230), BodyType.StaticBody);
		addShape(edge, new Vector2(-400, -230), BodyType.StaticBody);

	}

	public void addShape(Shape shap, Vector2 pos, BodyType type) {
		BodyDef bd = new BodyDef();
		bd.position.set(pos);
		bd.type = type;
		b[++idx] = world.createBody(bd);
		b[idx].createFixture(shap, 0.001f);
	}

	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
		renderer.render(world, camera.combined);
		super.draw();
	}

	public void dispose() {
		renderer.dispose();
		world.dispose();
		super.dispose();
	}

	public boolean keyDown(int keyCode) {
		try {
			String s = null;
			if (keyCode == Input.Keys.LEFT)
				s = "LEFT";
			if (keyCode == Input.Keys.RIGHT)
				s = "RIGHT";
			if (keyCode == Input.Keys.UP)
				s = "UP";
			if (keyCode == Input.Keys.DOWN)
				s = "DOWN";
			if (keyCode == Input.Keys.SPACE)
				s = "SPACE";
			Battle_of_Hero.out.writeUTF(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.keyDown(keyCode);
	}

	public static void gao(String s) {
		int x = s.charAt(0) - '0';
		System.out.println(s);
		s = s.substring(1);

		if (s.compareTo("LEFT") == 0)
			b[x].applyLinearImpulse(new Vector2(-10000, 0),
					b[x].getLocalCenter(), true);
		if (s.compareTo("RIGHT") == 0)
			b[x].applyLinearImpulse(new Vector2(10000, 0),
					b[x].getLocalCenter(), true);
		if (s.compareTo("UP") == 0)
			b[x].applyLinearImpulse(new Vector2(0, 10000),
					b[x].getLocalCenter(), true);
		if (s.compareTo("DOWN") == 0)
			b[x].applyLinearImpulse(new Vector2(0, -10000),
					b[x].getLocalCenter(), true);

		return;
	}
}