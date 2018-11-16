package game.square;

import game.Player;

public abstract class Square {

	public abstract SquareType getType();
	public abstract void executeAction(Player player);
	
}