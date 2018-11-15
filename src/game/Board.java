package game;

import game.square.*;

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
		
		//The removed squares are replaced with free parking
		FreeParkingSquare freeParking1Track1 = new FreeParkingSquare ("Free Parking", 1);
		track1.addSquare(freeParking1Track1);
		TitleDeed lakeStreetTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		PropertySquare lakeStreet = new PropertySquare("Lake Street", 2, 30, null);
		track1.addSquare(lakeStreet);
		CommunityChestSquare communityChest1track1 = new CommunityChestSquare ("Community Chest", 3);
		track1.addSquare(communityChest1track1);
		TitleDeed nicolletAveTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		PropertySquare nicolletAve = new PropertySquare("Nicollet Avenue", 4, 30, null);
		track1.addSquare(nicolletAve);		
		TitleDeed hennepinAveTD = new TitleDeed(3, 15, 45, 120, 240, 350, 850, 30, 50, 50, 50);
		PropertySquare hennepinAve = new PropertySquare("Hennepin Avenue", 5, 60, null);
		track1.addSquare(hennepinAve);
		FreeParkingSquare freeParking2Track1 = new FreeParkingSquare ("Free Parking", 6);
		track1.addSquare(freeParking2Track1);
		FreeParkingSquare freeParking3Track1 = new FreeParkingSquare ("Free Parking", 7);
		track1.addSquare(freeParking3Track1);
		// Reading TransitStation-Railroad 
		TitleDeed esplanadeAveTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		PropertySquare esplanadeAve = new PropertySquare("Esplanade Avenue", 9, 90, null);
		track1.addSquare(esplanadeAve);
		TitleDeed canalStreetTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		PropertySquare canalStreet = new PropertySquare("Canal Street", 10, 90, null);
		track1.addSquare(canalStreet);
		ChanceSquare chanceSquare1track1 = new ChanceSquare("Chance", 11);
		track1.addSquare(chanceSquare1track1);
//		Utility Squares will be modified.
//		UtilitySquare cableCompany = new UtilitySquare ("Cable Company", 12, 150, null);
//		track1.addSquare(cableCompany);
		TitleDeed magazineStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare magazineStreet = new PropertySquare("Magazine Street",13, 120, null);
		track1.addSquare(magazineStreet);
		TitleDeed bourbonStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare bourbonStreet = new PropertySquare("Bourbon Street", 14, 120, null);
		track1.addSquare(bourbonStreet);
		HollandTunnelSquare hollandTunnel1 = new HollandTunnelSquare("Holland Tunnel", 15);
		track1.addSquare(hollandTunnel1);
		FreeParkingSquare freeParking4Track1 = new FreeParkingSquare ("Free Parking", 16);
		track1.addSquare(freeParking4Track1);
		TitleDeed katyFreewayTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		PropertySquare katyFreeway = new PropertySquare("Katy Freeway", 17, 150, null);
		track1.addSquare(katyFreeway);
		TitleDeed westheimerRoadTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		PropertySquare westheimerRoad = new PropertySquare("Westheimer Road", 18, 150, null);
		track1.addSquare(westheimerRoad);
//		UtilitySquare internetServiceProvider = new UtilitySquare ("Internet Service Provider", 19, 150, null);
//		track1.addSquare(internetServiceProvider);
		TitleDeed kirbyDrTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		PropertySquare kirbyDr = new PropertySquare("Kirby Drive", 20, 180, null);
		track1.addSquare(kirbyDr);
		TitleDeed cullenBlvdTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		PropertySquare cullenBlvd = new PropertySquare("Cullen Boulevard", 21, 180, null);
		track1.addSquare(cullenBlvd);
		ChanceSquare chanceSquare2track1 = new ChanceSquare("Chance", 22);
		track1.addSquare(chanceSquare2track1);
		FreeParkingSquare freeParking5Track1 = new FreeParkingSquare ("Free Parking", 23);
		track1.addSquare(freeParking5Track1);
		TitleDeed dekalbAveTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		PropertySquare dekalbAve = new PropertySquare("Dekalb Avenue", 24, 210, null);
		track1.addSquare(dekalbAve);
		CommunityChestSquare communityChest2track1 = new CommunityChestSquare ("Community Chest", 25);
		track1.addSquare(communityChest2track1);
		TitleDeed andrewYoungIntlBlvdTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		PropertySquare andrewYoungIntlBlvd = new PropertySquare("Andrew Young Intl Boulevard", 26, 210, null);
		track1.addSquare(andrewYoungIntlBlvd);
		TitleDeed decaturStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);
		PropertySquare decaturSt = new PropertySquare("Decatur Street", 27, 240, null);
		track1.addSquare(decaturSt);
		TitleDeed peachtreeStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);
		PropertySquare peachtreeSt = new PropertySquare("Peachtree Street", 28, 240, null);
		track1.addSquare(peachtreeSt);
