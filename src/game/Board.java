package game;

import java.util.ArrayList;
import java.util.List;

import game.square.GoSquare;
import game.square.Square;

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
		
	}
	public Square getSquare(int index){
		return squares.get(index);
	}
	public int getNoOfSquares(){
		return squares.size();
	}
		
}
