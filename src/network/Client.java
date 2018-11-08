package network;

import java.net.Socket;

public class Client {

	private static final int DEFAULT_PORT = 302;

	private Socket s;

	public Client(String IPAddress) throws Exception {
		s = new Socket(IPAddress, DEFAULT_PORT);
	}

	protected MessageSocket getMessageSocket() {
		return new MessageSocket(s);
	}

}
