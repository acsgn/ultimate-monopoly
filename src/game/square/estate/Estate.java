package game.square.estate;

import game.Player;
import game.Square;
import game.SquareType;

public abstract class Estate extends Square {
	
	private static final SquareType type = SquareType.ACTION;

	public abstract void executeAction(Player player);
	
	@Override
	public SquareType getType() {
		return type;
	}
}
