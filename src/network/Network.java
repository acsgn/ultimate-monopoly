package network;

import java.io.IOException;

public class Network {

	private Network self;
	private MessageSocket mS;
	private Thread server;

	public Network(int numOfPlayers) {
		server = new Thread(new Server(numOfPlayers));
		server.start();
		try {
			mS = new Client("localhost").getMessageSocket();
		} catch (Exception e) {
		}
		self = this;
	}

	public Network(String IPAddress) {
		try {
			mS = new Client(IPAddress).getMessageSocket();
		} catch (Exception e) {
		}
		self = this;
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

}
