package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import game.card.ActionCards;
import game.card.Card;
import network.NetworkFacade;
import ui.UIFacade;

public class MonopolyGame implements Runnable {
	private boolean start = false;
	private boolean destroy = false;
	private volatile ArrayList<Player> players;
	private volatile Player currentPlayer;
	private Board board;
	private int order;
	private boolean isNewGame;
	

	public MonopolyGame() {
		board = new Board();
		players = new ArrayList<>();
		currentPlayer = new Player(board);
		players.add(currentPlayer);
		isNewGame = true;
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void executeNetworkMessage(String[] parsed) {
		if (parsed[0].equals("SENDDICE")) {
			if (isNewGame) {
				int[] dice = currentPlayer.rollDice();
				NetworkFacade.getInstance().sendMessageToOthers((dice[0] + dice[1]) + "");
			} else {
				NetworkFacade.getInstance().sendMessageToOthers(players.size() - order + "");
			}
			return;
		} else if (parsed[0].equals("SENDNAME")) {
			NetworkFacade.getInstance().sendMessageToOthers(players.get(0).getName());
			return;
		} else if (parsed[0].equals("SENDCOLOR")) {
			NetworkFacade.getInstance().sendMessageToOthers(players.get(0).getColor());
			return;
		} else if (parsed[0].equals("RECEIVENAME")) {
			if (isNewGame) {
				if (!parsed[1].equals(players.get(0).getName())) {

					Player newPlayer = new Player(board);
					newPlayer.setName(parsed[1]);
					players.add(newPlayer);

				} else {
					// This is the current player order in playing.
					order = players.size() - 1;
				}
			}
			return;
		} else if (parsed[0].equals("ALLDONE")) {
			UIFacade.getInstance().connectionDone();
			return;
		} else if (parsed[0].equals("PAUSE")) {
			pause();
			return;
		}	else if (parsed[0].equals("RESUME")) {
			resume();
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

	public void updateCurrentPlayer(String name) {
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
			case "PAUSE":
				pause();
				NetworkFacade.getInstance().sendMessageToOthers("PAUSE");
				break;
			case "RESUME":
				NetworkFacade.getInstance().sendMessageToOthers("RESUME");
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
				synchronized (this) {
					start = true;
					notify();
				}
				break;
			case "SAVEGAME":
				saveGame(parsed[2]);
				break;
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				currentPlayer.sendColor();
				break;
			case "SERVER":
				NetworkFacade.getInstance().connect(Integer.parseInt(parsed[2]));
				synchronized (this) {
					start = true;
					notify();
				}
				break;
			case "CLIENT":
				NetworkFacade.getInstance().connect(parsed[2]);
				if (!NetworkFacade.getInstance().isConnected()) {
					UIFacade.getInstance().connectionError();
					break;
				}
				synchronized (this) {
					start = true;
					notify();
				}
				break;
			case "LOADGAME":
				loadGame(parsed[2]);
				isNewGame = false;
				break;
			}
		}
	}

	/**
	 * @overview This function parses the given string 
	 * and creates an integer based on the string
	 * @requires the input to be a string of integers.
	 * @modifies input string.
	 * @effects
	 * @param string the input to turn into integer
	 * @return the integer created from the string
	 */
	public int toInt(String string) {
		return Integer.parseInt(string);
	}
	
	private void resume() {
		Controller.getInstance().publishGameEvent("RESUME");
	}
	
	private void pause() {
		Controller.getInstance().publishGameEvent("PAUSE");
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (!start)
						wait();
					if (destroy)
						break;
				}
				if (start) {
					String message = NetworkFacade.getInstance().receiveMessage();
					String[] parsed = message.split("/");
					while (!parsed[0].equals("PLAY")) {
						executeNetworkMessage(parsed);
						message = NetworkFacade.getInstance().receiveMessage();
						parsed = message.split("/");
					}
					currentPlayer = players.get(0);
					currentPlayer.publishGameEvent("PLAY");
				}
			} catch (InterruptedException e) {
			}
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
			/*for (Player p : players) {
				if (p.getName().equals(saved.getCurreentPlayer())) {
					currentPlayer = p;
					break;
				}
			}*/
			// Who is the first player to play will be handled by server
			// (Instead of sending him the original order, we can send it modified
			// a bit. 
			currentPlayer = players.get(0);
			this.order = saved.getOrder();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean repOk(){
		if(currentPlayer==null ||players==null ||players.size()==0 )
			return false;
		
		return true;
	}

}
