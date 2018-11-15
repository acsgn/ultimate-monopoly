package game;

import game.card.deed.TitleDeed;
import game.square.*;
import game.square.action.ChanceSquare;
import game.square.action.CommunityChestSquare;
import game.square.action.GoToJailSquare;
import game.square.paycorner.GoSquare;
import game.square.tradable.PropertySquare;

public class Board {

	private final int[] OUTER_TRACK_TRANSIT_LOCATIONS = { 21, 35 };
	private final int[] MIDDLE_TRACK_TRANSIT_LOCATIONS = { 5, 15, 25, 35 };
	private final int[] INNER_TRACK_TRANSIT_LOCATIONS = { 9, 21 };

	private Track outerTrack;
	private Track middleTrack;
	private Track innerTrack;
	private static Board board;

	private Board() {
		outerTrack = new Track(TrackType.OUTER_TRACK);
		middleTrack = new Track(TrackType.MIDDLE_TRACK);
		innerTrack = new Track(TrackType.INNER_TRACK);

		outerTrack.setTransitlocations(OUTER_TRACK_TRANSIT_LOCATIONS);
		middleTrack.setTransitlocations(MIDDLE_TRACK_TRANSIT_LOCATIONS);
		innerTrack.setTransitlocations(INNER_TRACK_TRANSIT_LOCATIONS);
		constructSquares();
	}

	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	public void constructSquares() {
		// mortgage values will be added.
		GoSquare go = new GoSquare("GO", 1);
		middleTrack.addSquare(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		middleTrack.addSquare(mediterraneanAve);
		CommunityChestSquare communityChest1middleTrack = new CommunityChestSquare("Community Chest", 3);
		middleTrack.addSquare(communityChest1middleTrack);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		middleTrack.addSquare(balticAve);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		// squares.add(incomeTax1);
		// Reading TransitStation-Railroad
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		middleTrack.addSquare(orientalAve);
		ChanceSquare chanceSquare1middleTrack = new ChanceSquare("Chance", 8);
		middleTrack.addSquare(chanceSquare1middleTrack);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		middleTrack.addSquare(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		middleTrack.addSquare(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		middleTrack.addSquare(jailSquare);
		TitleDeed stCharlesPlaceTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare stCharlesPlace = new PropertySquare("St. Charles Place", 12, 140, null);
		middleTrack.addSquare(stCharlesPlace);
//		Utility Squares will be modified.
//		UtilitySquare electricCompany = new UtilitySquare ("Electric Company", 13, 150, null);
//		middleTrack.addSquare(electricCompany);
		TitleDeed statesAveTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare statesAve = new PropertySquare("States Avenue", 14, 140, null);
		middleTrack.addSquare(statesAve);
		TitleDeed virginiaAveTD = new TitleDeed(12, 60, 180, 500, 700, 900, 1400, 80, 100, 100, 100);
		PropertySquare virginiaAve = new PropertySquare("Virginia Avenue", 15, 160, null);
		middleTrack.addSquare(virginiaAve);
		// Pennsylvania Railroad/TransitStation
		TitleDeed stJamesPlaceTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare stJamesPlace = new PropertySquare("St. James Place", 17, 180, null);
		middleTrack.addSquare(stJamesPlace);
		CommunityChestSquare communityChest2middleTrack = new CommunityChestSquare("Community Chest", 18);
		middleTrack.addSquare(communityChest2middleTrack);
		TitleDeed tennesseeAveTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare tennesseeAve = new PropertySquare("Tennessee Avenue", 19, 180, null);
		middleTrack.addSquare(tennesseeAve);
		TitleDeed newYorkAveTD = new TitleDeed(16, 80, 220, 600, 800, 1000, 1500, 100, 100, 100, 100);
		PropertySquare newYorkAve = new PropertySquare("New York Avenue", 20, 200, null);
		middleTrack.addSquare(newYorkAve);
		FreeParkingSquare freeParking1middleTrack = new FreeParkingSquare("Free Parking", 21);
		TitleDeed kentuckyAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare kentuckyAve = new PropertySquare("Kentucky Avenue", 22, 220, null);
		middleTrack.addSquare(kentuckyAve);
		ChanceSquare chanceSquare2middleTrack = new ChanceSquare("Chance", 23);
		middleTrack.addSquare(chanceSquare2middleTrack);
		TitleDeed indianaAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare indianaAve = new PropertySquare("Indiana Avenue", 24, 220, null);
		middleTrack.addSquare(indianaAve);
		TitleDeed illinoisAveTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 210, 120, 150, 150, 150);
		PropertySquare illinoisAve = new PropertySquare("Illinois Avenue", 25, 240, null);
		middleTrack.addSquare(illinoisAve);
		// B&O TransitStation/Railroad
		TitleDeed atlanticAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare atlanticAve = new PropertySquare("Atlantic Avenue", 27, 260, null);
		middleTrack.addSquare(atlanticAve);
		TitleDeed ventnorAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare ventnorAve = new PropertySquare("Ventnor Avenue", 28, 260, null);
		middleTrack.addSquare(ventnorAve);
//		UtilitySquare waterWorks = new UtilitySquare ("Water Works", 29, 150, null);
//		middleTrack.addSquare(waterWorks);
		TitleDeed marvinGardensTD = new TitleDeed(24, 120, 350, 850, 1025, 1200, 2200, 140, 150, 150, 150);
		PropertySquare marvinGardens = new PropertySquare("Marvin Gardens", 30, 280, null);
		middleTrack.addSquare(marvinGardens);
		GoToJailSquare goToJail = new GoToJailSquare("Go to Jail", 31);
		middleTrack.addSquare(goToJail);

		TitleDeed pacificAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare pacificAve = new PropertySquare("Pacific Avenue", 32, 300, null);
		middleTrack.addSquare(pacificAve);
		TitleDeed noCarolinaAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare noCarolinaAve = new PropertySquare("North Carolina Avenue", 33, 300, null);
		middleTrack.addSquare(noCarolinaAve);
		CommunityChestSquare communityChest3middleTrack = new CommunityChestSquare("Community Chest", 34);
		middleTrack.addSquare(communityChest3middleTrack);
		TitleDeed pennsylvaniaAveTD = new TitleDeed(28, 150, 450, 1000, 1200, 1400, 2400, 160, 200, 200, 200);
		PropertySquare pennsylvaniaAve = new PropertySquare("Pennsylvania Avenue", 35, 300, null);
		middleTrack.addSquare(pennsylvaniaAve);
		// Short Line Railroad/TransitStation
		ChanceSquare chanceSquare3middleTrack = new ChanceSquare("Chance", 37);
		middleTrack.addSquare(chanceSquare3middleTrack);
		TitleDeed parkPlaceTD = new TitleDeed(35, 175, 500, 1100, 1300, 1500, 2500, 200, 200, 200, 200);
		PropertySquare parkPlace = new PropertySquare("Park Place", 38, 350, null);
		middleTrack.addSquare(parkPlace);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 39);
		// squares.add(incomeTax1);
		TitleDeed boardwalkTD = new TitleDeed(50, 200, 600, 1400, 1700, 2000, 3000, 200, 200, 200, 200);
		PropertySquare boardwalk = new PropertySquare("Boardwalk", 40, 400, null);
		middleTrack.addSquare(boardwalk);
		// tryy

	}

	public Square getSquare(int index, TrackType type) {
		return getTrack(type).getSquare(index);
	}

	public int getNoOfSquaresOnTrack(TrackType type) {
		return getTrack(type).getSquareNumber();
	}

	public int[] getTransitStationLocationsOnTrack(TrackType type) {
		Track track = getTrack(type);
		return track.getTransitlocations();
	}

	private Track getTrack(TrackType type) {
		if (type == outerTrack.getTrackType())
			return outerTrack;
		else if (type == middleTrack.getTrackType())
			return middleTrack;
		else
			return innerTrack;
	}

}
