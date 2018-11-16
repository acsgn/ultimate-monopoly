package game.square;

public class FreeParkingSquare extends Square {
	
	private static final SquareType type = SquareType.FREE_PARKING;
	
	public FreeParkingSquare() {
	}

	@Override
	public SquareType getType() {
		return type;
	}
	
}