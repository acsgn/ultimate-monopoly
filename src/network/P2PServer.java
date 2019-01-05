package network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
				if(message.equals("LOAD")) 
					receiveFile(s);
				messages.add(message);
				synchronized (this) {
					notify();
				}
				mS.close();
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	private void receiveFile(Socket s) {
		try {
			int bytesRead;
			long current = 0;
			InputStream is = s.getInputStream();
			while () {
				is.re
				bytesRead = is.read(buffer);
				fos.write(buffer, 0,bytesRead);
				current += bytesRead;
			}
			fos.close();
		} catch (IOException e) {
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
