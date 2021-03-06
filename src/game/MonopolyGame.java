package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import game.card.ActionCards;
import game.card.Card;
import game.dice.SingletonDice;
import game.square.estate.ColorGroup;
import game.square.estate.Estate;
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
				if(!currentPlayer.getLocation().isOwned())
					Controller.getInstance().publishGameEvent("BUY");
				break;
			case "ROLLDICE":
				SingletonDice.getInstance().rollDice();
				int[] diceRolls = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance()
						.sendMessage(myName + "/PLAY/" + parsed[2] + "/" + parsed[3]  + "/" + diceRolls[2]);
				if (diceRolls[0] == diceRolls[1])
					Controller.getInstance().publishGameEvent("DOUBLE");
				break;
			case "ENDGAME":
				destroy = true;
				if (bots.containsKey(myName))
					for (Bot b : bots.get(myName))
						b.destroy();
				NetworkFacade.getInstance().sendMessage(myName + "/ENDGAME");
				NetworkFacade.getInstance().endGame();
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
				Estate s = (Estate) board.getSquare(toInt(parsed[2]), TrackType.getType(toInt(parsed[3])));
				Controller.getInstance().publishGameEvent(s.information());
				break;
			case "ENDTURN":
				NetworkFacade.getInstance().sendMessage("ENDTURN");
				break;
			case "SAVEGAME":
				saveGame(parsed[2]);
				break;
			case "CHAT":
				NetworkFacade.getInstance().sendMessage(parsed[1] + "/" + myName + " said: " + parsed[2]);
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
				findPlayer(myName).sellBuildingAction();
				break;
			case "SELLBUILDING2":
				NetworkFacade.getInstance().sendMessage(myName + "/SELLBUILDING2/" + parsed[2] + "/" + parsed[3]);
				break;
			case "HURRICANE":
				NetworkFacade.getInstance()
						.sendMessage(myName + "/HURRICANE/" + parsed[2] + "/" + parsed[3] + "/" + parsed[4]);
				break;
			case "MORTGAGE":
				findPlayer(myName).mortgageAction();
				break;
			case "MORTGAGE2":
				NetworkFacade.getInstance().sendMessage(myName + "/MORTGAGE2/" + parsed[2]);
				break;
			case "UNMORTGAGE":
				findPlayer(myName).unmortgageAction();
				break;
			case "UNMORTGAGE2":
				NetworkFacade.getInstance().sendMessage(myName + "/UNMORTGAGE2/" + parsed[2]);
				break;
			case "JAIL2":
				switch (parsed[2]) {
				case "DOUBLES":
					SingletonDice.getInstance().rollDice();
					int[] dr = SingletonDice.getInstance().getFaceValues();
					NetworkFacade.getInstance().sendMessage(myName + "/" + "JAIL2/DOUBLES/" + dr[0] + "/" + dr[1]);
					break;
				case "PAYBAIL":
					NetworkFacade.getInstance().sendMessage(myName + "/" + "JAIL2/PAYBAIL");
					break;
				}
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
			case "ENDTURN2":
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
				myName = parsed[2];
				NetworkFacade.getInstance().startGame();
				int numOfBots = toInt(parsed[4]);
				for (int i = 0; i < numOfBots; i++)
					Bot.createBot();
				NetworkFacade.getInstance().sendMessage("CREATEPLAYER/" + parsed[2] + "/" + parsed[3]);
				SingletonDice.getInstance().rollDice();
				int[] dice = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance().sendMessage(myName + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				break;
			case "LOAD":
				myName = parsed[2];
				NetworkFacade.getInstance().startGame();
				NetworkFacade.getInstance().sendMessage("LOAD");
				NetworkFacade.getInstance().sendSavedGameFile(parsed[3]);
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
			case "CARD":
				if (parsed[2].equals(myName)) {
					// Card card;
					int index = 0;
					if (toInt(parsed[3]) == 0) {
						ActionCards.getInstance().getChanceCard();
						index = ActionCards.getInstance().getIndexChanceCard();
					} else {
						ActionCards.getInstance().getCommunityChestCard();
						index = ActionCards.getInstance().getIndexCommunityCard();
					}
					NetworkFacade.getInstance().sendMessage(parsed[2] + "/" + "CARD" + "/" + index + "/" + parsed[3]);
				}
				// currentPlayer.pickCard(card);
				break;
			case "HURRICANE":
				switch (parsed[2]) {
				case "GETNAMES":
					if (parsed[3].equals(myName))
						NetworkFacade.getInstance().sendMessage(parsed[3] + "/HURRICANE/GETNAMES");
					break;
				case "EXECUTE":
					NetworkFacade.getInstance()
							.sendMessage(parsed[5] + "/HURRICANE/" + "EXECUTE" + "/" + parsed[3] + "/" + parsed[4]);
				}
				break;
			case "ZERODOLLARSDOWN":
				Player curr = findPlayer(parsed[2]);
				ArrayList<ColorGroup> cGroup = curr.getMonopolyColorGroups();
				if (cGroup.size() != 0) {
					String level = cGroup.get(0).getLevel().toString();
					String square = cGroup.get(0).getAvailableSquares().get(0).getName();
					String info = square + "/" + level + "/";
					curr.buyBuilding(info, true);
				}
				// NetworkFacade.getInstance().sendMessage(parsed[2] + "/" +
				// parsed[1]);
				break;
			case "JAILACTION":
				if (parsed[2].equals(myName)) {
					Player.publishGameEvent("JAILACTION");
				}
				break;
			}
			break;
		}
	}

	/**
	 * @overview This function parses the given string and creates an integer based
	 *           on the string
	 * @requires the input to be a string of integers.
	 * @modifies input string.
	 * @effects
	 * @param string the input to turn into integer
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
		case "LOAD":
			isNewGame = false;
			Object saveGame = NetworkFacade.getInstance().receiveSavedGameFile();
			SaveGame save = (SaveGame) saveGame;
			players = save.getPlayers();
			bots = save.getBots();
			board = save.getBoard();
			currentPlayer = players.peekLast();
			currentPlayer.sendColor();
			for (Player p : players)
				p.informUI();
			board.informUI();
			if (bots.containsKey(myName))
				for (Bot b : bots.get(myName))
					b.start();
			Controller.getInstance().publishGameEvent("START");
			informCurrentPlayer();
			return;
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
				Player newPlayer = new Player(parsed[1], parsed[2], board);
				newPlayer.informUI();
				players.add(newPlayer);
			}
			return;
		case "CREATEBOT":
			if (isNewGame) {
				Player newPlayer = new Player(parsed[2], parsed[3], board);
				newPlayer.setBot();
				newPlayer.informUI();
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
		case "CHAT":
			Controller.getInstance().publishGameEvent(message);
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
			// ((Estate) currentPlayer.getLocation()).information();
			break;
		case "CARD":
			// This should be picked by executeMessage
			int index = toInt(parsed[2]);
			Card card;
			if (toInt(parsed[3]) == 0)
				card = ActionCards.getInstance().getChanceCardByIndex(index);
			else
				card = ActionCards.getInstance().getCommunityCardByIndex(index);
			currentPlayer.pickCard(card);
			break;
		case "HURRICANE":
			if (parsed[2].equals("GETNAMES") && currentPlayer.getName().equals(myName)) {
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
			if (cGroup.size() != 0) {
				String level = cGroup.get(0).getLevel().toString();
				String square = cGroup.get(0).getAvailableSquares().get(0).getName();
				String info = square + "/" + level + "/";
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
				board.informUI();
				Controller.getInstance().publishGameEvent("START");
				informCurrentPlayer();
			}
			break;
		case "ENDGAME":
			currentPlayer.endGame();
			players.remove(currentPlayer);
			if (bots.containsKey(currentPlayer.getName())) {
				for (Bot b : bots.remove(currentPlayer.getName())) {
					b.getPlayer().endGame();
					players.remove(b.getPlayer());
				}
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
		case "SELLBUILDING2":
			currentPlayer.sellBuilding(parsed[2], parsed[3]);
			break;
		case "MORTGAGE2":
			currentPlayer.mortgage(parsed[2]);
			break;
		case "UNMORTGAGE2":
			currentPlayer.unmortgage(parsed[2]);
			break;
		case "JAIL2":
			switch (parsed[2]) {
			case "DOUBLES":
				boolean isDouble = (toInt(parsed[3]) == toInt(parsed[4]));
				if (isDouble) {
					currentPlayer.gotOutOfJail();
					int[] dr = { toInt(parsed[3]), toInt(parsed[4]), 4 };
					currentPlayer.move(dr);
				} else {
					Player.publishGameEvent(
							"ACTION/" + currentPlayer.getName() + " didn't roll Doubles! Stay in Jail!");
					currentPlayer.incrementJailCounter();
					if (currentPlayer.getJailCounter() == 3) {
						currentPlayer.payBail();
						Player.publishGameEvent(
								"ACTION/" + currentPlayer.getName() + " paid Bail automatically afer 3 doubles!");
						int[] dr = { toInt(parsed[3]), toInt(parsed[4]), 4 };
						currentPlayer.move(dr);
					}
				}
				break;
			case "PAYBAIL":
				currentPlayer.payBail();
				break;
			}
			break;
		}
	}

	private void informCurrentPlayer() {
		Controller.getInstance().publishGameEvent("PLAYERINFO/" + currentPlayer.getName());
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

	public void saveGame(String saveGameFile) {
		try {
			SaveGame saved = new SaveGame(players, bots, board);
			FileOutputStream fos = new FileOutputStream(new File(saveGameFile + ".umsf"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(saved);
			oos.close();
		} catch (Exception ex) {
		}
	}

	public boolean repOk() {
		if (currentPlayer == null || players == null || players.size() == 0)
			return false;

		return true;
	}

}
