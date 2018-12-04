package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer implements Runnable {

	private static final int DEFAULT_PORT = 302;

	private String message;
	private boolean destroy = false;

	@Override
	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(DEFAULT_PORT);
			while (true) {
				synchronized (this) {
					if (destroy)
						break;
				}
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				message = mS.receiveMessage();
				synchronized (NetworkFacade.getInstance()) {
					NetworkFacade.getInstance().notify();
				}
				mS.close();
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	public String receiveMessage() {
		return message;
	}

	public void destroy() {
		destroy = true;
	}

}
