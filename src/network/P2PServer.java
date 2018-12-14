package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class P2PServer implements Runnable {

	private static final int P2P_PORT = 3022;

	private ArrayList<String> messages;

	public P2PServer() {
		messages = new ArrayList<String>();
	}

	@Override
	public void run() {
		boolean destroy = false;
		try {
			ServerSocket server = new ServerSocket(P2P_PORT);
			while (true) {
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				String message = mS.receiveMessage();
				if(message.equals("CLOSE"))
					destroy = true;
				messages.add(message);
				synchronized (this) {
					notify();
				}
				mS.close();
				if(destroy)
					break;
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	public String receiveMessage() {
		if (messages.isEmpty())
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
			}
		return messages.remove(0);
	}

}
