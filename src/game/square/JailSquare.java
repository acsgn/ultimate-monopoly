package game.square;

import game.Square;
import game.SquareType;

public class JailSquare extends Square{

	private static final SquareType type = SquareType.JAIL;
	
	public JailSquare() {
	}

	@Override
	public SquareType getType() {
		return type;
	}

}
