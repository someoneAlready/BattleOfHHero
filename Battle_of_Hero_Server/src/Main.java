import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
	public static void main(String args[]) {
		ServerSocket server = null;
		Socket s0 = null, s1 = null;
		while (true) {
			try {
				server = new ServerSocket(12306);
			} catch (IOException e1) {
			}

			try {
				if (server == null)
					System.out.println("FT");
				s1 = server.accept();
				InetAddress adr = s1.getInetAddress();
				System.out.println(adr);
			} catch (IOException e) {
			}

			if (s1 != null) {
				if (s0 != null) {
					System.out.println("Fuck up, we connected two client");
					ServerThread t0 = new ServerThread(s0, '0');
					ServerThread t1 = new ServerThread(s1, '1');
					t0.gao(t1);
					t1.gao(t0);
					t0.start();
					t1.start();
					s0 = null;

				} else
					s0 = s1;
				s1 = null;
			}

		}
	}
}

class ServerThread extends Thread {
	Socket s = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	ServerThread st = null;
	char x;

	ServerThread(Socket s, char x) {
		try {
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
		}
		this.x = x;
	}

	void gao(ServerThread st) {
		this.st = st;
	}

	void write(String s) {
		try {
			out.writeUTF(s);
		} catch (IOException e) {
		}
	}

	public void run() {
		while (true) {
			try {
				String s = in.readUTF();
				write(x + s);
				st.write(x + s);
			} catch (IOException e) {
				break;
			}
		}
	}
}
