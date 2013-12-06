package com.me.battleofhero;

import java.io.*;
import java.net.*;
import java.util.*;

class Update_thread extends Thread {
	DataInputStream in = null;

	Update_thread(DataInputStream in) {
		this.in = in;
	}

	public void run() {
		while (true) {
			try {
				String s = in.readUTF();
				Battle.gao(s);
			} catch (IOException e) {
			}
		}
	}

}
