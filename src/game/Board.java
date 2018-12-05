package game;

import game.square.*;
import game.square.action.*;
import game.square.estate.*;

public class Board {

	private Track outerTrack;
	private Track middleTrack;
	private Track innerTrack;

	private TransitStation firstTS;
	private TransitStation secondTS;
	private TransitStation thirdTS;
	private TransitStation fourthTS;
	
	private HollandTunnel hollandTunnel;
	
	private Chance chance;
	private CommunityChest communityChest;
	private FreeParking freeParking;

	public Board() {
		outerTrack = new Track(TrackType.OUTER_TRACK);
		middleTrack = new Track(TrackType.MIDDLE_TRACK);
		innerTrack = new Track(TrackType.INNER_TRACK);

		firstTS = new TransitStation("Reading Railroad", middleTrack.getTrackType(), outerTrack.getTrackType(), 5, 7);
		secondTS = new TransitStation("Pennsylvania Railroad", middleTrack.getTrackType(), innerTrack.getTrackType(),15,9);
		thirdTS = new TransitStation("B & O Railroad", middleTrack.getTrackType(), outerTrack.getTrackType(),25,35);
		fourthTS = new TransitStation("Short Line", middleTrack.getTrackType(), innerTrack.getTrackType(),35,27);

		hollandTunnel = new HollandTunnel();
		
		chance = new Chance();
		communityChest = new CommunityChest();
		freeParking = new FreeParking();

		constructOuterTrack();
		constructMiddleTrack();
		constructInnerTrack();
	}

	public Square getSquare(int index, TrackType type) {
		return getTrack(type).getSquare(index);
	}

	public int getNoOfSquaresOnTrack(TrackType type) {
		return getTrack(type).getSquareNumber();
	}

	public int getIndexOfSquare(Square sq, TrackType type) {
		return getTrack(type).getSquareIndex(sq);
	}

	private Track getTrack(TrackType type) {
		if (type == outerTrack.getTrackType())
			return outerTrack;
		else if (type == middleTrack.getTrackType())
			return middleTrack;
		else
			return innerTrack;
	}

