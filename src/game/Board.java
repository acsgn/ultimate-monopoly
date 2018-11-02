package game;

import java.util.List;

public class Board {
	private List<Square> squares; 
	
	public Board(){
		constructSquares();
	}
	public void constructSquares(){
		
	}
	public Square getSquare(int index){
		return squares.get(index);
	}
}
