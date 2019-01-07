package game.card;

import java.util.ArrayList;
import java.util.Random;

public class ActionCards {

	private static ActionCards actionCards;

	private ArrayList<Chance> chance;
	private ArrayList<CommunityChest> communityChest;
	//private ArrayList<RollThree> rollThree;

	private int chanceLastIndex;
	private int communityLastIndex;

	private ActionCards() {
		constructCommunityChestCards();
		constructChanceCards();
	}

	public static ActionCards getInstance() {
		if (actionCards == null) {
			actionCards = new ActionCards();
		}
		return actionCards;
	}
	/*
	 * private void constructRollThreeCards(){ rollThree = new ArrayList<>(); //some
	 * cards are implemented twice, it is correct RollThree card1 = new
	 * RollThree(1,2,3); RollThree card2 = new RollThree(1,2,4); RollThree card3 =
	 * new RollThree(1,2,5); RollThree card4 = new RollThree(1,2,6); RollThree card5
	 * = new RollThree(1,3,4); RollThree card6 = new RollThree(1,3,5); RollThree
	 * card7 = new RollThree(1,3,6); RollThree card8 = new RollThree(1,4,5);
	 * RollThree card9 = new RollThree(1,4,6); RollThree card10 = new
	 * RollThree(1,5,6); RollThree card11 = new RollThree(2,3,4); RollThree card12 =
	 * new RollThree(2,4,5); RollThree card13 = new RollThree(2,4,6); RollThree
	 * card14 = new RollThree(2,5,6); RollThree card15 = new RollThree(3,4,5);
	 * RollThree card16 = new RollThree(3,4,6); RollThree card17 = new
	 * RollThree(3,5,6); RollThree card18 = new RollThree(4,5,6); RollThree card19 =
	 * new RollThree(2,4,6); RollThree card20 = new RollThree(2,5,6); RollThree
	 * card21 = new RollThree(3,4,5); RollThree card22 = new RollThree(3,4,6);
	 * RollThree card23 = new RollThree(3,5,6); RollThree card24 = new
	 * RollThree(4,5,6);
	 * 
	 * rollThree.add(card1);rollThree.add(card2);rollThree.add(card3);rollThree.add(
	 * card4);
	 * rollThree.add(card5);rollThree.add(card6);rollThree.add(card7);rollThree.add(
	 * card8);
	 * rollThree.add(card9);rollThree.add(card10);rollThree.add(card11);rollThree.
	 * add(card12);
	 * rollThree.add(card13);rollThree.add(card14);rollThree.add(card15);rollThree.
	 * add(card16);
	 * rollThree.add(card17);rollThree.add(card18);rollThree.add(card19);rollThree.
	 * add(card20);
	 * rollThree.add(card21);rollThree.add(card22);rollThree.add(card23);rollThree.
	 * add(card24); }
	 */

	private void constructCommunityChestCards() {
		communityChest = new ArrayList<>();

		// CommunityChest happyBirthday = new CommunityChest("Happy Birthday");
		// CommunityChest gameNight = new CommunityChest("Game Night");
		// CommunityChest aMovingExperience = new CommunityChest("A Moving Experience");
		// CommunityChest houseCondemned = new CommunityChest("House Condemned");

		// CommunityChest beKindRewind = new CommunityChest("Be Kind, Rewind");
		CommunityChest payHospitalBills = new CommunityChest("Pay Hospital Bills");
		// CommunityChest tornadoHits = new CommunityChest("Tornado Hits!");
		CommunityChest theInsidersEdge = new CommunityChest("The Insider's Edge");

		/*
		 * communityChest.add(happyBirthday); communityChest.add(gameNight);
		 * communityChest.add(aMovingExperience); communityChest.add(houseCondemned);
		 */

		// communityChest.add(beKindRewind);
		communityChest.add(payHospitalBills);
		// communityChest.add(tornadoHits);
		communityChest.add(theInsidersEdge);

	}

