package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game.card.ActionCards;
import game.card.Card;
import network.NetworkFacade;
import ui.UIFacade;

public class MonopolyGame implements Runnable {
	private boolean start = false;
	private boolean destroy = false;
	private volatile ArrayList<Player> players;
	private volatile Player currentPlayer;

	public MonopolyGame() {
		players = new ArrayList<>();
		currentPlayer = new Player();
		players.add(currentPlayer);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void executeNetworkMessage(String[] parsed) {
		if (parsed[0].equals("SENDDICE")) {
			int[] dice = currentPlayer.rollDice();
			NetworkFacade.getInstance().sendMessageToOthers((dice[0] + dice[1]) + "");
			return;
		} else if (parsed[0].equals("SENDNAME")) {
			NetworkFacade.getInstance().sendMessageToOthers(players.get(0).getName());
			return;
		} else if (parsed[0].equals("SENDCOLOR")) {
			NetworkFacade.getInstance().sendMessageToOthers(players.get(0).getColor());
			return;
		} else if (parsed[0].equals("RECEIVENAME")) {
			if (!parsed[1].equals(players.get(0).getName())) {
				Player newPlayer = new Player();
				newPlayer.setName(parsed[1]);
				players.add(newPlayer);
			}
			return;
		} else if (parsed[0].equals("ALLDONE")) {
			UIFacade.getInstance().connectionDone();
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
		case "ENDGAME":
			currentPlayer.endGame();
			players.remove(currentPlayer);
			Controller.getInstance().dispatchMessage("ACTION/" + currentPlayer.getName() + " left the game.");
		}
	}

	private void updateCurrentPlayer(String name) {
		for (Player player : players) {
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
				synchronized (this) {
					start = true;
					notify();
				}
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
				synchronized (this) {
					start = true;
					notify();
				}
				break;
			case "SERVER":
				NetworkFacade.getInstance().connect(Integer.parseInt(parsed[2]));
				break;
			case "CLIENT":
				NetworkFacade.getInstance().connect(parsed[2]);
				if (!NetworkFacade.getInstance().isConnected()) {
					UIFacade.getInstance().connectionError();
					break;
				}
				break;
			case "LOADGAME":
				loadGame(parsed[2]);
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
		
		SavedGame saved = new SavedGame(players,currentPlayer);
		try {
			// create a new file with an ObjectOutputStream
			FileOutputStream out = new FileOutputStream(savedGameFileName+".txt");
			ObjectOutputStream oout = new ObjectOutputStream(out);

			// write something in the file
			oout.writeObject(saved);

			// close the stream
			oout.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void loadGame(String path){
		try {
	         // create an ObjectInputStream for the file we created before
	         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));

	         // read and print what we wrote before
	         SavedGame saved = (SavedGame)ois.readObject();
	         players = saved.getPlayers();
	         for(Player p: players){
	        	 if(p.getName().equals(saved.getCurreentPlayer())){
	        		 currentPlayer = p;
	        		 break;
	        	 }
	         }
	         
	         
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
	}

}
