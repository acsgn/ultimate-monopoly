package network;

public class NetworkFacade {

	private static NetworkFacade self;

	// To learn your ip InetAddress.getLocalHost().getHostAddress();
	
	private MessageSocket mS;
	private Thread server;
	private String[] IPAddresses;
	private  P2PServer p2p;
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
		for(int i= 0; i<IPAddresses.length;i++) {
			try {
				mS = new Client(IPAddresses[i]).getMessageSocket();
				mS.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String receiveMessage() throws InterruptedException {
		wait();
		return p2p.receiveMessage();
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

}
