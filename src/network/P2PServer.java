package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer implements Runnable {

	private static final int DEFAULT_PORT = 302;
	
	private String message;

	@Override
	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(DEFAULT_PORT);
			while (true) {
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				message = mS.receiveMessage();
				NetworkFacade.getInstance().notify();
				mS.close();
			}
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	public String receiveMessage() {
		return message;
	}
	
}
