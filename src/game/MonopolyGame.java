package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import game.card.ActionCards;
import game.card.Card;
import game.card.Chance;
import game.dice.SingletonDice;
import game.square.estate.ColorGroup;
import game.square.estate.Property;
import network.NetworkFacade;
import game.bot.Bot;

public class MonopolyGame implements Runnable {

	private ConcurrentLinkedDeque<Player> players;
	private ConcurrentLinkedDeque<Player> checkedPlayers;
	private Player currentPlayer;
	private Board board;
	private String myName;
	private int numOfPlayers = 0;
	private int numOfDiceReceived = 0;
	private boolean isNewGame;
	private boolean destroy = false;

	private ConcurrentHashMap<String, LinkedList<Bot>> bots;

	public MonopolyGame() {
		players = new ConcurrentLinkedDeque<Player>();
		checkedPlayers = new ConcurrentLinkedDeque<Player>();
		bots = new ConcurrentHashMap<String, LinkedList<Bot>>();
		isNewGame = true;
		board = new Board();
		NetworkFacade.getInstance().startNetwork();
		new Thread(this, "Game").start();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public ConcurrentLinkedDeque<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ConcurrentLinkedDeque<Player> players) {
		this.players = players;
	}

	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "PAUSE":
				NetworkFacade.getInstance().sendMessage("PAUSE");
				break;
			case "RESUME":
				NetworkFacade.getInstance().sendMessage("RESUME");
				break;
			case "ROLLDICE":
				SingletonDice.getInstance().rollDice();
				int[] diceRolls = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance()
						.sendMessage(myName + "/PLAY/" + diceRolls[0] + "/" + diceRolls[1] + "/" + diceRolls[2]);
				break;
			case "ENDGAME":
				NetworkFacade.getInstance().sendMessage(myName + "/ENDGAME");
				destroy = true;
				NetworkFacade.getInstance().endGame();
				if (bots.containsKey(myName))
					for (Bot b : bots.get(myName))
						b.destroy();
				break;
			case "BUYPROPERTY":
				NetworkFacade.getInstance().sendMessage(myName + "/BUYESTATE");
				break;
			case "PLAYERINFO":
				Player p = findPlayer(parsed[2]);
				if (p != null)
					p.updateState();
				break;
			case "DEEDINFO":
				break;
			case "ENDTURN":
				NetworkFacade.getInstance().sendMessage("ENDTURN");
				break;
			case "SAVEGAME":
				saveGame(parsed[2]);
				break;
			case "ANIMATIONEND":
				synchronized (this) {
					notify();
				}
				break;
			case "BUYBUILDING":
				NetworkFacade.getInstance().sendMessage(myName + "/BUYBUILDING");
				break;
			case "BUYBUILDING2":
				NetworkFacade.getInstance().sendMessage(myName + "/BUYBUILDING2/" + parsed[2]);
				break;
			case "BUYBUILDING3":
				NetworkFacade.getInstance().sendMessage(myName + "/BUYBUILDING3/" + parsed[2] + "/" + parsed[3]);
				break;
			case "SELLBUILDING":
				NetworkFacade.getInstance().sendMessage(myName + "/SELLBUILDING");
				break;
			case "HURRICANE":
				NetworkFacade.getInstance()
						.sendMessage(myName + "/HURRICANE/" + parsed[2] + "/" + parsed[3] + "/" + parsed[4]);
				break;
			}
			break;
		case "BOT":
			switch (parsed[1]) {
			case "ROLLDICE":
				SingletonDice.getInstance().rollDice();
				int[] diceRolls = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance()
						.sendMessage(parsed[2] + "/PLAY/" + diceRolls[0] + "/" + diceRolls[1] + "/" + diceRolls[2]);
				break;
			case "BUYPROPERTY":
				NetworkFacade.getInstance().sendMessage(parsed[2] + "/BUYESTATE");
				break;
			case "ENDTURN":
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
					}
				}
				NetworkFacade.getInstance().sendMessage("ENDTURN");
				break;
			case "CREATEBOT":
				NetworkFacade.getInstance()
						.sendMessage("CREATEBOT/" + myName + "/" + myName + parsed[2] + "/" + parsed[3]);
				SingletonDice.getInstance().rollDice();
				int[] dice = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance().sendMessage(myName + parsed[2] + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				break;
			}
			break;
		case "UICREATOR":
			switch (parsed[1]) {
			case "CREATE":
				NetworkFacade.getInstance().startGame();
				myName = parsed[2];
				int numOfBots = toInt(parsed[4]);
				for (int i = 0; i < numOfBots; i++)
					Bot.createBot();
				NetworkFacade.getInstance().sendMessage("CREATEPLAYER/" + parsed[2] + "/" + parsed[3]);
				SingletonDice.getInstance().rollDice();
				int[] dice = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance().sendMessage(myName + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				break;
			case "LOADGAME":
				loadGame(parsed[2]);
				isNewGame = false;
				break;
			}
			break;
		case "PLAYER":
			switch (parsed[1]) {
			case "BIRTHGIFT":
				int money = 0;
				for (Player player : players) {
					if (player.getName().equals(parsed[3]))
						continue;
					if (player.reduceMoney(toInt(parsed[2]))) {
						money += toInt(parsed[2]);
					} else {
						// The player is bankrupted.
					}
				}
				Player p = findPlayer(parsed[3]);
				p.increaseMoney(money);
				break;
			case "HURRICANE":
				switch (parsed[2]) {
				case "GETNAMES":
					NetworkFacade.getInstance().sendMessage(parsed[3] + "/HURRICANE/GETNAMES");
					break;
				case "EXECUTE":
					NetworkFacade.getInstance()
							.sendMessage(parsed[5] + "/HURRICANE/" + "EXECUTE" + "/" + parsed[3] + "/" + parsed[4]);
				}
				break;
			case "ZERODOLLARSDOWN":
				NetworkFacade.getInstance().sendMessage(parsed[2] + "/" + parsed[1]);
				break;
			}
			break;
		}
	}

	/**
	 * @overview This function parses the given string and creates an integer
	 *           based on the string
	 * @requires the input to be a string of integers.
	 * @modifies input string.
	 * @effects
	 * @param string
	 *            the input to turn into integer
	 * @return the integer created from the string
	 */
	public int toInt(String string) {
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
			System.out.println(message);
			executeNetworkMessage(message);
		}
	}

	public void executeNetworkMessage(String message) {
		switch (message) {
		case "ENDTURN":
			currentPlayer = players.poll();
			players.add(currentPlayer);
			informCurrentPlayer();
			return;
		case "PLAYERCHECK":
			NetworkFacade.getInstance().sendMessage("CHECK/" + myName);
			return;
		case "DISCONNECTED":
			Controller.getInstance().publishGameEvent("YOUDISCONNECTED");
			return;
		case "PAUSE":
			Controller.getInstance().publishGameEvent("PAUSE");
			return;
		case "RESUME":
			Controller.getInstance().publishGameEvent("RESUME");
			return;
		}
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "PLAYERCOUNT":
			numOfPlayers = toInt(parsed[1]);
			Controller.getInstance().publishGameEvent(message);
			return;
		case "CREATEPLAYER":
			if (isNewGame) {
				Player newPlayer = new Player(parsed[1], parsed[2],board);
				newPlayer.createPiece();
				players.add(newPlayer);
			} else {
			}
			return;
		case "CREATEBOT":
			if (isNewGame) {
				Player newPlayer = new Player(parsed[2], parsed[3],board);
				newPlayer.setBot();
				newPlayer.createPiece();
				Bot b = new Bot(newPlayer);
				if (parsed[1].equals(myName))
					b.start();
				players.add(newPlayer);
				if (bots.containsKey(parsed[1]))
					bots.get(parsed[1]).add(b);
				else {
					bots.put(parsed[1], new LinkedList<Bot>());
					bots.get(parsed[1]).add(b);
				}
			}
			return;
		case "CONNECTIVITYPLAYERCOUNT":
			numOfPlayers = toInt(parsed[1]);
			return;
		case "CHECK":
			Player player = findPlayer(parsed[1]);
			if (!checkedPlayers.contains(player))
				checkedPlayers.add(player);
			if (checkedPlayers.size() == numOfPlayers) {
				for (Player p : players)
					if (!checkedPlayers.contains(p) && !p.isBot()) {
						p.endGame();
						players.remove(p);
						if (bots.containsKey(p.getName())) {
							for (Bot b : bots.get(p.getName())) {
								b.getPlayer().endGame();
								players.remove(b.getPlayer());
							}
							bots.remove(p.getName());
						}
					}
				checkedPlayers.clear();
				if (players.peekLast() != currentPlayer) {
					currentPlayer = players.poll();
					players.add(currentPlayer);
					informCurrentPlayer();
				}
			}
			return;
		}
		currentPlayer = findPlayer(parsed[0]);
		switch (parsed[1]) {
		case "PLAY":
			int[] diceRolls = new int[3];
			diceRolls[0] = toInt(parsed[2]);
			diceRolls[1] = toInt(parsed[3]);
			diceRolls[2] = toInt(parsed[4]);
			currentPlayer.play(diceRolls);
			break;
		case "BUYESTATE":
			currentPlayer.buySquare();
			break;
		case "CARD":
			// This should be picked by executeMessage
			Card card;
			if (toInt(parsed[2]) == 0)
				card = ActionCards.getInstance().getChanceCard();
			else
				card = ActionCards.getInstance().getCommunityChestCard();
			currentPlayer.pickCard(card);
			break;
		case "HURRICANE":
			if (parsed[2].equals("GETNAMES")) {
				Hashtable<String, ArrayList<String>> playersData = new Hashtable<>();
				for (Player p : players) {
					if (!p.getName().equals(currentPlayer.getName())) {
						ArrayList<String> colorGroups = new ArrayList<>();
						for (ColorGroup c : p.getMonopolyColorGroups()) {
							boolean test = false;
							for (Property property : c.getPropertyColorSquares()) {
								if (property.getBuildings().size() > 0) {
									test = true;
									break;
								}
							}
							if (test)
								colorGroups.add(c.getColor().toString());
						}
						if (p.getMonopolyColorGroups().size() != 0)
							playersData.put(p.getName(), colorGroups);
					}
				}
				currentPlayer.doHurricaneAction(playersData);
			} else if (parsed[2].equals("EXECUTE")) {
				String player = parsed[3];
				String group = parsed[4];
				currentPlayer.executeHurricaneAction(findPlayer(player), group);
			}
			break;
		case "ZERODOLLARSDOWN":
			ArrayList<ColorGroup> cGroup = currentPlayer.getMonopolyColorGroups();
			if(cGroup.size()!=0){
				String level = cGroup.get(0).getLevel().toString();
				String square = cGroup.get(0).getAvailableSquares().get(0).getName();
				String info = square+ "/" + level + "/";
				currentPlayer.buyBuilding(info, true);	
			}
			break;
		case "RECEIVEDICE":
			currentPlayer.setInitialDiceOrder(Integer.parseInt(parsed[2]));
			if (!currentPlayer.isBot())
				numOfDiceReceived++;
			if (numOfDiceReceived == numOfPlayers) {
				sortPlayers();
				currentPlayer = players.poll();
				players.add(currentPlayer);
				currentPlayer.sendColor();
				Controller.getInstance().publishGameEvent("START");
				informCurrentPlayer();
			}
			break;
		case "ENDGAME":
			currentPlayer.endGame();
			players.remove(currentPlayer);
			if (bots.containsKey(currentPlayer.getName())) {
				for (Bot b : bots.get(currentPlayer.getName())) {
					b.getPlayer().endGame();
					players.remove(b.getPlayer());
				}
				bots.remove(currentPlayer.getName());
			}
			break;
		case "BUYBUILDING":
			currentPlayer.buyBuildingAction();
			break;
		case "BUYBUILDING2":
			currentPlayer.buyBuildingChooseSquare(parsed[2]);
			break;
		case "BUYBUILDING3":
			currentPlayer.buyBuilding(parsed[2] + "/" + parsed[3], false);
			break;
		case "SELLBUILDING":
			currentPlayer.pickCard(new Chance("Hurricane", true));
			break;
		case "PAYRENT":
			int rent = currentPlayer.payRent();
			Player owner = findPlayer(parsed[2]);
			owner.increaseMoney(rent);
			break;
		}
	}

	private void informCurrentPlayer() {
		if (currentPlayer.getName().equals(myName))
			Controller.getInstance().publishGameEvent("PLAY");
		else if (bots.containsKey(myName))
			for (Bot b : bots.get(myName))
				if (b.getPlayer().equals(currentPlayer))
					b.play();
	}

	private void sortPlayers() {
		Player[] tmp = new Player[players.size()];
		Arrays.sort(players.toArray(tmp), new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p2.getInitialDiceOrder(), p1.getInitialDiceOrder());
			}
		});
		for (Player p : tmp) {
			players.add(p);
			players.poll();
		}
	}

	private Player findPlayer(String name) {
		for (Player player : players)
			if (player.getName().equals(name))
				return player;
		return null;
	}

	public void saveGame(String savedGameFileName) {
		// Create the SavedGame Object.

		SavedGame saved = new SavedGame(players, currentPlayer, bots);
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
			 * for (Player p : players) { if
			 * (p.getName().equals(saved.getCurreentPlayer())) { currentPlayer =
			 * p; break; } }
			 */
			// Who is the first player to play will be handled by server
			// (Instead of sending him the original order, we can send it
			// modified
			// a bit.
			currentPlayer = players.poll();
			bots = saved.getBots();
			ois.close();
		} catch (Exception ex) {
		}
	}

	public boolean repOk() {
		if (currentPlayer == null || players == null || players.size() == 0)
			return false;

		return true;
	}

}
