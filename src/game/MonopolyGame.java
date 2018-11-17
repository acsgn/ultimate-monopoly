package game;

import java.util.ArrayList;

import game.card.ActionCards;
import game.card.Card;
import network.NetworkFaçade;
import ui.UILinker;

public class MonopolyGame {
	private ArrayList<Player> players;
	private Player currentPlayer;

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

	public void receiveMessage() {
		String message = NetworkFaçade.getInstance().receiveMessage();
		String[] parsed = message.split("/");
		while (!parsed[0].equals("PLAY")) {
			executeNetworkMessage(parsed);
			message = NetworkFaçade.getInstance().receiveMessage();
			parsed = message.split("/");
		}
		currentPlayer = players.get(0);
		// Enable Buttons in UI
	}

	public void executeNetworkMessage(String[] parsed) {
		if(parsed[0].equals("SENDDICE")) {
			int [] dice = currentPlayer.rollDice();
			NetworkFaçade.getInstance().sendMessageToOthers((dice[0]+dice[1]) + "");
			return;
		}else if(parsed[0].equals("SENDNAME")) {
			NetworkFaçade.getInstance().sendMessageToOthers(players.get(0).getName());
			return;
		}else if(parsed[0].equals("SENDCOLOR")) {
			NetworkFaçade.getInstance().sendMessageToOthers(players.get(0).getColor());
			return;
		}else if(parsed[0].equals("RECEIVENAME")) {
			if(!parsed[1].equals(players.get(0).getName())){
				Player newPlayer = new Player();
				newPlayer.setName(parsed[1]);
				players.add(newPlayer);
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
			if(toInt(parsed[2]) == 0) 
				card = ActionCards.getInstance().getChanceCard();
			else 
				card = ActionCards.getInstance().getCommunityChestCard();
			currentPlayer.pickCard(card);
			break;
		case "RECEIVECOLOR":
			currentPlayer.setColor(parsed[2]);
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
				for(Player player: players) 
					player.createPiece();
				break;
			case "ROLLDICE":
				currentPlayer.play();
				break;
			case "ENDGAME":
				NetworkFaçade.getInstance().sendMessageToOthers("CLOSE");
				break;
			case "BUYPROPERTY":
				currentPlayer.buySquare();
			case "ENDTURN":
				NetworkFaçade.getInstance().sendMessageToOthers("ENDTURN");
				receiveMessage();
				break;
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				break;
			case "SERVER":
				NetworkFaçade.getInstance().connect(Integer.parseInt(parsed[2]));
				receiveMessage();
				UILinker.getInstance().connectionDone();
				break;
			case "CLIENT":
				NetworkFaçade.getInstance().connect(parsed[2]);
				if (!NetworkFaçade.getInstance().isConnected()) {
					UILinker.getInstance().connectionError();
					break;
				}
				receiveMessage();
				UILinker.getInstance().connectionDone();
				break;
			}
		}
	}

	private int toInt(String string) {
		return Integer.parseInt(string);
	}

}
