package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.Card;
import game.card.action.Chance;
import game.card.action.CommunityChest;
import game.dice.SingletonDice;
import game.square.Square;
import game.square.SquareType;
import game.square.estate.*;

public class Player {

	private static final int BEGIN_MONEY = 3200;
	private static final TrackType BEGIN_TRACK = TrackType.MIDDLE_TRACK;
	private static final int BEGIN_INDEX = 0;

	private String name;
	private String color;
	private int money;
	private TrackType currentTrack;
	private int indexOnTrack;
	private Square location;
	boolean inJail;
	boolean isBankrupt;
	
	private ArrayList<Property> properties;
	private ArrayList<TransitStation> transitStations;
	private ArrayList<Utility> utilities;
	
	private ArrayList<GameListener> listeners;
	private String message;

	public Player() {
		listeners = new ArrayList<GameListener>();
		// this.board = board;
		money = BEGIN_MONEY;
		currentTrack = BEGIN_TRACK;
		indexOnTrack = BEGIN_INDEX;
		location = Board.getInstance().getSquare(indexOnTrack, currentTrack);
		properties = new ArrayList<>();
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
		updateState();
		location.executeAction(this);
	}

	public List<Integer> rollDice() {
		SingletonDice.getInstance().rollDice();
		return SingletonDice.getInstance().getFaceValues();
	}

	public void move(List<Integer> diceRolls) {
		// Mr.Monopoly AND Bus Icon will be handled in the nest phase
		// Now we just sum the first two regular dice/
		int sum = diceRolls.get(0) + diceRolls.get(1);
		Square newLocation = location;
		TrackType newTrack = currentTrack;
		int i = sum;
		int newIndex = 0;
		int currentIndex = indexOnTrack;
		boolean transitUsed = false;
		while (true) {
			if (!transitUsed && newLocation instanceof TransitStation && sum % 2 == 0) {
				newIndex = ((TransitStation) newLocation).getOtherIndex(newTrack);
				newTrack = ((TransitStation) newLocation).getOtherTrack(newTrack);
				currentIndex = newIndex;
				transitUsed = true;
			} else {
				newIndex = (currentIndex + 1) % Board.getInstance().getNoOfSquaresOnTrack(newTrack);
				newLocation = Board.getInstance().getSquare(newIndex, newTrack);
				i--;
				currentIndex = newIndex;
				transitUsed = false;
			}
			if (i == 0) {
				location = newLocation;
				break;
			}
		}

		// location = Board.getInstance().getSquare(indexOnTrack , trackID);
		message = "MOVE/" + 0 + "/";
		message += currentTrack.ordinal() + "/" + indexOnTrack + "/" + newTrack.ordinal() + "/" + newIndex;
		publishGameEvent(message);
		indexOnTrack = newIndex;
		currentTrack = newTrack;
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

	public List<Property> getProperties() {
		return this.properties;
	}

	public boolean payRent(Square s) {
		 int rent = ((Estate) s).getRent();
		// publishGameEvent(message);
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
		if (location instanceof Estate) {
			Estate estate = (Estate) location;
			if (estate.getOwner() == null) {
				estate.setOwner(this);
				reduceMoney(estate.getPrice());
				message = "ACTION/" + "ProperySquare" + estate.getName() + " is bought\n";
				publishGameEvent(message);
				updateState();
				return true;
			}else{
				message = "ACTION/"+"Property: is owned by "+ estate.getOwner().getName()+"\n";
				publishGameEvent(message);
				return false;
			}
		} else {
			message = "ACTION/" + "Sale Failed. It's not a property Square";
			publishGameEvent(message);
			return false;
		}
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

	public boolean buyBuilding(Building building, Property Property) {
		return false;

	}

	public void sellBuilding(Building building, Property Property) {

	}

	public void pickCard(Card card) {
		if (card instanceof CommunityChest) {
			((CommunityChest) card).executeAction(this);
			message = "ACTION/ " + ((CommunityChest) card).getName();
			publishGameEvent(message);
		}
		if (card instanceof Chance) {
			((Chance) card).executeAction(this);
			message = "ACTION/ " + ((Chance) card).getName();
			publishGameEvent(message);
		}

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

	public List<Property> getPropertys() {
		return properties;
	}

	public void setPropertys(ArrayList<Property> properties) {
		this.properties = properties;
	}

	public ArrayList<TransitStation> getTransitStations() {
		return transitStations;
	}

	public void setRailRoadSquares(ArrayList<TransitStation> transitStations) {
		this.transitStations = transitStations;
	}

	public List<Utility> getUtilitySquares() {
		return utilities;
	}

	public void setUtilitySquares(ArrayList<Utility> utilities) {
		this.utilities = utilities;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public void updateState() {
		message = "PLAYERDATA/";
		message += "Player Name: " + name + "\n";
		message += "Player Money: " + money + "\n";
		message += "Player Properties: \n";
		int i = 1;
		for (Property property : properties) {
			message += i + "- " + property.getName() + "\n";
			i++;
		}
		publishGameEvent(message);
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
