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
		GoSquare go = new GoSquare("GO", 1);
		track2.addSquare(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		track2.addSquare(mediterraneanAve);
		// CommunityChestSquare communityChest1 = new CommunityChestSquare ("Community
		// Chest", 3);
		// squares.add(communityChest1);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		track2.addSquare(balticAve);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		// squares.add(incomeTax1);
		// Transit-Railroad here
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		track2.addSquare(orientalAve);
		ChanceSquare chanceSquare1 = new ChanceSquare("Chance", 8);
		track2.addSquare(chanceSquare1);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		track2.addSquare(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		track2.addSquare(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		track2.addSquare(jailSquare);

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
