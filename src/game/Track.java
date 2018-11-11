package game;

import java.util.ArrayList;

import game.square.*;

public class Track {

	private ArrayList<Square> squares;

	private int[] transitLocations;

	public Track() {
		squares = new ArrayList<Square>();
	}

	public void addSquare(Square sq) {
		squares.add(sq);
	}

	public Square getSquare(int index) {
		return squares.get(index);
	}

	public int getSquareNumber() {
		return squares.size();
	}

	public void setTransitlocations(int[] locations) {
		transitLocations = locations;
	}
	
	public int[] getTransitlocations() {
		return transitLocations;
	}
}
