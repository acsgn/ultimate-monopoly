package game;

import java.util.ArrayList;

import network.NetworkFaçade;
import ui.UIFaçade;

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
	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "ROLLDICE":
				currentPlayer.play(); break;
			case "ENDGAME": 
				NetworkFaçade.getInstance().sendMessageToOthers("CLOSE");break;
			case "BUYPROPERTY":
				currentPlayer.buySquare();
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
				UIFaçade.getInstance().connectionDone();
				break;
			case "CLIENT":
				NetworkFaçade.getInstance().connect(parsed[2]);
				if (!NetworkFaçade.getInstance().isConnected()) {
					UIFaçade.getInstance().connectionError();
					break;
				}
				UIFaçade.getInstance().connectionDone();
				break;
			}
		}
	}
	
}
