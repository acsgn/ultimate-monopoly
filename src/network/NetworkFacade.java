package network;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkFacade implements Runnable {

	private static NetworkFacade self;
	private static final int checkRate = 5000;

	private ConcurrentLinkedQueue<String> IPAddresses;
	private Discovery discovery;
	private P2PServer p2p;
	private boolean isDiscovering = true;
	private volatile boolean destroy = false;
	private volatile boolean isChecking = true;
	private volatile boolean someoneDisconnected = false;
	private String disconnectedMesssage = "DISCONNECTED";
	private String connectivityCheckMessage = "CONNECTIVITYCHECK";
	private String playerCountMessage = "CONNECTIVITYPLAYERCOUNT/";
	private String playerCheckMessage = "PLAYERCHECK";

	private NetworkFacade() {
		IPAddresses = new ConcurrentLinkedQueue<String>();
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
		for (String IP : discovery.getIPAddresses()) {
			System.out.println(IP);
			IPAddresses.add(IP);
		}
		new Thread(this, "Connection Control").start();
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
			if (IPAddresses.size() == 1) {
				sendMessage(disconnectedMesssage);
			} else
				someoneDisconnected = true;
		}
	}

	public String receiveMessage() {
		if (isDiscovering) {
			return discovery.getNumberOfPlayers();
		} else {
			String message = p2p.receiveMessage();
			if (message.equals(connectivityCheckMessage))
				isChecking = false;
			return message;
		}
	}

	public void endGame() {
		destroy = true;
		try {
			P2PClient c = new P2PClient("localhost");
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

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(checkRate);
				if (destroy)
					break;
				if (isChecking) {
					sendMessage(connectivityCheckMessage);
					if (someoneDisconnected) {
						sendMessage(playerCountMessage + IPAddresses.size());
						sendMessage(playerCheckMessage);
						someoneDisconnected = false;
					}
				}
				isChecking = true;
			} catch (InterruptedException e) {
			}
		}
	}
}
