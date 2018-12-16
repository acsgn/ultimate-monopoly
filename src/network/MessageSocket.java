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

	public void sendMessage(String message) {
		os.println(message);
		os.flush();
	}

	public String receiveMessage() {
		// REQUIRES: Input stream should not be null. 
		// EFFECTS: Receive a message from input stream.
		String message = "MESSAGEERROR";
		try {
			message = is.readLine();
		} catch (IOException e) {
			System.err.println("Message Receiving Error");
		}
		return message;
	}

	public void close() {
		// MODIFIES: Socket and its input and output stream
		// EFFECTS: Closes the socket and so
		// block the further use of the socket
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

	public Socket getSocket() {
		return socket;
	}
	
	public boolean repOk(){
		if(socket ==null || is == null || os == null)
			return false;
		else 
			return true;
	}

}
