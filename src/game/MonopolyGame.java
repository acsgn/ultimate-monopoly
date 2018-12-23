package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedDeque;

import game.card.ActionCards;
import game.card.Card;
import game.dice.SingletonDice;
import network.NetworkFacade;
import game.bot.bot;
public class MonopolyGame implements Runnable {

	private ConcurrentLinkedDeque<Player> players;
	private ConcurrentLinkedDeque<Player> checkedPlayers;
	private Player currentPlayer;
	private String myName;
	private int numOfPlayers = 0;
	private int numOfDiceReceived = 0;
	private int order;
	private boolean isNewGame;
	private Board board;
	private boolean destroy = false;
	
	private ArrayList<bot> bots; 
	

	public MonopolyGame() {
		board = new Board();
		players = new ConcurrentLinkedDeque<Player>();
		checkedPlayers = new ConcurrentLinkedDeque<Player>();
		bots = new ArrayList<>();
		isNewGame = true;
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
			case "START":
				currentPlayer = players.pollLast();
				currentPlayer.createPiece();
				for (Player player : players)
					player.createPiece();
				players.add(currentPlayer);
				break;
			case "PAUSE":
				pause();
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
				destroy = true;
				NetworkFacade.getInstance().sendMessage(myName + "/ENDGAME");
				break;
			case "BUYPROPERTY":
				NetworkFacade.getInstance().sendMessage(myName + "/BUYESTATE");
				break;
			case "ENDTURN":
				NetworkFacade.getInstance().sendMessage("ENDTURN");
				break;
			case "SAVEGAME":
				saveGame(parsed[2]);
				break;
			}
			break;
		case "BOT":
			switch(parsed[1]){
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
				NetworkFacade.getInstance().sendMessage("ENDTURN");
				break;
			case "CREATEBOT":
				NetworkFacade.getInstance().sendMessage("RECEIVENAME/" + parsed[2]);
				NetworkFacade.getInstance().sendMessage(parsed[2] + "/RECEIVECOLOR/" + "Black");
				SingletonDice.getInstance().rollDice();
				int[] dice = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance().sendMessage(parsed[2] + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				bot b = new bot(findPlayer(parsed[2]));
				bots.add(b);
				break;
			}
			break;
		case "UICREATOR":
			switch (parsed[1]) {
			case "START":
				NetworkFacade.getInstance().startGame();
				break;
			case "PLAYERNAME":
				myName = parsed[2];
				NetworkFacade.getInstance().sendMessage("RECEIVENAME/" + parsed[2]);
				break;
			case "PLAYERCOLOR":
				NetworkFacade.getInstance().sendMessage(myName + "/RECEIVECOLOR/" + parsed[2]);
				break;
			case "DICE":
				SingletonDice.getInstance().rollDice();
				int[] dice = SingletonDice.getInstance().getFaceValues();
				NetworkFacade.getInstance().sendMessage(myName + "/RECEIVEDICE/" + (dice[0] + dice[1]));
				break;
			case "LOADGAME":
				loadGame(parsed[2]);
				isNewGame = false;
				break;
			case "BOTCOUNT":
				int numOfBots = toInt(parsed[2]);
				numOfPlayers += numOfBots;
				for(int i = 0;i<numOfBots ; i++){
					bot.createBot();
				}
				break;
			}
			break;
		case "PLAYER":
			switch(parsed[1]){
			case "BIRTHGIFT":
				int money = 0;
				for(Player player : players){
					if(player.getName().equals(parsed[3]))
							continue;
					if(player.reduceMoney(toInt(parsed[2]))){
						money += toInt(parsed[2]);
					}else{
						// The player is bankrupted.
					}
				}
				Player p = findPlayer(parsed[3]);
				p.increaseMoney(money);
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

	private void resume() {
		Controller.getInstance().publishGameEvent("RESUME");
	}

	private void pause() {
		Controller.getInstance().publishGameEvent("PAUSE");
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
		if (message.equals("ENDTURN")) {
			currentPlayer = players.poll();
			players.add(currentPlayer);
			if (currentPlayer.getName().equals(myName))
				Controller.getInstance().publishGameEvent("PLAY");
			for(bot b : bots){
				if(b.getCurrentPlayer().getName().equals(currentPlayer.getName())){
					b.play();
				}
			}
			return;
		} else if (message.equals("CONNECTIVITYCHECK"))
			return;
		else if (message.equals("PLAYERCHECK")) {
			NetworkFacade.getInstance().sendMessage("CHECK/" + myName);
			return;
		} else if (message.equals("DISCONNECTED")) {
			Controller.getInstance().publishGameEvent("YOUDISCONNECTED");
			return;
		}
		String[] parsed = message.split("/");
		if (parsed[0].equals("PLAYERCOUNT")) {
			numOfPlayers = toInt(parsed[1]);
			Controller.getInstance().publishGameEvent(message);
			return;
		} else if (parsed[0].equals("RECEIVENAME")) {
			if (isNewGame) {
				Player newPlayer = new Player(board, parsed[1]);
				players.add(newPlayer);
			} else {
				// This is the current player order in playing.
				order = players.size() - 1;
			}
			return;
		} else if (parsed[0].equals("CONNECTIVITYPLAYERCOUNT")) {
			numOfPlayers = toInt(parsed[1]);
			return;
		} else if (parsed[0].equals("CHECK")) {
			Player player = findPlayer(parsed[1]);
			if (!checkedPlayers.contains(player))
				checkedPlayers.add(player);
			if (checkedPlayers.size() == numOfPlayers) {
				for (Player p : players)
					if (!checkedPlayers.contains(p)) {
						p.endGame();
						players.remove(p);
					}
				checkedPlayers.clear();
				if (players.peekLast() != currentPlayer) {
					currentPlayer = players.poll();
					players.add(currentPlayer);
					if (currentPlayer.getName().equals(myName))
						Controller.getInstance().publishGameEvent("PLAY");
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
		case "RECEIVECOLOR":
			currentPlayer.setColor(parsed[2]);
			break;
		case "RECEIVEDICE":
			currentPlayer.setInitialDiceOrder(Integer.parseInt(parsed[2]));
			if (++numOfDiceReceived == numOfPlayers) {
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
				currentPlayer = players.poll();
				players.add(currentPlayer);
				currentPlayer.sendColor();
				if (currentPlayer.getName().equals(myName))
					Controller.getInstance().publishGameEvent("PLAY");
				for(bot b : bots){
					if(b.getCurrentPlayer().getName().equals(currentPlayer.getName())){
						b.play();
					}
				}
				Controller.getInstance().publishGameEvent("START");
			}
			break;
		case "ENDGAME":
			currentPlayer.endGame();
			players.remove(currentPlayer);
			if (currentPlayer.getName().equals(myName))
				NetworkFacade.getInstance().endGame();
			break;
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
			currentPlayer = players.poll();
			this.order = saved.getOrder();
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
