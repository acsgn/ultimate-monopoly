package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer implements Runnable {

	private static final int P2P_PORT = 3022;

	private String message;
	private boolean destroy = false;

	@Override
	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(P2P_PORT);
			while (true) {
				synchronized (this) {
					if (destroy)
						break;
				}
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				message = mS.receiveMessage();
				notify();
				mS.close();
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	public String receiveMessage() {
		try {
			wait();
		} catch (InterruptedException e) {
		}
		return message;
	}

	public void destroy() {
		destroy = true;
	}

}