	private void constructChanceCards() {
		chance = new ArrayList<>();

		// Chance advanceToThePayCorner = new Chance("Advance to the Pay Corner", true);
		// Chance advanceToTheNearestRailroad = new Chance("Advance to the Nearest
		// Railroad", true);
		// Chance getOutOfJailFree = new Chance("Get out of Jail Free", false);
		// Chance advanceToSaintCharlesPlace = new Chance("Advance to Saint Charles
		// Place", true);
		// Chance goToJail = new Chance("Go to Jail!", true);
		// Chance makeGeneralRepairsToAllYourProperties = new Chance("Make General
		// Repairs to all your properties", true);
		Chance holidayBonus = new Chance("Holiday Bonus!", true);
		// Chance buyersmarket = new Chance("Buyer's Market", true);
		// Chance ForeclosedPropertySale = new Chance("Foreclosed Property Sale!",
		// true);
		// Chance SeeYouInCourt = new Chance("See you in Court", true);

		// Chance forwardThinker = new Chance("Forward Thinker", true);
		Chance propertyTaxes = new Chance("Property Taxes", true);
		// Chance getRollin = new Chance("Get Rollin'", true);
		// Chance hurricaneMakesLandfall = new Chance("Hurricane makes landfall!",
		// true);
		// Chance rideTheSubway = new Chance("Ride the Subway", true);
		// Chance socialMediaFail = new Chance("Social Media Fail!", true);
		// Chance payBack = new Chance("Pay Back!", true);
		// Chance mardiGras = new Chance("Mardi Gras!", true);
		// Chance GPSisNotWorking = new Chance("GPS is not working", true);
		Chance zeroDollarsDown = new Chance("Zero Dollars Down", true);
		// Chance changingLanesAbove = new Chance("Changin Lanes Above", true);
		// Chance changingLanesBelow = new Chance("Changin Lanes Below", true);

		/*
		 * chance.add(advanceToThePayCorner); chance.add(advanceToTheNearestRailroad);
		 * chance.add(getOutOfJailFree); chance.add(advanceToSaintCharlesPlace);
		 * chance.add(goToJail); chance.add(makeGeneralRepairsToAllYourProperties);
		 */
		chance.add(holidayBonus);
		/*
		 * chance.add(buyersmarket); chance.add(ForeclosedPropertySale);
		 * chance.add(SeeYouInCourt); chance.add(forwardThinker);
		 */
		chance.add(propertyTaxes);
		/*
		 * chance.add(getRollin); chance.add(hurricaneMakesLandfall);
		 * chance.add(rideTheSubway); chance.add(socialMediaFail); chance.add(payBack);
		 * chance.add(mardiGras); chance.add(GPSisNotWorking);
		 */
		chance.add(zeroDollarsDown);
		/*
		 * chance.add(changingLanesAbove); chance.add(changingLanesBelow);
		 */

		// Hurricane card.
		Chance hurricane = new Chance("Hurricane", true);
		chance.add(hurricane);
	}

	public void getCommunityChestCard() {
		Random d = new Random();
		int k = d.nextInt(communityChest.size());
		communityLastIndex = k;
		// CommunityChest tmp = communityChest.remove(k);
		// communityChest.add(tmp);
	}

	public void getChanceCard() {
		Random d = new Random();
		int k = d.nextInt(chance.size());
		chanceLastIndex = k;
		// Chance tmp = chance.remove(k);
		// chance.add(tmp);
	}

	public int getIndexChanceCard() {
		return chanceLastIndex;
	}

	public int getIndexCommunityCard() {
		return communityLastIndex;
	}

	public Card getChanceCardByIndex(int i) {
		Chance tmp = chance.remove(i);
		chance.add(tmp);
		return tmp;
	}

	public Card getCommunityCardByIndex(int i) {
		CommunityChest tmp = communityChest.remove(i);
		communityChest.add(tmp);
		return tmp;
	}

	public void putTheCardBack(Card card) {

	}

}
