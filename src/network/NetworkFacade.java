package network;

import java.util.ArrayList;

public class NetworkFacade {

	private static NetworkFacade self;

	private String message;

	private ArrayList<String> IPAddresses;
	private Discovery discovery;
	private P2PServer p2p;
	private boolean isDiscovering = true;

	private NetworkFacade() {
	}

	public void startNetwork() {
		discovery = new Discovery();
		new Thread(discovery, "Discovery").start();
		p2p = new P2PServer();
		new Thread(p2p, "P2P Server").start();
	}

	public void startGame() {
		discovery.destroy();
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
		long time = System.currentTimeMillis();

		if (isDiscovering && discovery != null) {

			Thread tmp = new Thread(new Runnable() {
				@Override
				public void run() {
					String tmp = null;
					tmp = discovery.getNumberOfPlayers();
					while (tmp == null)
						tmp = discovery.getNumberOfPlayers();

					message = tmp;
				}
			}, "Temporary");
			tmp.start();

			while (true) {
				if (!tmp.isAlive())
					break;
				else if (System.currentTimeMillis() - time >= 2000) {
					System.out.println("Cannot get a response in 2 seconds");
					tmp.interrupt();
					break;
				}
			}
			if (message != null)
				return message;
			return "";
		}else if(!isDiscovering && p2p != null) {
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
