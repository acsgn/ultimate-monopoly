package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;

public class Server implements Runnable {

	private static final int DEFAULT_PORT = 302;

	private ArrayList<MessageSocket> players;
	private int numberOfConnections;

	public Server(int numOfPlayers) {
		players = new ArrayList<MessageSocket>(numOfPlayers);
		numberOfConnections = numOfPlayers;
	}

	@Override
	public void run() {
		connectPlayers();
		orderPlayers();
		informPlayers();
		play();
	}

	private void informPlayers() {
		String getName = "SENDNAME";
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			sendMessageToPlayer(getName, i);
			String name = receiveMessageFromPlayer(i);
			names.add(name);
		}
		for (int i = 0; i < players.size(); i++) {
			sendMessageToOtherPlayers("RECEIVENAME/" + names.get(i), i);
		}
		String getColor = "SENDCOLOR";
		ArrayList<String> colors = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			sendMessageToPlayer(getColor, i);
			String color = receiveMessageFromPlayer(i);
			colors.add(color);
		}
		for (int i = 0; i < players.size(); i++) {
			sendMessageToOtherPlayers(names.get(i) + "/RECEIVECOLOR/" + colors.get(i), i);
		}
		String done = "ALLDONE";
		for (int i = 0; i < players.size(); i++) {
			sendMessageToPlayer(done, i);
		}
	}

	private void play() {
		int currentPlayer = 0;
		while (true) {
			sendMessageToPlayer("PLAY", currentPlayer);
			String message;
			while (true) {
				message = receiveMessageFromPlayer(currentPlayer);
				if (message.equals("ENDTURN"))
					break;
				sendMessageToOtherPlayers(message, currentPlayer);
				if (message.split("/")[1].compareTo("ENDGAME") == 0) {
					players.remove(currentPlayer);
					currentPlayer = currentPlayer == players.size() + 1 ? 0 : currentPlayer;
					break;
				}
			}
			if (players.isEmpty())
				break;
			currentPlayer = (currentPlayer + 1) % players.size();
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
				return Integer.compare(mS2.diceValue, mS1.diceValue);
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
				if (i == numberOfConnections)
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
			sendMessageToPlayer(message, i);
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
