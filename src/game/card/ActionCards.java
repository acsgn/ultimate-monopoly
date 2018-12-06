package game.card;

import java.util.ArrayList;

public class ActionCards {
	
	private static ActionCards actionCards;
	
	private ArrayList<Chance> chance;
	private ArrayList<CommunityChest> communityChest;
	private ArrayList<RollThree> rollThree;
	
	private ActionCards(){
		constructCommunityChestCards();
		constructChanceCards();
	}
	public static ActionCards getInstance(){
		if(actionCards==null){
			actionCards = new ActionCards();
		}
		return actionCards;
	}
	
	private void constructCommunityChestCards(){
		communityChest = new ArrayList<>();
		
		CommunityChest happyBirthday = new CommunityChest("Happy Birthday");
		CommunityChest gameNight = new CommunityChest("Game Night");
		CommunityChest aMovingExperience = new CommunityChest("A Moving Experience");
		CommunityChest houseCondemned = new CommunityChest("House Condemned");
		
		CommunityChest beKindRewind = new CommunityChest("Be Kind, Rewind");
		CommunityChest payHospitalBills = new CommunityChest("Pay Hospital Bills");
		CommunityChest tornadoHits = new CommunityChest("Tornado Hits!");
		CommunityChest theInsidersEdge = new CommunityChest("The Insider's Edge");
		
		communityChest.add(happyBirthday);
		communityChest.add(gameNight);
		communityChest.add(aMovingExperience);
		communityChest.add(houseCondemned);
		
		communityChest.add(beKindRewind);
		communityChest.add(payHospitalBills);
		communityChest.add(tornadoHits);
		communityChest.add(theInsidersEdge);
	
	}
	private void constructChanceCards(){
		chance = new ArrayList<>();
		
		Chance advanceToThePayCorner = new Chance("Advance to the Pay Corner");
		Chance advanceToTheNearestRailroad = new Chance("Advance to the Nearest Railroad");
		Chance getOutOfJailFree = new Chance("Get out of Jail Free");
		Chance advanceToSaintCharlesPlace = new Chance("Advance to Saint Charles Place");
		Chance goToJail = new Chance("Go to Jail!");
		Chance makeGeneralRepairsToAllYourProperties = new Chance("Make General Repairs to all your properties");
		Chance holidayBonus = new Chance("Holiday Bonus!");
		Chance buyersmarket = new Chance("Buyer's Market");
		Chance ForeclosedPropertySale = new Chance("Foreclosed Property Sale!");
		Chance SeeYouInCourt = new Chance("See you in Court");

		Chance forwardThinker = new Chance("Forward Thinker");
		Chance propertyTaxes = new Chance("Property Taxes");
		Chance getRollin = new Chance("Get Rollin'");
		Chance hurricaneMakesLandfall = new Chance("Hurricane makes landfall!");
		Chance rideTheSubway = new Chance("Ride the Subway");
		Chance socialMediaFail = new Chance("Social Media Fail!");
		Chance payBack = new Chance("Pay Back!");
		Chance mardiGras = new Chance("Mardi Gras!");
		Chance GPSisNotWorking = new Chance("GPS is not working");
		Chance zeroDollarsDown = new Chance("zeroDollarsDown!");
		Chance changingLanesAbove = new Chance("Changin Lanes Above");
		Chance changingLanesBelow = new Chance("Changin Lanes Below");
		
		chance.add(advanceToThePayCorner);
		chance.add(advanceToTheNearestRailroad);
		chance.add(getOutOfJailFree);
		chance.add(advanceToSaintCharlesPlace);
		chance.add(goToJail);
		chance.add(makeGeneralRepairsToAllYourProperties);
		chance.add(holidayBonus);
		chance.add(buyersmarket);
		chance.add(ForeclosedPropertySale);
		chance.add(SeeYouInCourt);
		chance.add(forwardThinker);
		chance.add(propertyTaxes);
		chance.add(getRollin);
		chance.add(hurricaneMakesLandfall);
		chance.add(rideTheSubway);
		chance.add(socialMediaFail);
		chance.add(payBack);
		chance.add(mardiGras);
		chance.add(GPSisNotWorking);
		chance.add(zeroDollarsDown);
		chance.add(changingLanesAbove);
		chance.add(changingLanesBelow);
	}
	public CommunityChest getCommunityChestCard(){
		CommunityChest tmp = communityChest.remove(0);
		communityChest.add(tmp);
		return tmp;
	}
	public Chance getChanceCard(){
		Chance tmp = chance.remove(0);
		chance.add(tmp);
		return tmp;
	}
	
	public void putTheCardBack(Card card) {

	}
	
}
