package network;

import java.net.InetSocketAddress;
import java.net.Socket;

public class P2PClient {

	private final int P2P_PORT = 3022;
	private final int timeout = 3000;

	private Socket s;

	public P2PClient(String IPAddress) throws Exception {
		s = new Socket();
		InetSocketAddress sA = new InetSocketAddress(IPAddress, P2P_PORT);
		s.connect(sA, timeout);
	}

	protected Socket getSocket() {
		return s;
	}

}
