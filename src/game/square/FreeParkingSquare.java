package game.square;

import game.Square;
import game.SquareType;

public class FreeParkingSquare extends Square {
	
	private static final SquareType type = SquareType.FREE_PARKING;
	
	public FreeParkingSquare() {
	}

	@Override
	public SquareType getType() {
		return type;
	}
	
}