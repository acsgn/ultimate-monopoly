package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private static final int DEFAULT_PORT = 302;
	
	private Socket s;

	public Client(String IPAddress) throws UnknownHostException {
			try {
				s = new Socket(IPAddress, DEFAULT_PORT);
			} catch (IOException e) {
				System.err.println("Connection I/O Error");
			}
	}
	
	protected MessageSocket getMessageSocket() {
		return new MessageSocket(s);
	}
	
}
