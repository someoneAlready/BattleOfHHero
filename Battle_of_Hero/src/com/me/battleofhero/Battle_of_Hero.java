package com.me.battleofhero;

import java.net.*;
import java.util.*;
import java.io.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class Battle_of_Hero implements ApplicationListener {
	public void resize(int width, int height) {}		
	public void pause() {}
	public void resume() {}
	public void dispose() {}
	
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture heroImage[];
	Texture fireImage[] = new Texture[2] ;
	Texture firexImage = null;
	
	Rectangle hero[]= new Rectangle[2];
	Integer rect[] = new Integer[2];
	
	int[] hp = new int[2];
	
	
	
	Rectangle fire;
	long updatetime, delta = 100000000;
	
	Array<Array<Rectangle> > fires = new Array<Array<Rectangle>>(); 
	Array<Array<Integer>> fireRect = new Array<Array<Integer>>();
	
	Socket socket;
	DataInputStream in = null;
	DataOutputStream out = null;
	
	Sound hpSound;

	public void create(){
		hpSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		
		heroImage = new Texture[2];
		
		heroImage[0] = new Texture(Gdx.files.internal("hero.png"));
		heroImage[1] = new Texture(Gdx.files.internal("hero1.png"));
		
		firexImage = new Texture(Gdx.files.internal("firex.png"));
		fireImage[0] = new Texture(Gdx.files.internal("fire.png"));
		fireImage[1] = new Texture(Gdx.files.internal("fire1.png"));
		
		hero[0] = new Rectangle(0, 0, 128, 256);
		rect[0] = 0;
		
		hp[0] = hp[1] = 20;
		
		hero[1]= new Rectangle(800-128, 0, 128, 256);
		rect[1] = 1;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		for (int i=0; i<2; ++i){
			fires.add(new Array<Rectangle>());
			fireRect.add(new Array<Integer>());
		}
		
		updatetime = TimeUtils.nanoTime();
		
		try{
			socket = new Socket("localhost", 12306);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(IOException e){}
		if (socket == null) System.out.println("FT");
		
		Update_thread t = new Update_thread(in);
        t.start();
	}
	
	@SuppressWarnings("unchecked")
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Iterator<Array<Rectangle>> itFires = fires.iterator();
		Iterator<Array<Integer>> itRects = fireRect.iterator();
		
		batch.begin();
			for (int i=0; i<2; ++i){
				
				if (itFires.hasNext()){
					Iterator<Rectangle> itFire = itFires.next().iterator();
					Iterator<Integer> itRect = itRects.next().iterator();
					while (itFire.hasNext()){
						Rectangle fire = itFire.next();
						Integer rect = itRect.next();
						batch.draw(firexImage, fire.x, fire.y);
					}
				}
			}
			for (int i=0; i<2; ++i) batch.draw(heroImage[rect[i]], hero[i].x, hero[i].y);
		batch.end();

		
		itFires = fires.iterator();
        itRects = fireRect.iterator();
        
		for (int i=0; i<2; ++i){
			
			if (itFires.hasNext()){
				Iterator<Rectangle> itFire = itFires.next().iterator();
				Iterator<Integer> itRect = itRects.next().iterator();
				while (itFire.hasNext()){
					Rectangle fire = itFire.next();
					Integer rect = itRect.next();
					fire.x += (rect==0?1:-1)*300 * Gdx.graphics.getDeltaTime();
					
					if (fire.x<0 || fire.x>800-fire.width){
						itFire.remove();
						itRect.remove();
					}
					else if (fire.overlaps(hero[1-i])){
						hp[1-i]--;
						itFire.remove();
						itRect.remove();
						hpSound.play();
					}
				}
			}
		}		
	
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			try {
				out.writeUTF("L");
			} catch (IOException e) {}
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			try{
				out.writeUTF("R");
			}catch (IOException e){}
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			try{
				out.writeUTF(" ");
			}catch (IOException e){}
		}		
	}	
	
	

class Update_thread extends Thread {
    @SuppressWarnings("unchecked")
	public void run() {
		Iterator<Array<Rectangle>> itFires = fires.iterator();
        Iterator<Array<Integer>> itRects = fireRect.iterator();    
        Array<Rectangle> itFire0, itFire1;
        Array<Integer> itRect0, itRect1;

        
        itFire0 = itFires.next();
        itFire1 = itFires.next();
        
        itRect0 = itRects.next();
        itRect1 = itRects.next();
        
        while (true) {
        	try{
        		String s = in.readUTF();
        		int role;
        		
        		if (s.charAt(0)=='0') role = 0;
        		else role = 1;
        		
    			String t = s.substring(1);
    			
    			if (t.charAt(0)=='L'){
    				hero[role].x -= 200 * Gdx.graphics.getDeltaTime();
    				rect[role] = 1;
    			}
    			
    			if (t.charAt(0)=='R'){
    				hero[role].x += 200 * Gdx.graphics.getDeltaTime();
    				rect[role] = 0;
    			}
    			
    			if (t.charAt(0)==' '){
    				Rectangle fire = new Rectangle( hero[role].x+(rect[role]==0?hero[role].width:-64) ,hero[role].y + hero[role].height/2, 64, 64);
    				Integer fireRect = rect[role];
    				if (role==0){
    					itFire0.add(fire);
    					itRect0.add(fireRect);
    				}
    				else{
    					itFire1.add(fire);
    					itRect1.add(fireRect);
    				}
    			}

        		if (hero[role].x<0) hero[role].x=0;
        		if (hero[role].x>800-hero[role].width) hero[role].x=800-hero[role].width;
        		
        		
        	}catch(IOException e){}
        }
    }
    
	DataInputStream in = null;
    Update_thread(DataInputStream in) {
    	this.in= in;
    }
    
}

	
}

