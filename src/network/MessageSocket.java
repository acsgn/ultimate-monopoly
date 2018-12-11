package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSocket {

	private final String messageCheck = "UMWH"; // Ult. Mon. Wat. Hat.
	private final String confirmation = "ACK";

	private Socket socket;
	private BufferedReader is;
	private PrintWriter os;

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
		os.println(messageCheck + message);
		os.flush();
		String response = receiveConfirmation();
		while (!response.equals(confirmation)) {
			os.println(messageCheck + message);
			os.flush();
		}
	}

	protected String receiveMessage() {
		String message = "MESSAGEERROR";
		try {
			message = is.readLine();
			if (message.startsWith(messageCheck))
				sendConfirmation();
			message = message.substring(messageCheck.length());
		} catch (IOException e) {
			System.err.println("Message Receiving Error");
		}
		return message;
	}

	private void sendConfirmation() {
		os.println(confirmation);
		os.flush();
	}

	private String receiveConfirmation() {
		String message = "";
		try {
			message = is.readLine();
		} catch (IOException e) {
		}
		return message;
	}

	protected void close() {
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			System.err.println("Socket Close Error");
		}
	}

}
