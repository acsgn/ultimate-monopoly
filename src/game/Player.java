package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import game.building.Building;
import game.building.Hotel;
import game.building.House;
import game.building.Skyscraper;
import game.card.Card;
import game.card.Chance;
import game.card.CommunityChest;
import game.card.RollThree;
import game.square.Square;
import game.square.estate.*;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final int BEGIN_MONEY = 3200;
	private static final TrackType BEGIN_TRACK = TrackType.MIDDLE_TRACK;
	private static final int BEGIN_INDEX = 0;

	private String name;
	private String color;
	private int money;

	private Board board;
	private Square location;
	private TrackType currentTrack;
	private int indexOnTrack;

	private ArrayList<Property> properties;
	private ArrayList<TransitStation> transitStations;
	private ArrayList<Utility> utilities;

	private ArrayList<ColorGroup> monopolyColorGroups;

	private int totalHouses = 0;
	private int totalHotels = 0;
	private int totalSkyscrapers = 0;

	private int initialDiceOrder;
	private String message;

	private ArrayList<Chance> ChanceCards;
	private Card roll3card;

	private boolean inJail;
	private boolean isBankrupt;
	private boolean goAnyWhere;
	private boolean isBot = false;

	private int jailCounter = 0;
	private static final int jailBail = 50;

	public Player(String name, String color) {
		board = Board.getInstance();
		this.name = name;
		this.color = color;
		money = BEGIN_MONEY;
		currentTrack = BEGIN_TRACK;
		indexOnTrack = BEGIN_INDEX;
		location = board.getSquare(indexOnTrack, currentTrack);
		properties = new ArrayList<>();
		transitStations = new ArrayList<>();
		utilities = new ArrayList<>();

		monopolyColorGroups = new ArrayList<>();
		publishGameEvent("PLAYER/" + name);
	}

	public void addMonopolyGroup(ColorGroup colorGroup) {
		boolean exist = false;
		for (ColorGroup c : monopolyColorGroups) {
			if (c.getColor() == colorGroup.getColor()) {
				exist = true;
			}
		}
		if (!exist)
			monopolyColorGroups.add(colorGroup);
	}

	public String getName() {
		return name;
	}

	public void sendColor() {
		message = "COLOR/" + color;
		publishGameEvent(message);
	}

	public void createPiece() {
		message = "PIECE/" + name + "/" + color + "/" + currentTrack.ordinal() + "/" + indexOnTrack;
		publishGameEvent(message);
	}

	public void play(int[] diceRolls) {
		sendColor();
		message = "ACTION/";
		message += name + " rolled:";
		message += "Regular Die 1: " + diceRolls[0] + "\n";
		message += "Regular Die 2: " + diceRolls[1] + "\n";
		if (diceRolls[2] == 4) {
			message += "Speed Die : Bus Icon";
		} else if (diceRolls[2] == 5) {
			message += "Speed Die : Mr.Monopoly Bonus Move";
		} else {
			message += "Speed Die: " + diceRolls[2];
		}
		publishGameEvent(message);
		if (!inJail) {
			move(diceRolls);
			updateState();
			location.executeWhenLanded(this);
		} else {
			if (jailCounter < 3) {
				jailCounter++;
				if (diceRolls[0] == diceRolls[1]) {
					jailCounter = 0;
					inJail = false;
					move(diceRolls);
					updateState();
					location.executeWhenLanded(this);
					message = "ACTION/" + name + " rolled Doubles and got out of Jail";
					publishGameEvent(message);
				} else {
					if (jailCounter == 3) {
						this.payBail();
						move(diceRolls);
						updateState();
						location.executeWhenLanded(this);
						message = "ACTION/" + name + " paid Bail";
						publishGameEvent(message);
					}
				}
			}
		}
	}

	public void move(int[] diceRolls) {
		// Mr.Monopoly AND Bus Icon will be handled in the nest phase
		// Now we just sum the first two regular dice/
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

		// TODO I have attempted adjustments to allow moving backward. Must test
		// for bugs.
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
				} else if (sum < 0) {
					sum++;
				}

				transitUsed = false;
			}
			if (sum == 1 || sum == -1)
				isLastMove = true;
			if (sum == 0)
				break;
		}

		message = "MOVE/" + name + "/" + currentTrack.ordinal() + "/" + indexOnTrack + "/" + newTrack.ordinal() + "/"
				+ newIndex;
		publishGameEvent(message);
		indexOnTrack = newIndex;
		currentTrack = newTrack;
		location = newLocation;
	}

	public Square getLocation() {
		return location;
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

	public ArrayList<Property> getProperties() {
		return this.properties;
	}

	/**
	 * @overview This function gets the rent price of the estate square and reduces
	 *           the player's money in that amount.
	 * @requires input Square to be an Estate.
	 * @modifies Player's money field, reduces it for the amount of rent.
	 * @effects Player, input Square, and the Player who owns the square.
	 * @param s the square the player lands on
	 * @return the reduceMoney function which returns a boolean depending on the
	 *         success of the transaction
	 */
	public int payRent() {
		int rent = ((Estate) location).getRent();
		reduceMoney(rent);
		message = "ACTION/" + name + "paid rent: " + rent + " to player " + ((Estate) location).getOwner().getName();
		publishGameEvent(message);
		return rent;
	}

	/**
	 * @overview This function pays the bail amount to the pool and reduces the
	 *           player's money in that amount
	 * @requires
	 * @modifies Player's money and Pool's amount fields.
	 * @effects Player, Pool.
	 * @param amount the bail price to be paid
	 * @return the reduceMoney function which returns a boolean depending on the
	 *         success of the transaction
	 */
	public boolean payBail() {
		Pool.getInstance().payToPool(jailBail);
		this.inJail = false;
		this.jailCounter = 0;
		return reduceMoney(jailBail);
	}

	public boolean buySquare() {
		if (location instanceof Estate) {
			Estate estate = (Estate) location;
			if (estate.getOwner() == null) {
				estate.setOwner(this);
				reduceMoney(estate.getPrice());
				if (estate instanceof Property)
					properties.add((Property) estate);
				else if (estate instanceof TransitStation)
					transitStations.add((TransitStation) estate);
				else if (estate instanceof Utility)
					utilities.add((Utility) estate);
				message = "ACTION/" + estate.getName() + " is bought by " + name;
				publishGameEvent(message);
				updateState();
				return true;
			} else {
				message = "ACTION/" + "This square is owned by " + estate.getOwner().name;
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
		message = "JUMP/" + name + "/" + track.ordinal() + "/" + index;
		publishGameEvent(message);
		indexOnTrack = index;
		currentTrack = track;
		location = board.getSquare(index, track);
	}

	/**
	 * @overview This function adds the building to the property chosen
	 * @requires
	 * @modifies Property's buildings field by expanding it.
	 * @effects Property, Property's owner Player if applicable.
	 * @param building the building to be added to the property
	 * @param Property the property that will get the building
	 */
	public void buyBuilding(String info) {
		String[] parsed = info.split("/");
		// get the square
		Property targetedSquare = null;
		ColorGroup colorGroup = null;
		for (ColorGroup c : monopolyColorGroups) {
			for (Property p : c.getPropertyColorSquares()) {
				if (p.getName().equals(parsed[0])) {
					targetedSquare = p;
					colorGroup = c;
					break;
				}
			}
		}
		switch (parsed[1]) {
		case "FOUR_HOUSE_LEVEL":
			targetedSquare.dropBuildings();
			targetedSquare.addBuilding(new Hotel());
			reduceMoney(targetedSquare.getTitleDeed().getHotelCost());
			message = "ACTION/";
			message += "Hotel is built on Property: " + targetedSquare.getName() + " owned by " + this.getName();
			publishGameEvent(message);
			break;
		case "HOTEL_LEVEL":
			targetedSquare.dropBuildings();
			targetedSquare.addBuilding(new Skyscraper());
			reduceMoney(targetedSquare.getTitleDeed().getSkyscrapperCost());
			message = "ACTION/";
			message += "Skyscraper is built on Property: " + targetedSquare.getName() + " owned by " + this.getName();
			publishGameEvent(message);
			break;
		default:
			targetedSquare.addBuilding(new House());
			reduceMoney(targetedSquare.getTitleDeed().getHouseCost());
			message = "ACTION/";
			message += "House is built on Property: " + targetedSquare.getName() + " owned by " + this.getName();
			publishGameEvent(message);
			break;
		}
		colorGroup.updateLevel();
	}

	public void buyBuildingChooseSquare(String info) {
		String[] parsed = info.split("/");
		ColorGroup currentColorGroup = null;
		for (ColorGroup c : monopolyColorGroups) {
			if (c.getColor().toString().equals(parsed[0])) {
				currentColorGroup = c;
			}
		}
		ArrayList<Property> availableProperties = currentColorGroup.getAvailableSquares();
		message = "BUILDING2/";
		if (availableProperties.size() != 0) {
			for (Property p : availableProperties) {
				message += p.getName() + "/";
			}
			message += currentColorGroup.getLevel().toString();
		} else {
			message += "NO/This color Group has reached its maximum capacity. You may choose another one";
		}
		publishGameEvent(message);
	}

	public void buyBuildingAction() {
		message = "BUILDING/";
		if (monopolyColorGroups.size() == 0) {
			message += "NO/";
			message += "There are no monopolies or majorities you have";
		} else {
			message += "YES/";
			for (ColorGroup c : monopolyColorGroups) {
				message += c.getColor() + " GROUP. Level: " + c.getLevel() + "/";
			}
		}
		publishGameEvent(message);
		
		// To be deleted. Just for testing.
		//ColorGroup c = board.getTestColorGroup();
		//int i = 0;
		//for (Property k : c.getPropertyColorSquares()) {
		//	i++;
		//	if (i == 4)
		//		break;
		//	k.setOwner(this);
		//}
		//
	}

	public void addHouse() {
		totalHouses++;
	}

	public int getMoney() {
		return money;
	}

	public int getTotalTransitStations() {
		int size = transitStations.size();
		return size;
	}

	public void addHotel() {
		totalHotels++;
	}

	public void buyHouse() {
		addHouse();
	}

	public void buyHotel() {
		addHotel();
	}

	public void buySkyscraper() {
		addSkyscaper();
	}

	public int getTotalHouses() {
		return totalHouses;
	}

	public void setTotalHouses(int totalHouses) {
		this.totalHouses = totalHouses;
	}

	public int getTotalHotels() {
		return totalHotels;
	}

	public void setTotalHotels(int totalHotels) {
		this.totalHotels = totalHotels;
	}

	public int getTotalSkyscrapers() {
		return totalSkyscrapers;
	}

	public void setTotalSkyscrapers(int totalSkyscrapers) {
		this.totalSkyscrapers = totalSkyscrapers;
	}

	public void addSkyscaper() {
		totalSkyscrapers++;
	}

	public void addCard(Chance chance) {
		ChanceCards.add(chance);
	}

	public Card getRoll3() {
		return roll3card;
	}

	public ArrayList<Chance> getChanceCards() {
		return ChanceCards;
	}

	public void setChanceCards(ArrayList<Chance> chanceCards) {
		ChanceCards = chanceCards;
	}

	/**
	 * @overview This function removes the building from the property chosen
	 * @requires input building to be included in Property, and Property to have
	 *           buildings in the first place.
	 * @modifies Property's buildings field, Property's owner's money field.
	 * @effects Property, Property's owner
	 * @param building the building that will be removed from the property
	 * @param Property the property that will have its building removed
	 */
	public void sellBuilding(Building building, Property Property) {
		Property.getBuildings().remove(building);
	}

	public void pickCard(Card card) {
		if (card instanceof CommunityChest) {
			((CommunityChest) card).executeAction(this);
			message = "ACTION/" + ((CommunityChest) card).getName();
			publishGameEvent(message);
		}
		if (card instanceof Chance) {
			((Chance) card).executeAction(this);
			message = "ACTION/" + ((Chance) card).getName();
			publishGameEvent(message);
		}
		if (card instanceof RollThree) {
			((RollThree) card).executeAction(this);
			message = "ACTION/Roll Three";
		}
	}

	/**
	 * @overview This function reduces the money of the player in the given amount
	 * @requires
	 * @modifies Player's money field.
	 * @effects Player.
	 * @param m the input amount to be reduced from the money
	 * @return true if the transaction if successful and false if not
	 */
	public boolean reduceMoney(int m) {
		if (money > m) {
			this.money -= m;
			updateState();
			return true;
		}
		return false;
	}

	/**
	 * @overview This function increments the money of the player in the given
	 *           amount
	 * @requires
	 * @modifies Player's money field.
	 * @effects Player.
	 * @param m the input amount to be added to the money
	 */
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

	public void sendToJail() {
		this.inJail = true;
		this.goTo(TrackType.MIDDLE_TRACK, 10);
		updateState();
	}

	public void updateState() {
		sendColor();
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
	}

	public void setGoAnyWhere() {
		this.goAnyWhere = true;
	}

	public void endGame() {
		message = "REMOVEPIECE/" + name;
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

	public int getIndexOnTrack() {
		return indexOnTrack;
	}

	public boolean repOk() {
		if (money < 0 || indexOnTrack > 56)
			return false;
		if (board == null)
			return false;

		return true;
	}

	public boolean isBot() {
		return isBot;
	}

	public void setBot() {
		isBot = true;
	}

	public void delegateTask(String mess) {
		Controller.getInstance().dispatchMessage("PLAYER/" + mess);
	}

	public ArrayList<ColorGroup> getMonopolyColorGroups() {
		return monopolyColorGroups;
	}

	public void doHurricaneAction(Hashtable<String, ArrayList<String>> data) {
		message = "CARD/HURRICANE/CHOOSEPLAYER/";
		for (String name : data.keySet()) {
			message += name + "/";
			for (String val : data.get(name)) {
				message += val + "/";
			}
			message += "END/";
		}
		publishGameEvent(message);
	}

	public void executeHurricaneAction(Player player, String color) {
		for (ColorGroup c : player.getMonopolyColorGroups()) {
			if (c.getColor().toString().equals(color)) {
				c.decreaseLevel();
				message = "ACTION/Color Group " + color + " level was decreased";
				publishGameEvent(message);
			}
		}
	}

}
