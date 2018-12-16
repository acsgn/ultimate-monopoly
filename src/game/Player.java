package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.building.Building;
import game.card.ActionCards;
import game.card.Card;
import game.card.Chance;
import game.card.CommunityChest;
import game.card.RollThree;
import game.dice.SingletonDice;
import game.square.Square;
import game.square.estate.*;
import network.NetworkFacade;

public class Player implements Serializable{

	private static final int BEGIN_MONEY = 3200;
	private static final TrackType BEGIN_TRACK = TrackType.MIDDLE_TRACK;
	private static final int BEGIN_INDEX = 0;

	private static int playerIndexCounter = 0;

	private String name;
	private String color;
	private int playerIndex;
	private int money;

	private ArrayList<Chance> ChanceCards;
	
	//needed for chance cards
	private int totalHouses = 0;
	private int totalHotels = 0;
	private int totalSkyscrapers = 0;
	
	public void addHouse() {
		totalHouses++;
	}
	
	public int getMoney(){
		return money;
	}
	
	public int getTotalTransitStations() {
		int size = transitStations.size();
		return size;
		
	}
	
	public void addHotel() {
		
		totalHotels++;
	}
	
	public void buyHouse()
	{
		addHouse();
	}
	
	public void buyHotel(){
		addHotel();
	}
	
	public void buySkyscraper(){
		addSkyscaper();
	}
	
	public int getTotalHouses() {
		//total num of houses
		return totalHouses;
	}

	public void setTotalHouses(int totalHouses) {
		this.totalHouses = totalHouses;
	}

	public int getTotalHotels() {
		//total num of hotels
		return totalHotels;
	}

	public void setTotalHotels(int totalHotels) {
		this.totalHotels = totalHotels;
	}

	public int getTotalSkyscrapers() {
		//total num of sky
		return totalSkyscrapers;
	}

	public void setTotalSkyscrapers(int totalSkyscrapers) {
		this.totalSkyscrapers = totalSkyscrapers;
	}

	public void addSkyscaper() {
		//added as sky is bought
		totalSkyscrapers++;
	}

	public void addCard(Chance chance) {
		ChanceCards.add(chance);
	}

	public ArrayList<Chance> getChanceCards() {
		return ChanceCards;
	}

	public void setChanceCards(ArrayList<Chance> chanceCards) {
		ChanceCards = chanceCards;
	}

	// roll 3 field
	private Card roll3card;

	private TrackType currentTrack;
	private int indexOnTrack;
	private Square location;

	boolean inJail;
	boolean isBankrupt;
	boolean goAnyWhere;

	private ArrayList<Property> properties;
	private ArrayList<TransitStation> transitStations;
	private ArrayList<Utility> utilities;

	private Board board;

	private String message;

	public Player(Board board) {
		this.board = board;
		money = BEGIN_MONEY;
		currentTrack = BEGIN_TRACK;
		indexOnTrack = BEGIN_INDEX;
		playerIndex = playerIndexCounter;
		playerIndexCounter++;
		location = board.getSquare(indexOnTrack, currentTrack);
		properties = new ArrayList<>();
	}

	public void setName(String name) {
		this.name = name;
		message = "NAME/" + name;
		publishGameEvent(message);
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
		NetworkFacade.getInstance()
		.sendMessageToOthers(this.name + "/MOVE/" + diceRolls[0] + "/" + diceRolls[1] + "/" + diceRolls[2]);
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
		sendColor();
		int sum = diceRolls[0] + diceRolls[1];

		Square newLocation = location;
		TrackType newTrack = currentTrack;
		int newIndex = indexOnTrack;
		// whether to add or subtract 1
		int i = 1;
		if (sum < 0) {
			i = -1;
		}

		boolean isEven = sum % 2 == 0;
		boolean transitUsed = false;
		boolean isLastMove = false;

		// TODO I have attempted adjustments to allow moving backward. Must test for bugs.
		while (true) {
			if (!transitUsed && isEven && newLocation instanceof TransitStation) {
				newIndex = ((TransitStation) newLocation).getOtherIndex(newTrack);
				newTrack = ((TransitStation) newLocation).getOtherTrack(newTrack);
				transitUsed = true;
			} else {
				newIndex = (newIndex + i) % board.getNoOfSquaresOnTrack(newTrack);
				newLocation = board.getSquare(newIndex, newTrack);
				if (!isLastMove)
					newLocation.executeWhenPassed(this);

				if (sum > 0) {
					sum--;
				} else if (sum < 0) { sum++; }

				transitUsed = false;
			}
			if (sum == 1 || sum == -1)
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


	// get roll3
	public Card getRoll3() {
		return roll3card;
	}

	// get current track
	public TrackType getCurrentTrack() {
		return currentTrack;
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
		if (location instanceof Property) {
			Property property = (Property) location;
			if (property.getOwner() == null) {
				property.setOwner(this);
				reduceMoney(property.getPrice());
				properties.add(property);
				message = "ACTION/" + property.getName() + " is bought by " + this.getName() + "\n";
				publishGameEvent(message);
				updateState();
				return true;
			} else {
				message = "ACTION/" + "Property: is owned by " + property.getOwner().getName() + "\n";
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
		location = board.getSquare(index, track);
	}

	public void buyBuilding(Building building, Property Property) {
		Property.getBuildings().add(building);
	}

	public void sellBuilding(Building building, Property Property) {
		Property.getBuildings().remove(building);
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
		if (card instanceof RollThree) {
			((RollThree) card).executeAction(this);
			message = "ACTION/ Roll Three\n";
		}
	}

	public boolean reduceMoney(int m) {
		if (money > m) {
			this.money -= m;
			//updateState();
			return true;
		}
		return false;
	}

	public void increaseMoney(int m) {
		this.money += m;
		//updateState();
	}

	public String getName() {
		return name;
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

	public void sendToJail() {
		this.inJail = true;
		//updateState();
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
		publishGameEvent(message);
		NetworkFacade.getInstance().sendMessageToOthers(name + "/UPDATESTATE/" + message);
	}

	public void setGoAnyWhere() {
		this.goAnyWhere = true;
	}

	public void endGame() {
		message = "REMOVEPIECE/"+playerIndex;
		publishGameEvent(message);
	}

	public void publishGameEvent(String message) {
		Controller.getInstance().publishGameEvent(message);
	}

	public String getColor() {
		return color;
	}

}
