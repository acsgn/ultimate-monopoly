package network;

public class NetworkFacade {

	private static NetworkFacade self;

	// To learn your ip InetAddress.getLocalHost().getHostAddress();

	private MessageSocket mS;
	private String[] IPAddresses = { "172.20.98.75" };
	private P2PServer p2p;

	private NetworkFacade() {
	}

	public void start() {
		p2p = new P2PServer();
		new Thread(p2p, "P2p Server").start();
	}

	public void sendMessageToOthers(String message) {
		for (int i = 0; i < IPAddresses.length; i++) {
			try {
				mS = new Client(IPAddresses[i]).getMessageSocket();
				mS.sendMessage(message);
				mS.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String receiveMessage() {
		try {
			wait();
			return p2p.receiveMessage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "NOMESSAGE";
	}

	public void disconnect() {
		sendMessageToOthers("CLOSE");
		p2p.destroy();
	}

	public static synchronized NetworkFacade getInstance() {
		if (self == null) {
			self = new NetworkFacade();
		}
		return self;
	}

}
