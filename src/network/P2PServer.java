package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class P2PServer implements Runnable {

	private static final int P2P_PORT = 3022;

	private Object saveFile;
	private ArrayList<String> messages;

	public P2PServer() {
		messages = new ArrayList<String>();
	}

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(P2P_PORT);
			while (true) {
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				String message = mS.receiveMessage();
				if (message.equals("CLOSE")) {
					mS.close();
					break;
				}
				messages.add(message);
				synchronized (this) {
					notify();
				}
				mS.close();
				if (message.equals("LOAD"))
					receiveFile(server.accept());
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	private void receiveFile(Socket s) {
		try {
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			saveFile = ois.readObject();
			ois.close();
			s.close();
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
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

	public Object getSaveFile() {
		if (saveFile == null) {
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
			}
		}
		return saveFile;
	}

}
