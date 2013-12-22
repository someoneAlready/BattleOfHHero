package com.me.battleofhero;

import java.io.*;

public class ListenningThread extends Thread {
	DataInputStream in = null;

	ListenningThread(DataInputStream in) {
		this.in = in;
	}

	public void run() {
		while (true) {
			String s;
			try {
				s = in.readUTF();
				if (s.substring(1).compareTo("begin")==0){
					Battle_of_Hero.id2 = s.charAt(0)-'0';
				}
				else
					Battle_of_Hero.gao(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}