package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	private static final int DEFAULT_PORT = 302;
	
	private MessageSocket[] players;

	public Server(int numOfPlayers){
		players = new MessageSocket[numOfPlayers];
	}

	@Override
	public void run() {
		connectPlayers();
		// Here will go the protocol for messages
	}

	private void connectPlayers() {
		ServerSocket server;
		try {
			server = new ServerSocket(DEFAULT_PORT);
			for (int i = 0; i < players.length; i++) {
				Socket s = server.accept();
				players[i] = new MessageSocket(s);
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server setup error");
		}		
	}

	private void sendMessageToOthers(String message) {
		for(MessageSocket mS: players) {
			mS.sendMessage(message);
		}
	}

	private String receiveMessageFromOtherPlayer(int turnNumber) throws IOException {
		return  players[turnNumber].receiveMessage();
	}

}
