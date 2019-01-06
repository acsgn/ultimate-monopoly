package game;

import java.io.Serializable;
import java.util.ArrayList;

import game.square.*;

public class Track implements Serializable {
	private static final long serialVersionUID = 1L;

	private TrackType type;

	private ArrayList<Square> squares;

	public Track(TrackType type) {
		squares = new ArrayList<Square>();
		this.type = type;
	}

	/**
	 * @overview Adds the given square to the array list squares
	 * @param sq the square to be added to the array list
	 * @requires
	 * @modifies squares
	 * @effects a new square is added to the array list
	 */
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

	public TrackType getTrackType() {
		return type;
	}

}
