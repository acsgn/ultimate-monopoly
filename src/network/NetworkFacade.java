package network;

import java.util.ArrayList;

public class NetworkFacade {

	private static NetworkFacade self;

	private ArrayList<String> IPAddresses;
	private Discovery discovery;
	private P2PServer p2p;
	private boolean isDiscovering = true;
	private volatile boolean isChecking = false;
	private String disconnectedMesssage = "DISCONNECTED";
	private String connectivityCheckMessage = "CONNECTIVITYCHECK";
	private String playerCountMessage = "PLAYERCOUNT/";

	private NetworkFacade() {
	}

	public void startNetwork() {
		discovery = new Discovery();
		new Thread(discovery, "Discovery").start();
		p2p = new P2PServer();
		new Thread(p2p, "P2P Server").start();
	}

	public void startGame() {
		isDiscovering = false;
		discovery.destroy();
		IPAddresses = discovery.getIPAddresses();
	}

	public void sendMessage(String message) {
		ArrayList<String> closedIPs = new ArrayList<String>();
		for (String IP : IPAddresses) {
			try {
				P2PClient c = new P2PClient(IP);
				MessageSocket mS = c.getMessageSocket();
				mS.sendMessage(message);
				mS.close();
			} catch (Exception e) {
				closedIPs.add(IP);
			}
		}
		if (!closedIPs.isEmpty()) {
			IPAddresses.removeAll(closedIPs);
			if (IPAddresses.size() == 1)
				sendMessage(disconnectedMesssage);
			else if (!isChecking) {
				sendMessage(playerCountMessage + IPAddresses.size());
				if (!isChecking) { // to prevent recursive recalls to send connectivity message
					sendMessage(connectivityCheckMessage);
					isChecking = true;
				}
			}
		}
		isChecking = false;
	}

	public String receiveMessage() {
		if (isDiscovering) {
			return discovery.getNumberOfPlayers();
		} else {
			String message = p2p.receiveMessage();
			if (message.equals(connectivityCheckMessage))
				isChecking = true;
			return message;
		}
	}

	public void endGame() {
		try {
			P2PClient c = new P2PClient(IPAddresses.get(0));
			MessageSocket mS = c.getMessageSocket();
			mS.sendMessage("CLOSE");
			mS.close();
		} catch (Exception e) {
		}
	}

	public static synchronized NetworkFacade getInstance() {
		if (self == null) {
			self = new NetworkFacade();
		}
		return self;
	}

}
