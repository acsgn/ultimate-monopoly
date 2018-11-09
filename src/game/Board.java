package game;

import java.util.ArrayList;
import java.util.List;

import game.square.*;

public class Board {
	private List<Square> squares; 
	private static Board board; 
	
	private Board(){
		squares = new ArrayList<>();
		constructSquares();
	}
	
	public static Board getInstance(){
		if(board==null){
			board = new Board();
		}
		return board;
	}
	public void constructSquares(){
		GoSquare go = new GoSquare("GO",1);
		squares.add(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250,
				750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		squares.add(mediterraneanAve);
		//CommunityChestSquare communityChest1 = new CommunityChestSquare ("Community Chest", 3);
		//squares.add(communityChest1);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450,
				900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		squares.add(balticAve);
		//IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		//squares.add(incomeTax1);
		//Transit-Railroad here
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550,
				1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		squares.add(orientalAve);
		ChanceSquare chanceSquare1 = new ChanceSquare("Chance", 8);
		squares.add(chanceSquare1);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550,
				1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		squares.add(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600,
				1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		squares.add(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		squares.add(jailSquare);
		
		
	}
	public Square getSquare(int index){
		return squares.get(index);
	}
	public int getNoOfSquares(){
		return squares.size();
	}
		
}
