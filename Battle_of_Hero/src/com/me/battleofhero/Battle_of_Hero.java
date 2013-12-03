package com.me.battleofhero;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class Battle_of_Hero implements ApplicationListener {

	OrthographicCamera camera;
	SpriteBatch batch;
	Texture heroImage[];
	Texture fireImage;
	
	Rectangle hero;
	Integer rect;
	
	Rectangle fire;
	long updatetime;
	
	Array<Rectangle> fires;
	Array<Integer> fireRect;
	
	
	public void create(){
		heroImage = new Texture[2];
		heroImage[0] = new Texture(Gdx.files.internal("hero.png"));
		heroImage[1] = new Texture(Gdx.files.internal("hero1.png"));
		
		fireImage = new Texture(Gdx.files.internal("fire.png"));
		
		hero = new Rectangle(0, 0, 128, 256);
		rect = 0;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		fires = new Array<Rectangle>();
		fireRect = new Array<Integer>();
		
		updatetime = TimeUtils.nanoTime();
	}
	


	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			batch.draw(heroImage[rect], hero.x, hero.y);
			for(Rectangle fire: fires){
				batch.draw(fireImage, fire.x, fire.y);
			}
		batch.end();
		
	
			Iterator<Rectangle> iter = fires.iterator();
			Iterator<Integer> iter2 = fireRect.iterator();
			while (iter.hasNext()){
				Rectangle fire = iter.next();
				Integer rect = iter2.next();
				fire.x += (rect==0?1:-1) * 300 * Gdx.graphics.getDeltaTime();
				
			}
			
		
		
	if (TimeUtils.nanoTime() - updatetime > 200000000){
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			fire = new Rectangle(hero.x+(rect==0?hero.width:-64), hero.y+hero.height/2f, 64, 64);
			fireRect.add(rect);
			fires.add(fire);
			updatetime = TimeUtils.nanoTime();
		}
		
	}
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			hero.x -= 200 * Gdx.graphics.getDeltaTime();
			rect = 1;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			hero.x += 200 * Gdx.graphics.getDeltaTime();
			rect = 0;
		}		
		if (hero.x < 0) hero.x = 0;
		if (hero.x > 800 - hero.width) hero.x = 800-hero.width;
	}



	public void resize(int width, int height) {
		
	}

	public void pause() {

	}


	public void resume() {
		
	}

	public void dispose() {

	}
}