//		PayDaySquare payDay = new PayDaySquare("PAY DAY", 29);
//		track1.addSquare(payDay);
		TitleDeed randolphStTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		PropertySquare randolphSt = new PropertySquare("Randolph Street", 30, 270, null);
		track1.addSquare(randolphSt);
		ChanceSquare chanceSquare3track1 = new ChanceSquare("Chance", 31);
		track1.addSquare(chanceSquare3track1);
		TitleDeed lakeShoreDrTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		PropertySquare lakeShoreDr = new PropertySquare("Lake Shore Drive", 32, 270, null);
		track1.addSquare(lakeShoreDr);
		TitleDeed wackerDrTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		PropertySquare wackerDr = new PropertySquare("Wacker Drive", 33, 300, null);
		track1.addSquare(wackerDr);
		TitleDeed michiganAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		PropertySquare michiganAve = new PropertySquare("Michigan Avenue", 34, 300, null);
		track1.addSquare(michiganAve);
		FreeParkingSquare freeParking6Track1 = new FreeParkingSquare ("Free Parking", 35);
		track1.addSquare(freeParking6Track1);
		// B&O Railroad/TransitStation
		CommunityChestSquare communityChest3track1 = new CommunityChestSquare ("Community Chest", 37);
		track1.addSquare(communityChest3track1);
		TitleDeed southTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		PropertySquare southTemple = new PropertySquare("South Temple", 38, 330, null);
		track1.addSquare(southTemple);
		TitleDeed westTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		PropertySquare westTemple = new PropertySquare("West Temple", 39, 330, null);
		track1.addSquare(westTemple);
//		UtilitySquare trashCollector = new UtilitySquare ("Trash Collector", 40, 150, null);
//		track1.addSquare(trashCollector);
		TitleDeed northTempleTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		PropertySquare northTemple = new PropertySquare("North Temple", 41, 360, null);
		track1.addSquare(northTemple);
		TitleDeed templeSquareTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		PropertySquare templeSquare = new PropertySquare("Temple Square", 42, 360, null);
		track1.addSquare(templeSquare);
		SubwaySquare subway = new SubwaySquare ("Subway", 43);
		track1.addSquare(subway);
		TitleDeed southStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		PropertySquare southSt = new PropertySquare("South Street", 44, 390, null);
		track1.addSquare(southSt);
		TitleDeed broadStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		PropertySquare broadSt = new PropertySquare("Broad Street", 45, 390, null);
		track1.addSquare(broadSt);
		TitleDeed walnutStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		PropertySquare walnutSt = new PropertySquare("Walnut Street", 46, 420, null);
		track1.addSquare(walnutSt);
		CommunityChestSquare communityChest4track1 = new CommunityChestSquare ("Community Chest", 47);
		track1.addSquare(communityChest4track1);
		TitleDeed marketStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		PropertySquare marketSt = new PropertySquare("Market Street", 48, 420, null);
		track1.addSquare(marketSt);
		FreeParkingSquare freeParking7Track1 = new FreeParkingSquare ("Free Parking", 49);
		track1.addSquare(freeParking7Track1);
//		UtilitySquare sewageSystem = new UtilitySquare ("Sewage System", 50, 150, null);
//		track1.addSquare(sewageSystem);
		FreeParkingSquare freeParking8Track1 = new FreeParkingSquare ("Free Parking", 51);
		track1.addSquare(freeParking8Track1);
		BirthGiftSquare birthdayGift = new BirthGiftSquare ("Birthday Gift", 52);
		track1.addSquare(birthdayGift);
		TitleDeed mulhollandDrTD = new TitleDeed(70, 350, 750, 1600, 1850, 2100, 3600, 175, 300, 300, 300);
		PropertySquare mulhollandDr = new PropertySquare("Mulholland Drive", 53, 450, null);
		track1.addSquare(mulhollandDr);
		TitleDeed venturaBlvdTD = new TitleDeed(80, 400, 825, 1800, 2175, 2550, 4050, 200, 300, 300, 300);
		PropertySquare venturaBlvd = new PropertySquare("Ventura Boulevard", 54, 480, null);
		track1.addSquare(venturaBlvd);
		ChanceSquare chanceSquare4track1 = new ChanceSquare("Chance", 55);
		track1.addSquare(chanceSquare4track1);
		TitleDeed rodeoDrTD = new TitleDeed(90, 450, 900, 2000, 2500, 3000, 4500, 250, 300, 300, 300);
		PropertySquare rodeoDr = new PropertySquare("Rodeo Drive", 56, 510, null);
		track1.addSquare(rodeoDr);
		
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
		TitleDeed illinoisAveTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 2100, 120, 150, 150, 150);
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
		
		//trying
		
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
