package game;

import java.util.ArrayList;

import game.square.*;

public class Track {

	private TrackType type;

	private ArrayList<Square> squares;

	private int[] transitLocations;

	public Track(TrackType type) {
		squares = new ArrayList<Square>();
		this.type = type;
	}

	public void addSquare(Square sq) {
		squares.add(sq);
	}

	public Square getSquare(int index) {
		return squares.get(index);
	}

	public int getSquareIndex(Square sq) {
		return squares.indexOf(sq);
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

	public TrackType getTrackType() {
		return type;
	}

}