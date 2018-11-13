package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.Card;
import game.dice.SingletonDice;
import game.square.Square;
import game.square.tradable.PropertySquare;
import game.square.tradable.RailroadSquare;
import game.square.tradable.UtilitySquare;

public class Player {

	private static final int BEGIN_MONEY = 3200;

	private String name;
	private String color;
	private int money;
	private int trackID;
	private int indexOnTrack;
	private Square location;
	// private Board board;
	boolean inJail;
	boolean isBankrupt;
	private List<PropertySquare> propertySquares;
	private List<RailroadSquare> railRoadSquares;
	private List<UtilitySquare> utilitySquares;
	private ArrayList<GameListener> listeners;
	private String message;

	public Player() {
		this.money = BEGIN_MONEY;
		// this.board = board;
		listeners = new ArrayList<GameListener>();
		indexOnTrack = 0;
		trackID = 2;
		location = Board.getInstance().getSquare(indexOnTrack, trackID);
	}

	public void setName(String name) {
		this.name = name;
		message = "NAME/" + name;
		publishGameEvent(message);
	}

	public void setColor(String color) {
		this.color = color;
		message = "COLOR/" + color;
		publishGameEvent(message);
	}

	public void startGame() {
		message = "START";
		publishGameEvent(message);
	}

	public void networkError() {
		message = "NETWORKERROR";
		publishGameEvent(message);
	}

	public void play() {
		List<Integer> diceRolls = rollDice();
		// move(diceRolls);
		// location.executeAction();
		message = "ACTION/";
		message += "Regular Die 1: " + diceRolls.get(0) + "\n";
		message += "Regular Die 2: " + diceRolls.get(1) + "\n";
		if (diceRolls.get(2) == 4) {
			message += "Speed Die : Bus Icon\n";
		} else if (diceRolls.get(2) == 5) {
			message += "Speed Die : Mr.Monopoly Bonus Move\n";
		} else {
			message += "Speed Die: " + diceRolls.get(2) + "\n";
		}
		publishGameEvent(message);
		move(diceRolls);

		// location.executeAction(this);
	}

	public List<Integer> rollDice() {
		SingletonDice.getInstance().rollDice();
		return SingletonDice.getInstance().getFaceValues();
	}

	public void move(List<Integer> diceRolls) {
		// Mr.Monopoly AND Bus Icon will be handled in the nest phase
		// Now we just sum the first two regular dice/
		int sum = diceRolls.get(0) + diceRolls.get(1);

		int newIndexOnTrack;
		if (true) { // sum % 2 != 0
			int noOfSquares = 40;//Board.getInstance().getNoOfSquaresOnTrack(trackID);
			newIndexOnTrack = indexOnTrack + sum;
			newIndexOnTrack = newIndexOnTrack < noOfSquares ? newIndexOnTrack : newIndexOnTrack - noOfSquares;
		}
		// only moves on same track for now
		/*
		 * else { int[] transitLocationsOfTrack =
		 * Board.getInstance().getTransitStationLocationsOnTrack(track); int
		 * newLocationInTrack = indexOnTrack + sum; for (int i = 0; i <
		 * transitLocationsOfTrack.length; i++) { if (indexOnTrack <
		 * transitLocationsOfTrack[i] && newLocationInTrack > transitLocationsOfTrack[i]
		 * ) {
		 * 
		 * } } }
		 */

		// location = Board.getInstance().getSquare(indexOnTrack , trackID);
		message = "MOVE/" + 0 + "/";
		message += trackID + "/" + indexOnTrack + "/" + trackID + "/" + newIndexOnTrack;
		publishGameEvent(message);
		indexOnTrack= newIndexOnTrack;
	}

	public Square getLocation() {
		return location;
	}

	public boolean isInJail() {
		return inJail;
	}

	public boolean isBankrupt() {
		return isBankrupt;
	}

	public List<PropertySquare> getProperties() {
		return this.propertySquares;
	}

	public boolean payRent(Square s) {
		// publishGameEvent(message);
		int rent = 0;
		if (s instanceof PropertySquare) {
			rent = ((PropertySquare) s).getRent();
		} else if (s instanceof RailroadSquare) {
			rent = ((RailroadSquare) s).getRent();
		} else if (s instanceof UtilitySquare) {
			rent = ((UtilitySquare) s).getRent();
		}
		return reduceMoney(rent);
	}
	
	public boolean payBail(int amount) {
		Pool.getInstance().payToPool(amount);
		return reduceMoney(amount);		
	}

	public void collectRent(int rent) {
		money += rent;
	}

	public boolean buySquare() {
		return false;

	}

	public boolean passGo(int prevIndex, int newIndex) {
		return false;
	}

	public boolean passPayDay(int prevIndex, int newIndex) {
		return false;

	}

	public boolean passBonus(int prevIndex, int newIndex) {
		return false;

	}

	public boolean buyBuilding(Building building, PropertySquare propertySquare) {
		return false;

	}

	public void sellBuilding(Building building, PropertySquare propertySquare) {

	}

	public void pickCard(Card card) {
		card.executeAction();
	}

	public boolean reduceMoney(int m) {
		if (money > m) {
			this.money -= m;
			return true;
		}
		return false;
	}

	public void increaseMoney(int m) {
		this.money += m;
	}

	public String getName() {
		return name;
	}

	public List<PropertySquare> getPropertySquares() {
		return propertySquares;
	}

	public void setPropertySquares(List<PropertySquare> propertySquares) {
		this.propertySquares = propertySquares;
	}

	public List<RailroadSquare> getRailRoadSquares() {
		return railRoadSquares;
	}

	public void setRailRoadSquares(List<RailroadSquare> railRoadSquares) {
		this.railRoadSquares = railRoadSquares;
	}

	public List<UtilitySquare> getUtilitySquares() {
		return utilitySquares;
	}

	public void setUtilitySquares(List<UtilitySquare> utilitySquares) {
		this.utilitySquares = utilitySquares;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public void addGamelistener(GameListener lis) {
		listeners.add(lis);
	}

	public void publishGameEvent(String message) {
		for (GameListener l : listeners) {
			l.onGameEvent(message);
		}
	}

}
