package game;

import java.util.ArrayList;

import game.square.*;

public class Board {
	private ArrayList<Square> track1Squares;
	private ArrayList<Square> track2Squares;
	private ArrayList<Square> track3Squares;
	private static Board board;

	private Board() {
		track1Squares = new ArrayList<Square>();
		track2Squares = new ArrayList<Square>();
		track3Squares = new ArrayList<Square>();
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
		track1Squares.add(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		track1Squares.add(mediterraneanAve);
		// CommunityChestSquare communityChest1 = new CommunityChestSquare ("Community
		// Chest", 3);
		// squares.add(communityChest1);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		track1Squares.add(balticAve);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		// squares.add(incomeTax1);
		// Transit-Railroad here
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		track1Squares.add(orientalAve);
		ChanceSquare chanceSquare1 = new ChanceSquare("Chance", 8);
		track1Squares.add(chanceSquare1);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		track1Squares.add(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		track1Squares.add(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		track1Squares.add(jailSquare);

	}

	public Square getSquare(int index, int track) {
		if (track == 1)
			return track1Squares.get(index);
		else if (track == 2)
			return track2Squares.get(index);
		else
			return track3Squares.get(index);
	}

	public int getNoOfSquares() {
		return track1Squares.size() + track2Squares.size() + track3Squares.size();
	}

}
