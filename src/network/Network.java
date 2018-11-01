package network;

import java.io.IOException;

public class Network {

	private MessageSocket mS;

	public Network(int numOfPlayers) {
		Thread server = new Thread(new Server(numOfPlayers));
		server.start();
		mS = new Client("localhost").getMessageSocket();
	}

	public Network(String IPAddress) {
		mS = new Client(IPAddress).getMessageSocket();
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
	}

}
