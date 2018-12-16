package network;

public class NetworkFacade {

	private static NetworkFacade self;

	private MessageSocket mS;
	private Thread server;
	private boolean isConnected = true;
	private boolean isInitiated = false;

	private NetworkFacade() {
	}

	public void connect(int numOfPlayers) {
		if (!isInitiated) {
			server = new Thread(new Server(numOfPlayers), "Server");
			server.start();
			try {
				mS = new Client("localhost").getMessageSocket();
			} catch (Exception e) {
			}
		}
	}

	public void connect(String IPAddress) {
		if (!isInitiated) {
			try {
				mS = new Client(IPAddress).getMessageSocket();
			} catch (Exception e) {
				isConnected = false;
			}
		}
	}

	public void sendMessageToOthers(String message) {
		mS.sendMessage(message);
	}

	public String receiveMessage() {
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

	public static synchronized NetworkFacade getInstance() {
		if (self == null) {
			self = new NetworkFacade();
		}
		return self;
	}

	public boolean isConnected() {
		return isConnected;
	}
	
	public MessageSocket getMessageSocket() {
		return mS;
	}

}
