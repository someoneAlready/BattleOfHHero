package com.me.battleofhero;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.*;

public class Battle_of_Hero extends Game {
	static Battle battle = null;

	String server = "localhost";
	static Socket socket = null;
	static DataInputStream in = null;
	static DataOutputStream out = null;
	
	@Override
	public void create() {
		try {
			socket = new Socket(server, 12306);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			Update_thread t = new Update_thread(in);
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		battle = new Battle();
		Gdx.input.setInputProcessor(battle);		
	}


	@Override
	public void render() {
		battle.draw();
	}

	@Override
	public void dispose() {
		battle.dispose();
		super.dispose();
	}
}