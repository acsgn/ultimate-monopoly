package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;

public class Server implements Runnable {

	private static final int DEFAULT_PORT = 302;

	private ArrayList<MessageSocket> players;

	public Server(int numOfPlayers) {
		players = new ArrayList<MessageSocket>(numOfPlayers);
	}

	@Override
	public void run() {
		connectPlayers();
		orderPlayers();
		play();
	}

	private void play() {
		int currentPlayer = 0;
		while (true) {
			sendMessageToPlayer("PLAY", currentPlayer);
			String message = receiveMessageFromPlayer(currentPlayer);
			sendMessageToOtherPlayers(message, currentPlayer);
			if (message.compareTo("CLOSE") == 0) {
				players.remove(currentPlayer);
				currentPlayer = currentPlayer == players.size()+1 ? 0 : currentPlayer;
				continue;
			}
			currentPlayer++;
			currentPlayer = currentPlayer == players.size() ? 0 : currentPlayer;
		}
	}

	private void orderPlayers() {
		String getDice = "SENDDICE";
		for (int i = 0; i < players.size(); i++) {
			sendMessageToPlayer(getDice, i);
			int diceValue = Integer.parseInt(receiveMessageFromPlayer(i));
			players.get(i).diceValue = diceValue;
		}
		players.sort(new Comparator<MessageSocket>() {
			@Override
			public int compare(MessageSocket mS1, MessageSocket mS2) {
				return Integer.compare(mS1.diceValue, mS2.diceValue);
			}
		});
	}

	private void connectPlayers() {
		ServerSocket server;
		try {
			server = new ServerSocket(DEFAULT_PORT);
			int i = 0;
			while (true) {
				Socket s = server.accept();
				MessageSocket mS = new MessageSocket(s);
				players.add(mS);
				i++;
				if (i == players.size())
					break;
			}
			server.close();
		} catch (IOException e) {
			System.err.println("Server Setup Error");
		}
	}

	private void sendMessageToOtherPlayers(String message, int index) {
		for (int i = 0; i < players.size(); i++) {
			if (i == index)
				continue;
			sendMessageToPlayer(message, index);
		}
	}

	private void sendMessageToPlayer(String message, int index) {
		players.get(index).sendMessage(message);
	}

	private String receiveMessageFromPlayer(int index) {
		String message = "";
		message = players.get(index).receiveMessage();
		return message;
	}

}
