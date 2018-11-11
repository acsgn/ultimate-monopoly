package network;

public class Network {

	private Network self;
	private MessageSocket mS;
	private Thread server;
	private boolean isConnected = false;

	public Network(int numOfPlayers) {
		server = new Thread(new Server(numOfPlayers), "Server");
		server.start();
		mS = new Client("localhost").getMessageSocket();
		isConnected = true;
		self = this;
	}

	public Network(String IPAddress) {
		mS = new Client(IPAddress).getMessageSocket();
		self = this;
		isConnected = true;
	}

	public void sendMessageToOthers(String message) {
		mS.sendMessage(message);
	}

	public String receiveMessageFromOtherPlayer() {
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
