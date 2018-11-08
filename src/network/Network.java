package network;

import java.io.IOException;
import java.net.UnknownHostException;

public class Network {

	private MessageSocket mS;

	public Network(int numOfPlayers) {
		Thread server = new Thread(new Server(numOfPlayers));
		server.start();
		try {
			mS = new Client("localhost").getMessageSocket();
		} catch (UnknownHostException e) {
			System.err.println("Socket Connection Error");
		}
	}

	public Network(String IPAddress) throws UnknownHostException {
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
