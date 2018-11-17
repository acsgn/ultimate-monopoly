package game;

import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.Card;
import game.card.Chance;
import game.card.CommunityChest;
import game.dice.SingletonDice;
import game.square.Square;
import game.square.estate.*;
import network.NetworkFaçade;

public class Player {

	private static final int BEGIN_MONEY = 3200;
	private static final TrackType BEGIN_TRACK = TrackType.MIDDLE_TRACK;
	private static final int BEGIN_INDEX = 0;

	private String name;
	private String color;
	private int playerIndex = 0;
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

	private ArrayList<GameListener> listeners;
	private String message;

	public Player() {
		listeners = new ArrayList<GameListener>();
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

	public void createPiece() {
		message = "PIECE/" + BEGIN_TRACK.ordinal() + "/" + BEGIN_INDEX;
		publishGameEvent(message);
	}

	public void play() {
		int[] diceRolls = rollDice();
		message = "ACTION/";
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

	public int[] rollDice() {
		SingletonDice.getInstance().rollDice();
		return SingletonDice.getInstance().getFaceValues();
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

		message = "MOVE/" + playerIndex + "/" + newTrack.ordinal() + "/" + newIndex;
		publishGameEvent(message);
		indexOnTrack = newIndex;
		currentTrack = newTrack;
		location = newLocation;
		
		NetworkFaçade.getInstance().sendMessageToOthers(this.name+"/"+"MOVE"+"/"+diceRolls[0]+"/"+diceRolls[1]+"/"+diceRolls[2]);
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

	public boolean buySquare() {
		if (location instanceof Estate) {
			Estate estate = (Estate) location;
			if (estate.getOwner() == null) {
				estate.setOwner(this);
				reduceMoney(estate.getPrice());
				message = "ACTION/" + "ProperySquare" + estate.getName() + " is bought\n";
				publishGameEvent(message);
				updateState();
				NetworkFaçade.getInstance().sendMessageToOthers(this.name+"/"+"BUYESTATE");
				return true;
			} else {
				message = "ACTION/" + "Property: is owned by " + estate.getOwner().getName() + "\n";
				publishGameEvent(message);
				return false;
			}
		} else {
			message = "ACTION/" + "Sale Failed. It's not a property Square";
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
		int cardType = 0;
		if (card instanceof CommunityChest) {
			((CommunityChest) card).executeAction(this);
			message = "ACTION/ " + ((CommunityChest) card).getName();
			publishGameEvent(message);
			cardType = 1;
		}
		if (card instanceof Chance) {
			((Chance) card).executeAction(this);
			message = "ACTION/ " + ((Chance) card).getName();
			publishGameEvent(message);
			cardType = 0;
		}
		NetworkFaçade.getInstance().sendMessageToOthers(this.name+"/CARD/"+cardType);
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

	public String getName() {
		return name;
	}

	public List<Property> getPropertys() {
		return properties;
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

	public List<Utility> getUtilitySquares() {
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

	public void setGoAnyWhere() {
		this.goAnyWhere = true;
	}

	public void addGamelistener(GameListener lis) {
		listeners.add(lis);
	}

	public void publishGameEvent(String message) {
		for (GameListener l : listeners) {
			l.onGameEvent(message);
		}
	}

	public String getColor() {
		return color;
	}

}
