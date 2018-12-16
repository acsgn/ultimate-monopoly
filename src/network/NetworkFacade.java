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
			isInitiated = true;
		}
	}

	public void connect(String IPAddress) {
		// REQUIRES: IPAddress of server player
		// MODIFIES: isInitiated and mS are changed
		if (!isInitiated) {
			try {
				mS = new Client(IPAddress).getMessageSocket();
			} catch (Exception e) {
				isConnected = false;
			}
			isInitiated = true;
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
		// EFFECTS: If the system connected to a server
		// it returns true, otherwise false.
		return isConnected;
	}
	
	public MessageSocket getMessageSocket() {
		return mS;
	}
	
	public boolean repOk(){
		if(mS ==null || isInitiated == false)
			return false;
		else 
			return true;
	}

}
