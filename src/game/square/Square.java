package game.square;

import game.Player;

public abstract class Square {

	public abstract void executeWhenPassed(Player player);

	public abstract void executeWhenLanded(Player player);

	public abstract SquareType getType();

}