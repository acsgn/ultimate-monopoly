package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import game.card.ActionCards;
import game.card.Card;
import network.NetworkFacade;
import ui.UIFacade;

public class MonopolyGame implements Runnable {
	private boolean destroy = false;
	private volatile ArrayList<Player> players;
	private volatile Player currentPlayer;
	private volatile Player myPlayer;
	private int numOfDiceReceived = 0;
	private int order;
	private boolean isNewGame;

	public MonopolyGame() {
		players = new ArrayList<>();
		myPlayer = new Player();
		currentPlayer = myPlayer;
		players.add(currentPlayer);
		isNewGame = true;
		NetworkFacade.getInstance().start();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void executeNetworkMessage(String[] parsed) {
		if (parsed[0].equals("RECEIVENAME")) {
			if (isNewGame) {
				Player newPlayer = new Player();
				newPlayer.setName(parsed[1]);
				players.add(newPlayer);
				if (players.size() == 2) {
					int[] dice = currentPlayer.rollDice();
					NetworkFacade.getInstance()
							.sendMessageToOthers(myPlayer.getName() + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				}
			} else {
				// This is the current player order in playing.
				order = players.size() - 1;
			}
			return;
		}
		updateCurrentPlayer(parsed[0]);
		switch (parsed[1]) {
		case "MOVE":
			int[] diceRolls = new int[3];
			diceRolls[0] = toInt(parsed[2]);
			diceRolls[1] = toInt(parsed[3]);
			diceRolls[2] = toInt(parsed[4]);
			currentPlayer.move(diceRolls);
			break;
		case "BUYESTATE":
			currentPlayer.buySquare();
			break;
		case "CARD":
			Card card;
			if (toInt(parsed[2]) == 0)
				card = ActionCards.getInstance().getChanceCard();
			else
				card = ActionCards.getInstance().getCommunityChestCard();
			currentPlayer.pickCard(card);
			break;
		case "RECEIVECOLOR":
			currentPlayer.setColor(parsed[2]);
			break;
		case "RECEIVEDICE":
			numOfDiceReceived++;
			currentPlayer.setInitialDiceOrder(Integer.parseInt(parsed[2]));
			if (numOfDiceReceived == players.size() - 1) {
				players.sort(new Comparator<Player>() {
					@Override
					public int compare(Player p1, Player p2) {
						return Integer.compare(p2.getInitialDiceOrder(), p1.getInitialDiceOrder());
					}
				});
				if (players.get(0).equals(myPlayer)) {
					currentPlayer = players.get(0);
					currentPlayer.publishGameEvent("PLAY");
				}
				UIFacade.getInstance().connectionDone();
			}
			break;
		case "UPDATESTATE":
			String message = "";
			for (int i = 2; i < parsed.length; i++) {
				message += parsed[i] + "/";
			}
			currentPlayer.publishGameEvent(message);
			break;
		case "ENDGAME":
			currentPlayer.endGame();
			players.remove(currentPlayer);
			Controller.getInstance().dispatchMessage("ACTION/" + currentPlayer.getName() + " left the game.");
			break;
		}
	}

	private void updateCurrentPlayer(String name) {
		for (Player player : players) {
			System.out.println(player.getName());
			if (player.getName().equals(name)) {
				currentPlayer = player;
				break;
			}
		}
	}

	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "START":
				for (Player player : players)
					player.createPiece();
				break;
			case "ROLLDICE":
				currentPlayer.play();
				break;
			case "ENDGAME":
				NetworkFacade.getInstance().sendMessageToOthers(currentPlayer.getName() + "/ENDGAME");
				destroy = true;
				break;
			case "BUYPROPERTY":
				currentPlayer.buySquare();
				NetworkFacade.getInstance().sendMessageToOthers(currentPlayer.getName() + "/BUYESTATE");
				break;
			case "ENDTURN":
				NetworkFacade.getInstance().sendMessageToOthers("ENDTURN");
				break;
			case "SAVEGAME":
				saveGame(parsed[2]);
				break;
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				NetworkFacade.getInstance().sendMessageToOthers("RECEIVENAME/" + parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				NetworkFacade.getInstance().sendMessageToOthers(myPlayer.getName() + "/RECEIVECOLOR/" + parsed[2]);
				currentPlayer.sendColor();
				break;
			case "LOADGAME":
				loadGame(parsed[2]);
				isNewGame = false;
				break;
			}
		}
	}

	private int toInt(String string) {
		return Integer.parseInt(string);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				if (destroy)
					break;
			}
			String message = NetworkFacade.getInstance().receiveMessage();
			String[] parsed = message.split("/");
			while (!parsed[0].equals("ENDTURN")) {
				executeNetworkMessage(parsed);
				message = NetworkFacade.getInstance().receiveMessage();
				parsed = message.split("/");
			}
			currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
			if (currentPlayer.equals(myPlayer)) 
				currentPlayer.publishGameEvent("PLAY");
		}
	}

	public void saveGame(String savedGameFileName) {
		// Create the SavedGame Object.

		SavedGame saved = new SavedGame(players, currentPlayer, order);
		try {
			// create a new file with an ObjectOutputStream
			FileOutputStream out = new FileOutputStream(savedGameFileName + ".txt");
			ObjectOutputStream oout = new ObjectOutputStream(out);

			// write something in the file
			oout.writeObject(saved);

			// close the stream
			oout.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void loadGame(String path) {
		try {
			// create an ObjectInputStream for the file we created before
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));

			// read and print what we wrote before
			SavedGame saved = (SavedGame) ois.readObject();
			players = saved.getPlayers();
			/*
			 * for (Player p : players) { if (p.getName().equals(saved.getCurreentPlayer()))
			 * { currentPlayer = p; break; } }
			 */
			// Who is the first player to play will be handled by server
			// (Instead of sending him the original order, we can send it modified
			// a bit.
			currentPlayer = players.get(0);
			this.order = saved.getOrder();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
