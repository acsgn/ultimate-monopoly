package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.action.Chance;
import game.card.action.CommunityChest;
import game.dice.SingletonDice;
import game.square.estate.PropertySquare;
import game.square.estate.RailroadSquare;
import game.square.estate.UtilitySquare;
import game.square.estate.TransitstationSquare;

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
	// private Board board;
	boolean inJail;
	boolean isBankrupt;
	private List<PropertySquare> propertySquares;
	private List<RailroadSquare> railRoadSquares;
	private List<UtilitySquare> utilitySquares;
	private ArrayList<GameListener> listeners;
	private String message;

	public Player() {
		listeners = new ArrayList<GameListener>();
		// this.board = board;
		money = BEGIN_MONEY;
		currentTrack = BEGIN_TRACK;
		indexOnTrack = BEGIN_INDEX;
		location = Board.getInstance().getSquare(indexOnTrack, currentTrack);
		propertySquares = new ArrayList<>();
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
			System.out.println(newLocation.getName());
			if (!transitUsed && newLocation instanceof TransitstationSquare && sum % 2 == 0) {
				newIndex = ((TransitstationSquare) newLocation).getOtherIndex(newTrack);
				newTrack = ((TransitstationSquare) newLocation).getOtherTrack(newTrack);
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
		if (location instanceof PropertySquare) {
			if (((PropertySquare) location).getOwner() == null) {
				((PropertySquare) location).setOwner(this);
				propertySquares.add((PropertySquare) location);
				reduceMoney(((PropertySquare) location).getPrice());
				message = "ACTION/" + "ProperySquare" + location.getName() + " is bought\n";
				publishGameEvent(message);
				updateState();
				return true;
			}else{
				message = "ACTION/"+"PropertySquare: is owned by "+ ((PropertySquare) location).getOwner().getName()+"\n";
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

	public boolean buyBuilding(Building building, PropertySquare propertySquare) {
		return false;

	}

	public void sellBuilding(Building building, PropertySquare propertySquare) {

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

	public void updateState() {
		message = "PLAYERDATA/";
		message += "Player Name: " + name + "\n";
		message += "Player Money: " + money + "\n";
		message += "Player Properties: \n";
		int i = 1;
		for (PropertySquare property : propertySquares) {
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
