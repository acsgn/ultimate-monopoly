package game;

import game.card.deed.TitleDeed;
import game.square.*;
import game.square.action.ChanceSquare;
import game.square.action.CommunityChestSquare;
import game.square.action.GoToJailSquare;
import game.square.paycorner.GoSquare;
import game.square.tradable.PropertySquare;

public class Board {

	private final int OUTER_TRACK = 1;
	private final int MIDDLE_TRACK = 2;
	private final int INNER_TRACK = 3;

	private final int[] OUTER_TRACK_TRANSIT_LOCATIONS = { 21, 35 };
	private final int[] MIDDLE_TRACK_TRANSIT_LOCATIONS = { 5, 15, 25, 35 };
	private final int[] INNER_TRACK_TRANSIT_LOCATIONS = { 9, 21 };

	private Track track1;
	private Track track2;
	private Track track3;
	private static Board board;

	private Board() {
		track1 = new Track();
		track2 = new Track();
		track3 = new Track();

		track1.setTransitlocations(OUTER_TRACK_TRANSIT_LOCATIONS);
		track2.setTransitlocations(MIDDLE_TRACK_TRANSIT_LOCATIONS);
		track3.setTransitlocations(INNER_TRACK_TRANSIT_LOCATIONS);

		constructSquares();
	}

	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	public void constructSquares() {
		//mortgage values will be added.
		GoSquare go = new GoSquare("GO", 1);
		track2.addSquare(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		track2.addSquare(mediterraneanAve);
		CommunityChestSquare communityChest1track2 = new CommunityChestSquare ("Community Chest", 3);
		track2.addSquare(communityChest1track2);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		track2.addSquare(balticAve);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		// squares.add(incomeTax1);
		// Reading TransitStation-Railroad 
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		track2.addSquare(orientalAve);
		ChanceSquare chanceSquare1track2 = new ChanceSquare("Chance", 8);
		track2.addSquare(chanceSquare1track2);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		track2.addSquare(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		track2.addSquare(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		track2.addSquare(jailSquare);
		TitleDeed stCharlesPlaceTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare stCharlesPlace = new PropertySquare("St. Charles Place", 12, 140, null);
		track2.addSquare(stCharlesPlace);
//		Utility Squares will be modified.
//		UtilitySquare electricCompany = new UtilitySquare ("Electric Company", 13, 150, null);
//		track2.addSquare(electricCompany);
		TitleDeed statesAveTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare statesAve = new PropertySquare("States Avenue", 14, 140, null);
		track2.addSquare(statesAve);
		TitleDeed virginiaAveTD = new TitleDeed(12, 60, 180, 500, 700, 900, 1400, 80, 100, 100, 100);
		PropertySquare virginiaAve = new PropertySquare("Virginia Avenue", 15, 160, null);
		track2.addSquare(virginiaAve);
		// Pennsylvania Railroad/TransitStation
		TitleDeed stJamesPlaceTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare stJamesPlace = new PropertySquare("St. James Place", 17, 180, null);
		track2.addSquare(stJamesPlace);
		CommunityChestSquare communityChest2track2 = new CommunityChestSquare ("Community Chest", 18);
		track2.addSquare(communityChest2track2);
		TitleDeed tennesseeAveTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare tennesseeAve = new PropertySquare("Tennessee Avenue", 19, 180, null);
		track2.addSquare(tennesseeAve);
		TitleDeed newYorkAveTD = new TitleDeed(16, 80, 220, 600, 800, 1000, 1500, 100, 100, 100, 100);
		PropertySquare newYorkAve = new PropertySquare("New York Avenue", 20, 200, null);
		track2.addSquare(newYorkAve);
		FreeParkingSquare freeParking1Track2 = new FreeParkingSquare ("Free Parking", 21);		
		TitleDeed kentuckyAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare kentuckyAve = new PropertySquare("Kentucky Avenue", 22, 220, null);
		track2.addSquare(kentuckyAve);
		ChanceSquare chanceSquare2track2 = new ChanceSquare("Chance", 23);
		track2.addSquare(chanceSquare2track2);
		TitleDeed indianaAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare indianaAve = new PropertySquare("Indiana Avenue", 24, 220, null);
		track2.addSquare(indianaAve);
		TitleDeed illinoisAveTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 210, 120, 150, 150, 150);
		PropertySquare illinoisAve = new PropertySquare("Illinois Avenue", 25, 240, null);
		track2.addSquare(illinoisAve);
		// B&O TransitStation/Railroad
		TitleDeed atlanticAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare atlanticAve = new PropertySquare("Atlantic Avenue", 27, 260, null);
		track2.addSquare(atlanticAve);
		TitleDeed ventnorAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare ventnorAve = new PropertySquare("Ventnor Avenue", 28, 260, null);
		track2.addSquare(ventnorAve);
//		UtilitySquare waterWorks = new UtilitySquare ("Water Works", 29, 150, null);
//		track2.addSquare(waterWorks);
		TitleDeed marvinGardensTD = new TitleDeed(24, 120, 350, 850, 1025, 1200, 2200, 140, 150, 150, 150);
		PropertySquare marvinGardens = new PropertySquare("Marvin Gardens", 30, 280, null);
		track2.addSquare(marvinGardens);
		GoToJailSquare goToJail = new GoToJailSquare("Go to Jail", 31);
		track2.addSquare(goToJail);
		
		TitleDeed pacificAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare pacificAve = new PropertySquare("Pacific Avenue", 32, 300, null);
		track2.addSquare(pacificAve);
		TitleDeed noCarolinaAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare noCarolinaAve = new PropertySquare("North Carolina Avenue", 33, 300, null);
		track2.addSquare(noCarolinaAve);
		CommunityChestSquare communityChest3track2 = new CommunityChestSquare ("Community Chest", 34);
		track2.addSquare(communityChest3track2);
		TitleDeed pennsylvaniaAveTD = new TitleDeed(28, 150, 450, 1000, 1200, 1400, 2400, 160, 200, 200, 200);
		PropertySquare pennsylvaniaAve = new PropertySquare("Pennsylvania Avenue", 35, 300, null);
		track2.addSquare(pennsylvaniaAve);
		//Short Line Railroad/TransitStation
		ChanceSquare chanceSquare3track2 = new ChanceSquare("Chance", 37);
		track2.addSquare(chanceSquare3track2);
		TitleDeed parkPlaceTD = new TitleDeed(35, 175, 500, 1100, 1300, 1500, 2500, 200, 200, 200, 200);
		PropertySquare parkPlace = new PropertySquare("Park Place", 38, 350, null);
		track2.addSquare(parkPlace);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 39);
		// squares.add(incomeTax1);
		TitleDeed boardwalkTD = new TitleDeed(50, 200, 600, 1400, 1700, 2000, 3000, 200, 200, 200, 200);
		PropertySquare boardwalk = new PropertySquare("Boardwalk", 40, 400, null);
		track2.addSquare(boardwalk);
		//tryy
		
	}

	public Square getSquare(int index, int trackID) {
		return getTrack(trackID).getSquare(index);
	}

	public int getNoOfSquaresOnTrack(int trackID) {
		return getTrack(trackID).getSquareNumber();
	}

	public int[] getTransitStationLocationsOnTrack(int trackID) {
		Track track = getTrack(trackID);
		return track.getTransitlocations();
	}

	private Track getTrack(int trackID) {
		if (trackID == OUTER_TRACK)
			return track1;
		else if (trackID == MIDDLE_TRACK)
			return track2;
		else
			return track3;
	}

}
