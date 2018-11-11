package network;

public class Network {

	private Network self;
	private MessageSocket mS;
	private Thread server;
	private boolean isConnected = true;

	public Network(int numOfPlayers) {
		server = new Thread(new Server(numOfPlayers), "Server");
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
			isConnected = false;
		}
		self = this;
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
