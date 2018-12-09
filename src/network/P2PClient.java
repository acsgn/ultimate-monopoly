package network;

import java.net.Socket;

public class P2PClient {

	private static final int P2P_PORT = 3022;

	private Socket s;

	public P2PClient(String IPAddress) throws Exception {
		s = new Socket(IPAddress, P2P_PORT); 
	}

	protected MessageSocket getMessageSocket() {
		return new MessageSocket(s);
	}

}
