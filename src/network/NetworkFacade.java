package network;

import java.util.ArrayList;

public class NetworkFacade {

	private static NetworkFacade self;

	private String message;

	private ArrayList<String> IPAddresses;
	private Thread discoveryThread;
	private Discovery discovery;
	private P2PServer p2p;
	private boolean isDiscovering = true;

	private NetworkFacade() {
	}

	public void startNetwork() {
		discovery = new Discovery();
		discoveryThread = new Thread(discovery, "Discovery");
		discoveryThread.start();
		p2p = new P2PServer();
		new Thread(p2p, "P2P Server").start();
	}

	public void startGame() {
		discovery.destroy();
		discoveryThread.interrupt();
		IPAddresses = discovery.getIPAddresses();
		isDiscovering = false;
	}

	public void sendMessageToOthers(String message) {
		for (String IP : IPAddresses) {
			try {
				MessageSocket mS = new P2PClient(IP).getMessageSocket();
				mS.sendMessage(message);
				mS.close();
			} catch (Exception e) {
			}
		}
	}

	public String receiveMessage() {
		if (isDiscovering && discovery != null) {
			return discovery.getNumberOfPlayers();
		} else if (!isDiscovering && p2p != null) {
			return p2p.receiveMessage();
		}
		return "";
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
