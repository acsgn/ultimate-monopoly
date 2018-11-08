package network;

import java.io.IOException;

public class Network {

	private Network self;
	private MessageSocket mS;
	private Thread server;
	private boolean isConnected = false;

	public Network(int numOfPlayers) {
		server = new Thread(new Server(numOfPlayers),"Server");
		server.start();
		try {
			mS = new Client("localhost").getMessageSocket();
			isConnected = true;
		} catch (Exception e) {
		}
		self = this;
	}

	public Network(String IPAddress) {
		try {
			mS = new Client(IPAddress).getMessageSocket();
			self = this;
			isConnected = true;
		} catch (Exception e) {
		}
	}

	public void sendMessageToOthers(String message) {
		mS.sendMessage(message);
	}

	public String receiveMessageFromOtherPlayer() throws IOException {
		return mS.receiveMessage();
	}

	public void disconnect() {
		mS.sendMessage("CLOSE");
		mS.close();
		if (server != null)
			try {
				server.join();
			} catch (InterruptedException e) {
				System.err.println("Server Close Error");
			}
	}

	public Network getInstance() {
		return self;
	}

	public boolean isConnected() {
		return isConnected;
	}

}
