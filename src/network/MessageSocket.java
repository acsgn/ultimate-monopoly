package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSocket {

	private Socket socket;
	private BufferedReader is;
	private PrintWriter os;
	protected int diceValue = 0;

	public MessageSocket(Socket s) {
		this.socket = s;
		try {
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Socket I/O Error");
		}
	}

	protected void sendMessage(String message) {
		os.println(message);
		os.flush();
	}

	protected String receiveMessage() throws IOException {
		String message;
		message = is.readLine();
		return message;
	}

	protected void close() {
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (socket != null) {
				socket.close();
				System.out.println("Socket Closed");
			}
		} catch (IOException e) {
			System.err.println("Socket Close Error");
		}
	}

}