	private void constructInnerTrack() {
		TitleDeed theEmbarcaderoTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		TitleDeed fishermansWharfTD = new TitleDeed(21, 105, 320, 780, 950, 1125, 1625, 125, 100, 100, 100);
		TitleDeed beaconStTD = new TitleDeed(30, 160, 470, 1050, 1250, 1500, 2500, 165, 200, 200, 200);
		TitleDeed boylstonStTD = new TitleDeed(30, 160, 470, 1050, 1250, 1500, 2500, 165, 200, 200, 200);
		TitleDeed newburyStTD = new TitleDeed(40, 185, 550, 1200, 1500, 1700, 2700, 190, 200, 200, 200);
		TitleDeed fifthAveTD = new TitleDeed(60, 220, 650, 1500, 1800, 2100, 3600, 215, 300, 300, 300);
		TitleDeed madisonAveTD = new TitleDeed(60, 220, 650, 1500, 1800, 2100, 3600, 215, 300, 300, 300);
		TitleDeed wallStTD = new TitleDeed(80, 300, 800, 1800, 2200, 2700, 4200, 250, 300, 300, 300);
		TitleDeed floridaAveTD = new TitleDeed(9, 45, 120, 350, 500, 700, 1200, 65, 50, 50, 50);
		TitleDeed miamiAveTD = new TitleDeed(9, 45, 120, 350, 500, 700, 1200, 65, 50, 50, 50);
		TitleDeed biscayneAveTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 75, 50, 50, 50);
		TitleDeed lombardStTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);

		Property theEmbarcadero = new Property("The Embarcadero", 210, theEmbarcaderoTD);
		Property fishermansWharf = new Property("Fisherman's Wharf", 250, fishermansWharfTD);
		Property beaconSt = new Property("Beacon Street", 330, beaconStTD);
		Property boylstonSt = new Property("Boylston Street", 330, boylstonStTD);
		Property newburySt = new Property("Newbury Street", 380, newburyStTD);
		Property fifthAve = new Property("Fifth Avenue", 430, fifthAveTD);
		Property madisonAve = new Property("Madison Avenue", 430, madisonAveTD);
		Property wallSt = new Property("Wall Street", 500, wallStTD);
		Property floridaAve = new Property("Florida Avenue", 130, floridaAveTD);
		Property miamiAve = new Property("Miami Avenue", 130, miamiAveTD);
		Property biscayneAve = new Property("Biscayne Avenue", 150, biscayneAveTD);
		Property lombardSt = new Property("Lombard Street", 210, lombardStTD);

		Utility telephoneCompany = new Utility("Telephone Company");
		Utility gasCompany = new Utility("Gas Company");

		Bonus bonus = new Bonus();

		TaxRefund taxRefund = new TaxRefund();

		innerTrack.addSquare(freeParking);
		innerTrack.addSquare(theEmbarcadero);
		innerTrack.addSquare(fishermansWharf);
		innerTrack.addSquare(telephoneCompany);
		innerTrack.addSquare(communityChest);
		innerTrack.addSquare(beaconSt);
		innerTrack.addSquare(bonus);
		innerTrack.addSquare(boylstonSt);
		innerTrack.addSquare(newburySt);
		innerTrack.addSquare(secondTS);
		innerTrack.addSquare(fifthAve);
		innerTrack.addSquare(madisonAve);
		innerTrack.addSquare(freeParking);
		innerTrack.addSquare(wallSt);
		innerTrack.addSquare(taxRefund);
		innerTrack.addSquare(gasCompany);
		innerTrack.addSquare(chance);
		innerTrack.addSquare(floridaAve);
		innerTrack.addSquare(hollandTunnel);
		innerTrack.addSquare(miamiAve);
		innerTrack.addSquare(biscayneAve);
		innerTrack.addSquare(fourthTS);
		innerTrack.addSquare(freeParking);
		innerTrack.addSquare(lombardSt);
	}

	private void constructMiddleTrack() {
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		TitleDeed stCharlesPlaceTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		TitleDeed statesAveTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		TitleDeed virginiaAveTD = new TitleDeed(12, 60, 180, 500, 700, 900, 1400, 80, 100, 100, 100);
		TitleDeed stJamesPlaceTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		TitleDeed tennesseeAveTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		TitleDeed newYorkAveTD = new TitleDeed(16, 80, 220, 600, 800, 1000, 1500, 100, 100, 100, 100);
		TitleDeed kentuckyAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		TitleDeed indianaAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		TitleDeed illinoisAveTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 2100, 120, 150, 150, 150);
		TitleDeed atlanticAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		TitleDeed ventnorAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		TitleDeed marvinGardensTD = new TitleDeed(24, 120, 350, 850, 1025, 1200, 2200, 140, 150, 150, 150);
		TitleDeed pacificAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		TitleDeed noCarolinaAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		TitleDeed pennsylvaniaAveTD = new TitleDeed(28, 150, 450, 1000, 1200, 1400, 2400, 160, 200, 200, 200);
		TitleDeed parkPlaceTD = new TitleDeed(35, 175, 500, 1100, 1300, 1500, 2500, 200, 200, 200, 200);
		TitleDeed boardwalkTD = new TitleDeed(50, 200, 600, 1400, 1700, 2000, 3000, 200, 200, 200, 200);

		Property mediterraneanAve = new Property("Mediterranean Avenue", 60, mediterraneanAveTD);
		Property balticAve = new Property("Baltic Avenue", 60, balticAveTD);
		Property orientalAve = new Property("Oriental Avenue", 100, orientalAveTD);
		Property vermontAve = new Property("Vermont Avenue", 100, vermontAveTD);
		Property connecticutAve = new Property("Connecticut Avenue", 120, connecticutAveTD);
		Property stCharlesPlace = new Property("St. Charles Place", 140, stCharlesPlaceTD);
		Property statesAve = new Property("States Avenue", 140, statesAveTD);
		Property virginiaAve = new Property("Virginia Avenue", 160, virginiaAveTD);
		Property stJamesPlace = new Property("St. James Place", 180, stJamesPlaceTD);
		Property tennesseeAve = new Property("Tennessee Avenue", 180, tennesseeAveTD);
		Property newYorkAve = new Property("New York Avenue", 200, newYorkAveTD);
		Property kentuckyAve = new Property("Kentucky Avenue", 220, kentuckyAveTD);
		Property indianaAve = new Property("Indiana Avenue", 220, indianaAveTD);
		Property illinoisAve = new Property("Illinois Avenue", 240, illinoisAveTD);
		Property atlanticAve = new Property("Atlantic Avenue", 260, atlanticAveTD);
		Property ventnorAve = new Property("Ventnor Avenue", 260, ventnorAveTD);
		Property marvinGardens = new Property("Marvin Gardens", 280, marvinGardensTD);
		Property pacificAve = new Property("Pacific Avenue", 300, pacificAveTD);
		Property noCarolinaAve = new Property("North Carolina Avenue", 300, noCarolinaAveTD);
		Property pennsylvaniaAve = new Property("Pennsylvania Avenue", 320, pennsylvaniaAveTD);
		Property parkPlace = new Property("Park Place", 350, parkPlaceTD);
		Property boardwalk = new Property("Boardwalk", 400, boardwalkTD);

		Utility electricCompany = new Utility("Electric Company");
		Utility waterWorks = new Utility("Water Works");
		

		Go go = new Go();
		IncomeTax incomeTax = new IncomeTax();
		Jail jail = new Jail();
		GoToJail goToJail = new GoToJail();
		LuxuryTax luxuryTax = new LuxuryTax();

		middleTrack.addSquare(go);
		middleTrack.addSquare(mediterraneanAve);
		middleTrack.addSquare(communityChest);
		middleTrack.addSquare(balticAve);
		middleTrack.addSquare(incomeTax);
		middleTrack.addSquare(firstTS);
		middleTrack.addSquare(orientalAve);
		middleTrack.addSquare(chance);
		middleTrack.addSquare(vermontAve);
		middleTrack.addSquare(connecticutAve);
		middleTrack.addSquare(jail);
		middleTrack.addSquare(stCharlesPlace);
		middleTrack.addSquare(electricCompany);
		middleTrack.addSquare(statesAve);
		middleTrack.addSquare(virginiaAve);
		middleTrack.addSquare(secondTS);
		middleTrack.addSquare(stJamesPlace);
		middleTrack.addSquare(communityChest);
		middleTrack.addSquare(tennesseeAve);
		middleTrack.addSquare(newYorkAve);
		middleTrack.addSquare(freeParking);
		middleTrack.addSquare(kentuckyAve);
		middleTrack.addSquare(chance);
		middleTrack.addSquare(indianaAve);
		middleTrack.addSquare(illinoisAve);
		middleTrack.addSquare(thirdTS);
		middleTrack.addSquare(atlanticAve);
		middleTrack.addSquare(ventnorAve);
		middleTrack.addSquare(waterWorks);
		middleTrack.addSquare(marvinGardens);
		middleTrack.addSquare(goToJail);
		middleTrack.addSquare(pacificAve);
		middleTrack.addSquare(noCarolinaAve);
		middleTrack.addSquare(communityChest);
		middleTrack.addSquare(pennsylvaniaAve);
		middleTrack.addSquare(fourthTS);
		middleTrack.addSquare(chance);
		middleTrack.addSquare(parkPlace);
		middleTrack.addSquare(luxuryTax);
		middleTrack.addSquare(boardwalk);
	}

	private void constructOuterTrack() {

		TitleDeed lakeStreetTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		TitleDeed nicolletAveTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		TitleDeed hennepinAveTD = new TitleDeed(3, 15, 45, 120, 240, 350, 850, 30, 50, 50, 50);
		TitleDeed esplanadeAveTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		TitleDeed canalStreetTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		TitleDeed magazineStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		TitleDeed bourbonStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		TitleDeed katyFreewayTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		TitleDeed westheimerRoadTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		TitleDeed kirbyDrTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		TitleDeed cullenBlvdTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		TitleDeed randolphStTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		TitleDeed lakeShoreDrTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		TitleDeed wackerDrTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		TitleDeed michiganAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		TitleDeed southTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		TitleDeed westTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		TitleDeed northTempleTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		TitleDeed templeSquareTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		TitleDeed southStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		TitleDeed broadStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		TitleDeed walnutStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		TitleDeed marketStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		TitleDeed mulhollandDrTD = new TitleDeed(70, 350, 750, 1600, 1850, 2100, 3600, 175, 300, 300, 300);
		TitleDeed venturaBlvdTD = new TitleDeed(80, 400, 825, 1800, 2175, 2550, 4050, 200, 300, 300, 300);
		TitleDeed rodeoDrTD = new TitleDeed(90, 450, 900, 2000, 2500, 3000, 4500, 250, 300, 300, 300);
		TitleDeed dekalbAveTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		TitleDeed andrewYoungIntlBlvdTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		TitleDeed decaturStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);
		TitleDeed peachtreeStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);

		Property lakeStreet = new Property("Lake Street", 30, lakeStreetTD);
		Property nicolletAve = new Property("Nicollet Avenue", 30, nicolletAveTD);
		Property hennepinAve = new Property("Hennepin Avenue", 60, hennepinAveTD);
		Property esplanadeAve = new Property("Esplanade Avenue", 90, esplanadeAveTD);
		Property canalStreet = new Property("Canal Street", 90, canalStreetTD);
		Property magazineStreet = new Property("Magazine Street", 120, magazineStreetTD);
		Property bourbonStreet = new Property("Bourbon Street", 120, bourbonStreetTD);
		Property katyFreeway = new Property("Katy Freeway", 150, katyFreewayTD);
		Property westheimerRoad = new Property("Westheimer Road", 150, westheimerRoadTD);
		Property kirbyDr = new Property("Kirby Drive", 180, kirbyDrTD);
		Property cullenBlvd = new Property("Cullen Boulevard", 180, cullenBlvdTD);
		Property dekalbAve = new Property("Dekalb Avenue", 210, dekalbAveTD);
		Property andrewYoungIntlBlvd = new Property("Andrew Young Intl Boulevard", 210, andrewYoungIntlBlvdTD);
		Property decaturSt = new Property("Decatur Street", 240, decaturStTD);
		Property peachtreeSt = new Property("Peachtree Street", 240, peachtreeStTD);
		Property randolphSt = new Property("Randolph Street", 270, randolphStTD);
		Property lakeShoreDr = new Property("Lake Shore Drive", 270, lakeShoreDrTD);
		Property wackerDr = new Property("Wacker Drive", 300, wackerDrTD);
		Property michiganAve = new Property("Michigan Avenue", 300, michiganAveTD);
		Property southTemple = new Property("South Temple", 330, southTempleTD);
		Property westTemple = new Property("West Temple", 330, westTempleTD);
		Property northTemple = new Property("North Temple", 360, northTempleTD);
		Property templeSquare = new Property("Temple Square", 360, templeSquareTD);
		Property southSt = new Property("South Street", 390, southStTD);
		Property broadSt = new Property("Broad Street", 390, broadStTD);
		Property walnutSt = new Property("Walnut Street", 420, walnutStTD);
		Property marketSt = new Property("Market Street", 420, marketStTD);
		Property mulhollandDr = new Property("Mulholland Drive", 450, mulhollandDrTD);
		Property venturaBlvd = new Property("Ventura Boulevard", 480, venturaBlvdTD);
		Property rodeoDr = new Property("Rodeo Drive", 510, rodeoDrTD);

		Utility cableCompany = new Utility("Cable Company");
		Utility internetServiceProvider = new Utility("Internet Service Provider");
		Utility trashCollector = new Utility("Trash Collector");
		Utility sewageSystem = new Utility("Sewage System");

		PayDay payDay = new PayDay();
		Subway subway = new Subway();
		BirthGift birthdayGift = new BirthGift();

		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(lakeStreet);
		outerTrack.addSquare(communityChest);
		outerTrack.addSquare(nicolletAve);
		outerTrack.addSquare(hennepinAve);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(firstTS);
		outerTrack.addSquare(esplanadeAve);
		outerTrack.addSquare(canalStreet);
		outerTrack.addSquare(chance);
		outerTrack.addSquare(cableCompany);
		outerTrack.addSquare(magazineStreet);
		outerTrack.addSquare(bourbonStreet);
		outerTrack.addSquare(hollandTunnel);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(katyFreeway);
		outerTrack.addSquare(westheimerRoad);
		outerTrack.addSquare(internetServiceProvider);
		outerTrack.addSquare(kirbyDr);
		outerTrack.addSquare(cullenBlvd);
		outerTrack.addSquare(chance);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(dekalbAve);
		outerTrack.addSquare(communityChest);		
		outerTrack.addSquare(andrewYoungIntlBlvd);
		outerTrack.addSquare(decaturSt);
		outerTrack.addSquare(peachtreeSt);
		outerTrack.addSquare(payDay);
		outerTrack.addSquare(randolphSt);
		outerTrack.addSquare(chance);
		outerTrack.addSquare(lakeShoreDr);
		outerTrack.addSquare(wackerDr);
		outerTrack.addSquare(michiganAve);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(thirdTS);
		outerTrack.addSquare(communityChest);
		outerTrack.addSquare(southTemple);
		outerTrack.addSquare(westTemple);
		outerTrack.addSquare(trashCollector);
		outerTrack.addSquare(northTemple);
		outerTrack.addSquare(templeSquare);
		outerTrack.addSquare(subway);
		outerTrack.addSquare(southSt);
		outerTrack.addSquare(broadSt);
		outerTrack.addSquare(walnutSt);
		outerTrack.addSquare(communityChest);
		outerTrack.addSquare(marketSt);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(sewageSystem);
		outerTrack.addSquare(freeParking);
		outerTrack.addSquare(birthdayGift);
		outerTrack.addSquare(mulhollandDr);
		outerTrack.addSquare(venturaBlvd);
		outerTrack.addSquare(chance);
		outerTrack.addSquare(rodeoDr);
	}

}
