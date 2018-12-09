package game;

import java.io.Serializable;
import java.util.ArrayList;

import game.building.Building;
import game.card.Card;
import game.card.Chance;
import game.card.CommunityChest;
import game.square.Square;
import game.square.estate.*;

public class Player implements Serializable{

	private static final int BEGIN_MONEY = 3200;
	private static final TrackType BEGIN_TRACK = TrackType.MIDDLE_TRACK;
	private static final int BEGIN_INDEX = 0;

	private static int playerIndexCounter = 0;

	private String name;
	private String color;
	private int playerIndex;
	private int initialDiceOrder;
	private int money;

	private TrackType currentTrack;
	private int indexOnTrack;
	private Square location;

	boolean inJail;
	boolean isBankrupt;
	boolean goAnyWhere;

	private ArrayList<Property> properties;
	private ArrayList<TransitStation> transitStations;
	private ArrayList<Utility> utilities;

	private String message;

	public Player(String name) {
		this.name = name;
		money = BEGIN_MONEY;
		currentTrack = BEGIN_TRACK;
		indexOnTrack = BEGIN_INDEX;
		playerIndex = playerIndexCounter;
		playerIndexCounter++;
		location = Board.getInstance().getSquare(indexOnTrack, currentTrack);
		properties = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void sendColor() {
		message = "COLOR/" + color;
		publishGameEvent(message);
	}

	public void createPiece() {
		message = "PIECE/" + color + "/" + currentTrack.ordinal() + "/" + indexOnTrack;
		publishGameEvent(message);
	}

	public void play(int[] diceRolls) {
		sendColor();
		message = "ACTION/";
		message += name+" rolled:";
		message += "Regular Die 1: " + diceRolls[0] + "\n";
		message += "Regular Die 2: " + diceRolls[1] + "\n";
		if (diceRolls[2] == 4) {
			message += "Speed Die : Bus Icon\n";
		} else if (diceRolls[2] == 5) {
			message += "Speed Die : Mr.Monopoly Bonus Move\n";
		} else {
			message += "Speed Die: " + diceRolls[2] + "\n";
		}
		publishGameEvent(message);
		move(diceRolls);
		updateState();
		location.executeWhenLanded(this);
	}

	public void move(int[] diceRolls) {
		// Mr.Monopoly AND Bus Icon will be handled in the nest phase
		// Now we just sum the first two regular dice/
		int sum = diceRolls[0] + diceRolls[1];

		Square newLocation = location;
		TrackType newTrack = currentTrack;
		int newIndex = indexOnTrack;

		boolean isEven = sum % 2 == 0;
		boolean transitUsed = false;
		boolean isLastMove = false;

		while (true) {
			if (!transitUsed && isEven && newLocation instanceof TransitStation) {
				newIndex = ((TransitStation) newLocation).getOtherIndex(newTrack);
				newTrack = ((TransitStation) newLocation).getOtherTrack(newTrack);
				transitUsed = true;
			} else {
				newIndex = (newIndex + 1) % Board.getInstance().getNoOfSquaresOnTrack(newTrack);
				newLocation = Board.getInstance().getSquare(newIndex, newTrack);
				if (!isLastMove)
					newLocation.executeWhenPassed(this);
				sum--;
				transitUsed = false;
			}
			if (sum == 1)
				isLastMove = true;
			if (sum == 0)
				break;
		}

		message = "MOVE/" + playerIndex + "/" + currentTrack.ordinal() + "/" + indexOnTrack + "/" + newTrack.ordinal()
				+ "/" + newIndex;
		publishGameEvent(message);
		indexOnTrack = newIndex;
		currentTrack = newTrack;
		location = newLocation;
	}

	public Square getLocation() {
		return location;
	}

	public int getPosition() {
		return indexOnTrack;
	}

	public boolean isInJail() {
		return inJail;
	}

	public boolean isBankrupt() {
		return isBankrupt;
	}

	public ArrayList<Property> getProperties() {
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

	public boolean buySquare() {
		if (location instanceof Estate) {
			Estate estate = (Estate) location;
			if (estate.getOwner() == null) {
				estate.setOwner(this);
				reduceMoney(estate.getPrice());
				if(estate instanceof Property)
					properties.add((Property) estate);
				else if (estate instanceof TransitStation)
					transitStations.add((TransitStation) estate);
				else if (estate instanceof Utility)
					utilities.add((Utility) estate);
				message = "ACTION/" + estate.getName() + " is bought by " + name + "\n";
				publishGameEvent(message);
				updateState();
				return true;
			} else {
				message = "ACTION/" + "This square is owned by " + estate.getOwner().name + "\n";
				publishGameEvent(message);
				return false;
			}
		} else {
			message = "ACTION/" + "You can not own this square!";
			publishGameEvent(message);
			return false;
		}
	}

	public void goTo(TrackType track, int index) {
		message = "JUMP/" + playerIndex + "/" + track.ordinal() + "/" + index;
		publishGameEvent(message);
		indexOnTrack = index;
		currentTrack = track;
		location = Board.getInstance().getSquare(index, track);
	}

	public boolean buyBuilding(Building building, Property Property) {
		return false;
	}

	public void sellBuilding(Building building, Property Property) {

	}

	public void pickCard(Card card) {
		if (card instanceof CommunityChest) {
			((CommunityChest) card).executeAction(this);
			message = "ACTION/" + ((CommunityChest) card).getName() + "\n";
			publishGameEvent(message);
		}
		if (card instanceof Chance) {
			((Chance) card).executeAction(this);
			message = "ACTION/" + ((Chance) card).getName() + "\n";
			publishGameEvent(message);
		}
	}

	public boolean reduceMoney(int m) {
		if (money > m) {
			this.money -= m;
			updateState();
			return true;
		}
		return false;
	}

	public void increaseMoney(int m) {
		this.money += m;
		updateState();
	}

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}

	public ArrayList<TransitStation> getTransitStations() {
		return transitStations;
	}

	public void setRailRoadSquares(ArrayList<TransitStation> transitStations) {
		this.transitStations = transitStations;
	}

	public ArrayList<Utility> getUtilitySquares() {
		return utilities;
	}

	public void setUtilitySquares(ArrayList<Utility> utilities) {
		this.utilities = utilities;
	}

	public void setInJail() {
		this.inJail = true;
		updateState();
	}

	public void updateState() {
		message = "PLAYERDATA/";
		message += "Player Name: " + name + "/";
		message += "Player Money: " + money + "/";
		message += "Player Properties:/";
		int i = 1;
		for (Property property : properties) {
			message += i + "- " + property.getName() + "/";
			i++;
		}
	}

	public void setGoAnyWhere() {
		this.goAnyWhere = true;
	}
	
	public void endGame() {
		message = "REMOVEPIECE/"+playerIndex;
		publishGameEvent(message);
		message = "ACTION/" + name + " left the game.";
		publishGameEvent(message);
	}

	public void publishGameEvent(String message) {
		Controller.getInstance().publishGameEvent(message);
	}

	public int getInitialDiceOrder() {
		return initialDiceOrder;
	}

	public void setInitialDiceOrder(int initialDiceOrder) {
		this.initialDiceOrder = initialDiceOrder;
	}

	public String getColor() {
		return color;
	}

}
